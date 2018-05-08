package com.dreamer.basic.backstage.sys.service;

import com.sys.repository.dao.SysUserRoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户与角色对应关系
 * @author R & D
 * @email 908350381@qq.com
 * @date 2016年9月18日 上午9:43:24
 */
@Service
public class SysUserRoleService {
	@Autowired
	private SysUserRoleDao sysUserRoleDao;

	/**
	 * 根据用户ID和角色ID列表，持久化用户与角色对应关系
	 */
	public void saveOrUpdate(String userId, List<String> roleIdList) {
		if (roleIdList.size() == 0) {
			return;
		}

		// 先删除用户与角色关系
		sysUserRoleDao.delete(userId);

		// 保存用户与角色关系
		Map<String, Object> map = new HashMap<>();
		for	(String roleId : roleIdList) {
			map.put("userId", userId);
			map.put("roleId", roleId);
			sysUserRoleDao.save(map);
		}
	}

	/**
	 * 根据用户ID，获取角色ID列表
	 */
	public List<String> queryRoleIdList(String userId) {
		return sysUserRoleDao.queryRoleIdList(userId);
	}

	/**
	 * 根据用户ID，删除对应关系
	 */
	public void delete(String userId) {
		sysUserRoleDao.delete(userId);
	}
	/**
	 * 删除用户
	 */
	@Transactional
	public void deleteBatch(String[] userId) {
		sysUserRoleDao.deleteBatch(userId);
	}
}
