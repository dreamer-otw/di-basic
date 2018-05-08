package com.dreamer.basic.backstage.sys.service;

import com.sys.repository.dao.SysUserDao;
import com.sys.repository.entity.SysUserEntity;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统用户
 * @author R & D
 * @email 908350381@qq.com
 * @date 2016年9月18日 上午9:43:39
 */
@Service
public class SysUserService {
	@Autowired
	private SysUserDao sysUserDao;
	@Autowired
	private SysUserRoleService sysUserRoleService;

	/**
	 * 查询用户的所有权限
	 * @param userId 用户ID
	 */
	public List<String> queryAllPerms(String userId) {
		return sysUserDao.queryAllPerms(userId);
	}

	/**
	 * 查询用户的所有菜单ID
	 */
	public List<String> queryAllMenuId(String userId) {
		return sysUserDao.queryAllMenuId(userId);
	}

	/**
	 * 根据用户名，查询系统用户
	 */
	public SysUserEntity queryByUserName(String username) {
		return sysUserDao.queryByUserName(username);
	}

	/**
	 * 根据用户ID，查询用户
	 * @param userId
	 * @return
	 */
	public SysUserEntity queryObject(String userId) {
		return sysUserDao.queryObject(userId);
	}

	/**
	 * 查询用户列表
	 */
	public List<SysUserEntity> queryList(Map<String, Object> map) {
		return sysUserDao.queryList(map);
	}

	/**
	 * 查询总数
	 */
	public int queryTotal(Map<String, Object> map) {
		return sysUserDao.queryTotal(map);
	}

	/**
	 * 保存用户
	 */
	@Transactional
	public void save(SysUserEntity user) {
		user.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		// sha256加密
		user.setPassword(new Sha256Hash(user.getPassword()).toHex());
		//user.setUserId();
		// 添加用户
		sysUserDao.save(user);

		// 保存用户与角色关系
		sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
	}

	/**
	 * 修改用户
	 */
	@Transactional
	public void update(SysUserEntity user) {
		if (StringUtils.isBlank(user.getPassword())) {
			user.setPassword(null);
		} else {
			user.setPassword(new Sha256Hash(user.getPassword()).toHex());
		}
		// 更新用户
		sysUserDao.update(user);

		// 保存用户与角色关系
		sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
	}

	/**
	 * 删除用户
	 */
	@Transactional
	public void deleteBatch(String[] userId) {
		sysUserDao.deleteBatch(userId);
	}

	/**
	 * 修改密码
	 * @param userId 用户ID
	 * @param password 原密码
	 * @param newPassword 新密码
	 */
	public int updatePassword(String userId, String password, String newPassword) {
		Map<String, Object> map = new HashMap<>();
		map.put("userId", userId);
		map.put("password", password);
		map.put("newPassword", newPassword);
		return sysUserDao.updatePassword(map);
	}
}
