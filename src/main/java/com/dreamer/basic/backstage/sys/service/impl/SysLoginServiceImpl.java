package com.dreamer.basic.backstage.sys.service.impl;

import com.dreamer.basic.backstage.sys.data.SysMenuData;
import com.dreamer.basic.backstage.sys.service.SysLoginService;
import com.dreamer.basic.backstage.sys.utils.Constant;
import com.dreamer.basic.common.generator.dao.SysMenuMapper;
import com.dreamer.basic.common.generator.dao.SysRoleMenuMapper;
import com.dreamer.basic.common.generator.dao.SysUserMapper;
import com.dreamer.basic.common.generator.dao.SysUserRoleMapper;
import com.dreamer.basic.common.generator.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * >
 * author : dreamer
 * email : dreamers_otw@163.com
 * date : 2018/5/16 15:32
 */
@Service
public class SysLoginServiceImpl implements SysLoginService {
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;
    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;
    @Autowired
    private SysMenuMapper sysMenuMapper;
    @Autowired
    private SysUserMapper sysUserMapper;
    @Override
    public List<SysMenuData> getMenuListByUserId(int userId) {
        List<SysMenuData> sysMenuData = null;
        //超级管理员权限
        if (userId == 0) {
            SysMenuExample sysMenuExample = new SysMenuExample();
            sysMenuExample.createCriteria().andMenuTypeIsNotNull()
                    //0：目录；1：菜单；2：按钮
                    .andMenuTypeIn(Arrays.asList(0, 1, 2));
            List<SysMenu> sysMenus = sysMenuMapper.selectByExample(sysMenuExample);
            List<Integer> menuIdList = new ArrayList<>();
            for (SysMenu sysMenu : sysMenus) {
                menuIdList.add(sysMenu.getMenuId());
            }
            return getMenusAll(menuIdList);
        } else {//用户
            List<SysMenu> sysMenus = new ArrayList<>();
            SysUserRoleExample sysUserRoleExample = new SysUserRoleExample();
            sysUserRoleExample.createCriteria().andUserIdIsNotNull().andUserIdEqualTo(userId);
            List<SysUserRole> sysUserRoles = sysUserRoleMapper.selectByExample(sysUserRoleExample);
            List<Integer> menuIdList = null;
            for (SysUserRole sysUserRole : sysUserRoles) {
                SysRoleMenuExample sysRoleMenuExample = new SysRoleMenuExample();
                sysRoleMenuExample.createCriteria().andRoleIdIsNotNull().andRoleIdEqualTo(sysUserRole.getRoleId());
                List<SysRoleMenu> sysRoleMenus = sysRoleMenuMapper.selectByExample(sysRoleMenuExample);
                for (SysRoleMenu sysRoleMenu : sysRoleMenus) {
                    menuIdList.add(sysRoleMenu.getMenuId());
                    SysMenu sysMenu = sysMenuMapper.selectByPrimaryKey(sysRoleMenu.getMenuId());
                    sysMenus.add(sysMenu);
                }
            }
            return getMenusAll(menuIdList);
        }
    }

    @Override
    public int updatePwdByUserId(String password, int userId) {
        SysUser sysUser = new SysUser();
        sysUser.setUserId(userId);
        sysUser.setUserPwd(password);
        return sysUserMapper.updateByPrimaryKeySelective(sysUser);
    }

    /**
     * 获取所有菜单
     * @param menuIdList    [目录idList]
     */
    private List<SysMenuData> getMenusAll(List<Integer> menuIdList) {
        //获取根菜单（即目录）
        List<SysMenu> rootMenus = getMenuByParentId(0, menuIdList);
        //递归获取所有菜单
        return getMenuTreeList(rootMenus, menuIdList);
    }

    /**
     * 根据父菜单ID获取子菜单
     */
    private List<SysMenu> getMenuByParentId(int parentId, List<Integer> menuIdList) {
        SysMenuExample sysMenuExample = new SysMenuExample();
        sysMenuExample.createCriteria().andParentIdEqualTo(parentId).andParentIdIsNotNull();
        List<SysMenu> sysMenus = sysMenuMapper.selectByExample(sysMenuExample);
        if (menuIdList == null) {
            return sysMenus;
        }
        List<SysMenu> sysMenuList = new ArrayList<>();
        for (SysMenu menu : sysMenus) {
            if (menuIdList.contains(menu.getMenuId())) {
                sysMenuList.add(menu);
            }
        }
        return sysMenuList;
    }

    /**
     * 递归
     */
    private List<SysMenuData> getMenuTreeList(List<SysMenu> sysMenus, List<Integer> menuIdList) {
        List<SysMenuData> subMenuList = new ArrayList<>();
        for (SysMenu entity : sysMenus) {
            SysMenuData sysMenuData = new SysMenuData();
            sysMenuData.setMenuId(entity.getMenuId());
            sysMenuData.setParentId(entity.getParentId());
            sysMenuData.setMenuType(entity.getMenuType());
            sysMenuData.setMenuName(entity.getMenuName());
            sysMenuData.setMenuUrl(entity.getMenuUrl());
            sysMenuData.setIcon(entity.getIcon());
            sysMenuData.setOrderNum(entity.getOrderNum());
            if (sysMenuData.getMenuType() == 0) {// 目录
                sysMenuData.setList(getMenuTreeList(getMenuByParentId(sysMenuData.getMenuId(), menuIdList), menuIdList));
            }
            subMenuList.add(sysMenuData);
        }
        return subMenuList;
    }
}
