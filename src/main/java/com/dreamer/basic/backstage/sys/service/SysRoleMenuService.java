package com.dreamer.basic.backstage.sys.service;

import com.sys.repository.dao.SysRoleMenuDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 角色与菜单对应关系
 * @author R & D
 * @email 908350381@qq.com
 * @date 2016年9月18日 上午9:42:30
 */
@Service
public class SysRoleMenuService {
	@Autowired
	private SysRoleMenuDao sysRoleMenuDao;

	/**
	 * 根据角色ID和菜单ID列表，持久化角色与菜单对应关系
	 *
	 * @param roleId     角色ID
	 * @param menuIdList 菜单ID列表
	 */
	@Transactional
	public void saveOrUpdate(String roleId, List<String> menuIdList) {
		if (menuIdList.size() == 0) {
			return;
		}
		// 先删除角色与菜单关系
		sysRoleMenuDao.delete(roleId);

		// 保存角色与菜单关系
		for (String menuId : menuIdList) {
			Map<String, Object> map = new HashMap<>();
			map.put("roleId", roleId);
			map.put("menuId", menuId);
			sysRoleMenuDao.save(map);
		}
	}

	/**
	 * 根据角色ID，获取菜单ID列表
	 *
	 * @param roleId 角色ID
	 * @return
	 */
	public List<String> queryMenuIdList(String roleId) {
		return sysRoleMenuDao.queryMenuIdList(roleId);
	}


	/**
	 * 批量删除角色
	 */
	@Transactional
	public void deleteBatch(String[] roleIds) {
		sysRoleMenuDao.deleteBatch(roleIds);
	}
}
