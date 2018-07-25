package com.dreamer.basic.backstage.sys.service;

import com.dreamer.basic.backstage.sys.data.SysUserData;
import com.dreamer.basic.backstage.sys.utils.Page;
import com.dreamer.basic.common.generator.entity.SysUser;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * >
 * author : dreamer
 * email : dreamers_otw@163.com
 * date : 2018/5/14 15:17
 */
@Service
public interface SysUserService {
    //根据userId查询用户信息
    SysUserData getUserInfoById(String userId);

    //根据userAccount查询用户信息
    SysUser getUserByAccount(String userAccount);

    //根据Username查询用户信息
    SysUser getUserByUsername(String username);
    //分页list
    Page<SysUser> getUserList(Integer pageNo, Integer pageSize);
    //del
    int delUser(String[] userIds);
    //更新
    void updateUser(SysUserData sysUserData);
    //新增
    void saveUser(SysUserData sysUserData);
}
