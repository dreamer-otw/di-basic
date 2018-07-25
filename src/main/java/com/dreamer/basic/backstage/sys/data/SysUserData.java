package com.dreamer.basic.backstage.sys.data;

import com.dreamer.basic.common.generator.entity.SysUser;

import java.util.List;

/**
 * >
 *
 * @author : dreamer-otw
 * @email : dreamers_otw@163.com
 * @date : 2018/07/25 18:11
 */
public class SysUserData extends SysUser{
    List<String> roleIdList;

    public List<String> getRoleIdList() {
        return roleIdList;
    }

    public void setRoleIdList(List<String> roleIdList) {
        this.roleIdList = roleIdList;
    }
}
