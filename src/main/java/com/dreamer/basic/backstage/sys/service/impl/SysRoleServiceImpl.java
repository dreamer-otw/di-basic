package com.dreamer.basic.backstage.sys.service.impl;

import com.dreamer.basic.backstage.sys.dao.SysRoleDao;
import com.dreamer.basic.backstage.sys.data.SysRoleData;
import com.dreamer.basic.backstage.sys.service.SysRoleService;
import com.dreamer.basic.backstage.sys.utils.Page;
import com.dreamer.basic.common.dao.BaseCommonDao;
import com.dreamer.basic.common.generator.dao.SysRoleMapper;
import com.dreamer.basic.common.generator.dao.SysRoleMenuMapper;
import com.dreamer.basic.common.generator.entity.SysRole;
import com.dreamer.basic.common.generator.entity.SysRoleMenu;
import com.dreamer.basic.common.generator.entity.SysRoleMenuExample;
import com.dreamer.basic.common.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * > 角色管理Service实现
 *
 * @author : dreamer-otw
 * @email : dreamers_otw@163.com
 * @date : 2018/07/24 10:41
 */
@Service
@Transactional(readOnly = true)
public class SysRoleServiceImpl implements SysRoleService{
    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysRoleDao sysRoleDao;
    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;
    @Autowired
    private BaseCommonDao baseCommonDao;
    @Override
    public List<SysRole> getRoleList() {
        return sysRoleMapper.selectByExample(null);
    }

    @Override
    @Transactional
    public void saveRole(SysRoleData sysRoleData) {
        String uuid = baseCommonDao.getUUID();
        SysRole sysRole = new SysRole();
        sysRole.setRoleId(uuid);
        sysRole.setRoleName(sysRoleData.getRoleName());
        sysRole.setRemark(sysRoleData.getRemark());
        sysRole.setCreateTime(DateUtil.format(new Date(),DateUtil.NEW_FORMAT));

        sysRoleMapper.insertSelective(sysRole);
        for (String menuId : sysRoleData.getMenuIdList()) {
            SysRoleMenu sysRoleMenu = new SysRoleMenu();
            sysRoleMenu.setId(baseCommonDao.getUUID());
            sysRoleMenu.setMenuId(menuId);
            sysRoleMenu.setRoleId(uuid);
            sysRoleMenuMapper.insert(sysRoleMenu);
        }
    }

    @Override
    @Transactional
    public void updateRole(SysRoleData sysRoleData) {
        SysRole sysRole = new SysRole();
        sysRole.setRoleId(sysRoleData.getRoleId());
        sysRole.setRoleName(sysRoleData.getRoleName());
        sysRole.setRemark(sysRoleData.getRemark());
        sysRoleMapper.updateByPrimaryKeySelective(sysRole);
        for (String menuId : sysRoleData.getMenuIdList()) {
            SysRoleMenu sysRoleMenu = new SysRoleMenu();
            sysRoleMenu.setMenuId(menuId);
            SysRoleMenuExample sysRoleMenuExample = new SysRoleMenuExample();
            sysRoleMenuExample.createCriteria().andRoleIdIsNotNull().andRoleIdEqualTo(sysRoleData.getRoleId());
            sysRoleMenuMapper.updateByExampleSelective(sysRoleMenu, sysRoleMenuExample);
        }
    }

    @Override
    @Transactional
    public void delRole(String[] roleIds) {
        for (String roleId : roleIds) {
            sysRoleMapper.deleteByPrimaryKey(roleId);
            SysRoleMenuExample sysRoleMenuExample = new SysRoleMenuExample();
            sysRoleMenuExample.createCriteria().andRoleIdIsNotNull().andRoleIdEqualTo(roleId);
            sysRoleMenuMapper.deleteByExample(sysRoleMenuExample);
        }
    }

    @Override
    public SysRoleData getRoleInfo(String roleId) {
        SysRole sysRole = sysRoleMapper.selectByPrimaryKey(roleId);
        SysRoleData sysRoleData = new SysRoleData();
        sysRoleData.setRoleId(sysRole.getRoleId());
        sysRoleData.setRoleName(sysRole.getRoleName());
        sysRoleData.setRemark(sysRole.getRemark());
        sysRoleData.setCreateTime(sysRole.getCreateTime());
        SysRoleMenuExample sysRoleMenuExample = new SysRoleMenuExample();
        sysRoleMenuExample.createCriteria().andRoleIdIsNotNull().andRoleIdEqualTo(roleId);
        List<SysRoleMenu> sysRoleMenus = sysRoleMenuMapper.selectByExample(sysRoleMenuExample);
        List<String> menuIds = new ArrayList<>();
        for (SysRoleMenu sysRoleMenu : sysRoleMenus) {
            menuIds.add(sysRoleMenu.getMenuId());
        }
        sysRoleData.setMenuIdList(menuIds);
        return sysRoleData;
    }

    @Override
    public Page<SysRole> getRoleListPage(Integer pageNo, Integer pageSize) {
        long count = sysRoleMapper.countByExample(null);
        int startNum = (pageNo - 1) * pageSize;
        int endNum = pageNo * pageSize - 1;
        List<SysRole> roleListPage = sysRoleDao.getRoleListPage(startNum, endNum);
        Page page = new Page();
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        page.setResults(roleListPage);
        page.setTotalRecord(count);
        return page;
    }
}
