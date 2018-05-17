package com.dreamer.basic.backstage.sys.controller;
import com.dreamer.basic.backstage.sys.data.SysMenuData;
import com.dreamer.basic.backstage.sys.service.SysLoginService;
import com.dreamer.basic.backstage.sys.utils.Result;
import com.dreamer.basic.backstage.sys.utils.ShiroUtils;
import com.dreamer.basic.common.generator.entity.SysUser;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import org.apache.shiro.authc.*;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;


/**
 * > 登陆
 * author : dreamer
 * email : dreamers_otw@163.com
 * date : 2018/5/14 15:13
 */
@RestController
public class SysLoginController extends AbstractController{
	@Autowired
	private Producer producer;
	@Autowired
	private SysLoginService sysLoginService;

	/**
	 * 处理验证码
	 */
	@RequestMapping("captcha.jpg")
	public void captcha(HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("Cache-Control", "no-store, no-cache");
		response.setContentType("image/jpeg");

		// 生成文字验证码
		String text = producer.createText();
		// 生成图片验证码
		BufferedImage image = producer.createImage(text);
		// 保存到shiro session
		ShiroUtils.setSessionAttribute(Constants.KAPTCHA_SESSION_KEY, text);

		ServletOutputStream out = response.getOutputStream();
		ImageIO.write(image, "jpg", out);
	}

	/**
	 * 登录
	 */
	@PostMapping(value = "/sys/login")
	public Result login(String userAccount, String password, String captcha) throws IOException {
		String kaptcha = ShiroUtils.getKaptcha(Constants.KAPTCHA_SESSION_KEY);
		if (!captcha.equalsIgnoreCase(kaptcha)) {
			return Result.error(00001, "验证码不正确");
		}
		try {
			Subject subject = ShiroUtils.getSubject();
			// sha256加密
			password = new Sha256Hash(password).toHex();
			UsernamePasswordToken token = new UsernamePasswordToken(userAccount, password);
			subject.login(token);
		} catch (UnknownAccountException e) {
			return Result.error(e.getMessage());
		} catch (IncorrectCredentialsException e) {
			return Result.error(e.getMessage());
		} catch (LockedAccountException e) {
			return Result.error(e.getMessage());
		} catch (AuthenticationException e) {
			return Result.error("账户验证失败");
		}
		return Result.ok();
	}

	/**
	 * 退出
	 */
	@GetMapping(value = "logout")
	public void logout(HttpServletResponse response) throws IOException {
		ShiroUtils.logout();
		response.sendRedirect("login.html");
	}

	/**
	 * 获取菜单list
	 */
	@GetMapping("/sys/menuList")
	public Result getMenuList() {
		List<SysMenuData> menuList = sysLoginService.getMenuListByUserId(getUserId());
		return Result.ok().put("menuList", menuList);
	}

	/**
	 * 修改登陆用户密码
	 */
	@PostMapping("/sys/update/password")
	public Result updatePassword(String password, String newPassword) {
		SysUser user = getUser();
		//原密码加密
		password = new Sha256Hash(password).toHex();
		if (!user.getUserPwd().equals(password)) {
			return Result.error(00002, "原密码不正确");
		}
		//新密码加密
		newPassword = new Sha256Hash(newPassword).toHex();
		int count = sysLoginService.updatePwdByUserId(newPassword, user.getUserId());
		if (count == 0) {
			return Result.error(00003, "修改密码失败");
		}
		// 退出
		ShiroUtils.logout();
		return Result.ok();
	}

	/**
	 * 获取用户信息
	 */
	@GetMapping("/sys/userInfo")
	public Result getUserInfo() {
		return Result.ok().put("user", getUser());
	}
}
