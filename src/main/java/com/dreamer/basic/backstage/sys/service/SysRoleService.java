package com.dreamer.basic.backstage.sys.service;

import com.sys.repository.dao.SysRoleDao;
import com.sys.repository.entity.SysRoleEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 角色
 * @author R & D
 * @email 908350381@qq.com
 * @date 2016年9月18日 上午9:42:52
 */
@Service
public class SysRoleService {
	@Autowired
	private SysRoleDao sysRoleDao;
	@Autowired
	private SysRoleMenuService sysRoleMenuService;
	@Autowired
	private SysUserRoleService sysUserRoleService;

	/**
	 * 根据角色ID，查询角色
	 */
	public SysRoleEntity queryObject(String roleId) {
		return sysRoleDao.queryObject(roleId);
	}

	/**
	 * 查询所有角色
	 */
	public List<SysRoleEntity> queryList(Map<String, Object> map) {
		return sysRoleDao.queryList(map);
	}

	/**
	 * 统计所有角色总数
	 */
	public int queryTotal(Map<String, Object> map) {
		return sysRoleDao.queryTotal(map);
	}

	/**
	 * 持久化角色
	 */
	@Transactional
	public void save(SysRoleEntity role) {
		role.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		// 添加角色
		sysRoleDao.save(role);

		// 保存角色与菜单关系
		sysRoleMenuService.saveOrUpdate(role.getRoleId(), role.getMenuIdList());
	}

	/**
	 * 更新角色
	 */
	@Transactional
	public void update(SysRoleEntity role) {
		// 更新角色
		sysRoleDao.update(role);

		// 更新角色与菜单关系
		sysRoleMenuService.saveOrUpdate(role.getRoleId(), role.getMenuIdList());
	}

	/**
	 * 批量删除角色
	 */
	@Transactional
	public void deleteBatch(String[] roleIds) {
		sysRoleDao.deleteBatch(roleIds);
	}
}
