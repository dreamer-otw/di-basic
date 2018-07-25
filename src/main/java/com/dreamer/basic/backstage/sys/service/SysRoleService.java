package com.dreamer.basic.backstage.sys.service;

import com.dreamer.basic.backstage.sys.data.SysRoleData;
import com.dreamer.basic.backstage.sys.utils.Page;
import com.dreamer.basic.common.generator.entity.SysRole;

import java.util.List;


/**
 * >
 *
 * @author : dreamer-otw
 * @email : dreamers_otw@163.com
 * @date : 2018/07/24 10:40
 */
public interface SysRoleService {
    Page<SysRole> getRoleListPage(Integer pageNo, Integer pageSize);
    List<SysRole> getRoleList();
    void saveRole(SysRoleData sysRoleData);
    void updateRole(SysRoleData sysRoleData);
    void delRole(String[] roleIds);
    SysRoleData getRoleInfo(String roleId);
}
