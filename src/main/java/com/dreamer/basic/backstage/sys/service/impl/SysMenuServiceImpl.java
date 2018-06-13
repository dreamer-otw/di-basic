package com.dreamer.basic.backstage.sys.service.impl;

import com.dreamer.basic.backstage.sys.dao.SysMenuDao;
import com.dreamer.basic.backstage.sys.data.SysMenuData;
import com.dreamer.basic.backstage.sys.service.SysMenuService;
import com.dreamer.basic.backstage.sys.utils.Constant;
import com.dreamer.basic.backstage.sys.utils.Page;
import com.dreamer.basic.common.generator.dao.SysMenuMapper;
import com.dreamer.basic.common.generator.dao.SysRoleMenuMapper;
import com.dreamer.basic.common.generator.dao.SysUserRoleMapper;
import com.dreamer.basic.common.generator.entity.*;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * >
 * author : dreamer
 * email : dreamers_otw@163.com
 * date : 2018/5/14 16:46
 */
@Service
public class SysMenuServiceImpl implements SysMenuService {
    @Autowired
    private SysMenuMapper sysMenuMapper;
    @Autowired
    private SysMenuDao sysMenuDao;
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;
    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Override
    public Page<SysMenu> getMenuList(Integer pageNo, Integer pageSize) {
        long count = sysMenuMapper.countByExample(null);
        int startNum = (pageNo - 1) * pageSize;
        int endNum = pageNo * pageSize - 1;
        List<SysMenu> menuList = sysMenuDao.getMenuList(startNum, endNum);
        Page<SysMenu> sysMenuPage = new Page<>();
        sysMenuPage.setPageNo(pageNo);
        sysMenuPage.setPageSize(pageSize);
        sysMenuPage.setTotalRecord(count);
        sysMenuPage.setResults(menuList);
        return sysMenuPage;
    }

    @Override
    public List<SysMenuData> getMenuTree(Integer userId) {
        if (userId == Constant.ROOT_USER) {//超级管理员
            List<SysMenuData> menuList = sysMenuDao.getMenuDataList();
            return getMenuTree(menuList);
        } else {
            List<SysMenuData> sysMenus = new ArrayList<>();
            SysUserRoleExample sysUserRoleExample = new SysUserRoleExample();
            sysUserRoleExample.createCriteria().andUserIdIsNotNull().andUserIdEqualTo(userId);
            List<SysUserRole> sysUserRoles = sysUserRoleMapper.selectByExample(sysUserRoleExample);
            for (SysUserRole sysUserRole : sysUserRoles) {
                SysRoleMenuExample sysRoleMenuExample = new SysRoleMenuExample();
                sysRoleMenuExample.createCriteria().andRoleIdIsNotNull().andRoleIdEqualTo(sysUserRole.getRoleId());
                List<SysRoleMenu> sysRoleMenus = sysRoleMenuMapper.selectByExample(sysRoleMenuExample);
                for (SysRoleMenu sysRoleMenu : sysRoleMenus) {
                    SysMenuData sysMenuData = sysMenuDao.getMenuByMenuId(sysRoleMenu.getMenuId());
                    sysMenus.add(sysMenuData);
                }
            }
            return getMenuTree(sysMenus);
        }
    }

    @Override
    public int insertMenu(SysMenu sysMenu) {
        return sysMenuMapper.insertSelective(sysMenu);
    }

    @Override
    public int updateMenu(SysMenu sysMenu) {
        return sysMenuMapper.updateByPrimaryKeySelective(sysMenu);
    }

    @Override
    public int delMenu(String[] menuIds) {
        int num = 0;
        for (int i = 0; i < menuIds.length; i++) {
            num += sysMenuMapper.deleteByPrimaryKey(Integer.valueOf(menuIds[i]));
        }
        return num;
    }

    @Override
    public SysMenuData getMenuInfoByMenuId(String menuId) {
        return sysMenuDao.getMenuByMenuId(Integer.valueOf(menuId));
    }

    /**
     *
     * @param sysMenuDatas      [菜单集合]
     * @return  []
     */
    private List<SysMenuData> getMenuTree(List<SysMenuData> sysMenuDatas) {
        for (SysMenuData sysMenuData : sysMenuDatas) {
            if (sysMenuData.getMenuType() == 0) {
                sysMenuData.setOpen(false);
            }
        }
        return sysMenuDatas;
    }

    /**
     * 根据父ID查询子菜单或按钮
     * @param parentId  [父ID]
     */
    private List<SysMenu> getMenuByParentId(Integer parentId) {
        SysMenuExample sysMenuExample = new SysMenuExample();
        sysMenuExample.createCriteria()
                .andParentIdIsNotNull()
                .andParentIdEqualTo(parentId);
        return sysMenuMapper.selectByExample(sysMenuExample);
    }

}
