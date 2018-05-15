package com.dreamer.basic.backstage.sys.service;

import com.dreamer.basic.common.generator.entity.SysUser;
import org.springframework.stereotype.Service;

/**
 * >
 * author : dreamer
 * email : dreamers_otw@163.com
 * date : 2018/5/14 15:17
 */
@Service
public interface SysUserService {
    //根据userId查询用户信息
    SysUser getUserById(String userId);

    //根据userAccount查询用户信息
    SysUser getUserByAccount(String userAccount);

    //根据Username查询用户信息
    SysUser getUserByUsername(String username);

}
