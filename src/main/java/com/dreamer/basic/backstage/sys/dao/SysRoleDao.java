package com.dreamer.basic.backstage.sys.dao;

import com.dreamer.basic.common.generator.entity.SysRole;

import java.util.List;

/**
 * > 角色dao接口
 *
 * @author : dreamer-otw
 * @email : dreamers_otw@163.com
 * @date : 2018/07/24 13:34
 */
public interface SysRoleDao {
    List<SysRole> getRoleListPage(Integer startNum, Integer endNum);
}
