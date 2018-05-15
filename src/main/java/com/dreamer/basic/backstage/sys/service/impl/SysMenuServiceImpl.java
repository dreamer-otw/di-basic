package com.dreamer.basic.backstage.sys.service.impl;

import com.dreamer.basic.backstage.sys.service.SysMenuService;
import com.dreamer.basic.common.generator.dao.SysMenuMapper;
import com.dreamer.basic.common.generator.dao.SysRoleMenuMapper;
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
 * date : 2018/5/14 16:46
 */
@Service
public class SysMenuServiceImpl implements SysMenuService {
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;
    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;
    @Autowired
    private SysMenuMapper sysMenuMapper;
    @Override
    public List<SysMenu> getMenuListByUserId(String userId) {
        //超级管理员权限
        if ("000000".equals(userId)) {
            SysMenuExample sysMenuExample = new SysMenuExample();
            sysMenuExample.createCriteria().andMenuTypeIsNotNull()
                    //00：目录；01：菜单；02：按钮
                    .andMenuTypeIn(Arrays.asList("00","01","02"));
            return sysMenuMapper.selectByExample(sysMenuExample);
        } else {//用户
            List<SysMenu> sysMenus = new ArrayList<>();
            SysUserRoleExample sysUserRoleExample = new SysUserRoleExample();
            sysUserRoleExample.createCriteria().andUserIdIsNotNull().andUserIdEqualTo(userId);
            List<SysUserRole> sysUserRoles = sysUserRoleMapper.selectByExample(sysUserRoleExample);
            for (SysUserRole sysUserRole : sysUserRoles) {
                SysRoleMenuExample sysRoleMenuExample = new SysRoleMenuExample();
                sysRoleMenuExample.createCriteria().andRoleIdIsNotNull().andRoleIdEqualTo(sysUserRole.getRoleId());
                List<SysRoleMenu> sysRoleMenus = sysRoleMenuMapper.selectByExample(sysRoleMenuExample);
                for (SysRoleMenu sysRoleMenu : sysRoleMenus) {
                    SysMenu sysMenu = sysMenuMapper.selectByPrimaryKey(sysRoleMenu.getMenuId());
                    sysMenus.add(sysMenu);
                }
            }
            return sysMenus;
        }
    }
}
