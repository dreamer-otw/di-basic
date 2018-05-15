/*
package com.dreamer.basic.backstage.sys.controller;

import com.sys.repository.entity.SysUserEntity;
import com.sys.service.SysUserRoleService;
import com.sys.service.SysUserService;
import com.sys.utils.PageUtils;
import com.sys.utils.R;
import com.sys.utils.ShiroUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

*/
/**
 * 系统用户
 * @author R & D
 * @email 908350381@qq.com
 * @date 2016年10月31日 上午10:40:10
 *//*

@RestController
@RequestMapping("/sys/user")
public class SysUserController extends AbstractController {
	private static final Logger logger = LoggerFactory.getLogger(SysUserController.class);

	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysUserRoleService sysUserRoleService;

	*/
/**
	 * 所有用户列表
	 *//*

	@RequestMapping("/list")
	@RequiresPermissions("sys:user:list")
	public R list(Integer page, Integer limit) {
		Map<String, Object> map = new HashMap<>();
		map.put("offset", (page - 1) * limit);
		map.put("limit", limit*page);

		// 查询列表数据
		List<SysUserEntity> userList = sysUserService.queryList(map);
		int total = sysUserService.queryTotal(map);

		PageUtils pageUtil = new PageUtils(userList, total, limit, page);

		return R.ok().put("page", pageUtil);
	}

	*/
/**
	 * 获取登录的用户信息
	 *//*

	@RequestMapping("/info")
	public R info() {
		return R.ok().put("user", getUser());
	}

	*/
/**
	 * 修改登录用户密码
	 *//*

	@RequestMapping("/password")
	public R password(String password, String newPassword) {
		if (StringUtils.isBlank(newPassword)) {
			return R.error("新密码不为能空");
		}

		// sha256加密
		password = new Sha256Hash(password).toHex();
		// sha256加密
		newPassword = new Sha256Hash(newPassword).toHex();

		// 更新密码
		int count = sysUserService.updatePassword(getUserId(), password, newPassword);
		if (count == 0) {
			return R.error("原密码不正确");
		}

		// 退出
		ShiroUtils.logout();

		return R.ok();
	}

	*/
/**
	 * 用户信息
	 *//*

	@RequestMapping("/info/{userId}")
	@RequiresPermissions("sys:user:info")
	public R info(@PathVariable("userId") String userId) {
		SysUserEntity user = sysUserService.queryObject(userId);

		// 获取用户所属的角色列表
		List<String> roleIdList = sysUserRoleService.queryRoleIdList(userId);
		user.setRoleIdList(roleIdList);

		return R.ok().put("user", user);
	}

	*/
/**
	 * 保存用户
	 *//*

	@RequestMapping("/save")
	@RequiresPermissions("sys:user:save")
	public R save(@RequestBody SysUserEntity user) {
		try {
			if (StringUtils.isBlank(user.getUsername())) {
				return R.error("用户名不能为空");
			}
			if (StringUtils.isBlank(user.getPassword())) {
				return R.error("密码不能为空");
			}

			sysUserService.save(user);
		} catch (Exception e) {
			logger.error("保存用户信息失败",e);
			return R.error("保存用户信息失败");
		}

		return R.ok();
	}

	*/
/**
	 * 修改用户
	 *//*

	@RequestMapping("/update")
	@RequiresPermissions("sys:user:update")
	public R update(@RequestBody SysUserEntity user) {
		try {
			if (StringUtils.isBlank(user.getUsername())) {
				return R.error("用户名不能为空");
			}

			sysUserService.update(user);
		} catch (Exception e) {
			logger.error("修改用户信息失败",e);
			return R.error("修改用户信息失败");
		}
		return R.ok();
	}

	*/
/**
	 * 删除用户
	 *//*

	@RequestMapping("/delete")
	@RequiresPermissions("sys:user:delete")
	public R delete(@RequestBody String[] userIds) {
		try {
			if (ArrayUtils.contains(userIds, 1L)) {
				return R.error("系统管理员不能删除");
			}

			if (ArrayUtils.contains(userIds, getUserId())) {
				return R.error("当前用户不能删除");
			}

			sysUserService.deleteBatch(userIds);

			sysUserRoleService.deleteBatch(userIds);
		} catch (Exception e) {
			logger.error("删除用户信息失败",e);
			return R.error("删除用户信息失败");
		}
		return R.ok();
	}
}
*/
