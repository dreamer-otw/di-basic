package com.dreamer.basic.backstage.sys.service;

import com.dreamer.basic.common.generator.dao.SysMenuMapper;
import com.dreamer.basic.common.generator.entity.SysMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 菜单管理
 * @author R & D
 * @email 908350381@qq.com
 * @date 2016年9月18日 上午9:42:16
 */
@Service
public class SysMenuService {
	@Autowired
	private SysMenuMapper sysMenuDao;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysRoleMenuService sysRoleMenuService;

	/**
	 * 根据父菜单，查询子菜单
	 * @param parentId 父菜单ID
	 * @param menuIdList 用户菜单ID
	 */
	public List<SysMenu> queryListParentId(String parentId, List<String> menuIdList) {
		List<SysMenu> menuList = sysMenuDao.queryListParentId(parentId);
		if (menuIdList == null) {
			return menuList;
		}

		List<SysMenu> userMenuList = new ArrayList<>();
		for (SysMenu menu : menuList) {
			if (menuIdList.contains(menu.getMenuId())) {
				userMenuList.add(menu);
			}
		}
		return userMenuList;
	}

	/**
	 * 获取不包含按钮的菜单列表
	 */
	public List<SysMenu> queryNotButtonList() {
		return sysMenuDao.queryNotButtonList();
	}

	/**
	 * 获取用户菜单列表
	 */
	public List<SysMenu> getUserMenuList(String userId) {
		// 系统管理员，拥有最高权限
		if (userId.equals(1)) {
			return getAllMenuList(null);
		}

		// 用户菜单列表
		List<String> menuIdList = sysUserService.queryAllMenuId(userId);
		return getAllMenuList(menuIdList);
	}

	/**
	 * 查询菜单
	 */
	public SysMenu queryObject(String menuId) {
		return sysMenuDao.queryObject(menuId);
	}

	/**
	 * 查询菜单列表
	 */
	public List<SysMenu> queryList(Map<String, Object> map) {
		return sysMenuDao.queryList(map);
	}

	/**
	 * 查询总数
	 */
	public int queryTotal(Map<String, Object> map) {
		return sysMenuDao.queryTotal(map);
	}

	/**
	 * 保存菜单
	 */
	public void save(SysMenu menu) {
		sysMenuDao.save(menu);
	}

	/**
	 * 修改
	 */
	public void update(SysMenu menu) {
		sysMenuDao.update(menu);
	}

	/**
	 * 删除
	 */
	@Transactional
	public void deleteBatch(String[] menuIds) {
		sysMenuDao.deleteBatch(menuIds);
	}

	/**
	 * 获取所有菜单列表
	 */
	private List<SysMenu> getAllMenuList(List<String> menuIdList) {
		// 查询根菜单列表
		List<SysMenu> menuList = queryListParentId("0", menuIdList);
		// 递归获取子菜单
		getMenuTreeList(menuList, menuIdList);

		return menuList;
	}

	/**
	 * 递归
	 */
	private List<SysMenu> getMenuTreeList(List<SysMenu> menuList, List<String> menuIdList) {
		List<SysMenu> subMenuList = new ArrayList<SysMenu>();

		for (SysMenu entity : menuList) {
			if (entity.getType() == Constant.MenuType.CATALOG.getValue()) {// 目录
				entity.setList(getMenuTreeList(queryListParentId(entity.getMenuId(), menuIdList), menuIdList));
			}
			subMenuList.add(entity);
		}

		return subMenuList;
	}
}
