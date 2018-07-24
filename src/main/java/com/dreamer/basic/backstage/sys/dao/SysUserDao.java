package com.dreamer.basic.backstage.sys.dao;

import com.dreamer.basic.common.generator.entity.SysUser;

import java.util.List;

public interface SysUserDao {
    List<SysUser> getUserList(Integer startNum, Integer endNum);
    int delUser(String userId);
}
