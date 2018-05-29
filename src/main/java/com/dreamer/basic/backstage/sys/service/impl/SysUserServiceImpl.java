package com.dreamer.basic.backstage.sys.service.impl;

import com.dreamer.basic.backstage.sys.service.SysUserService;
import com.dreamer.basic.common.generator.dao.SysUserMapper;
import com.dreamer.basic.common.generator.entity.SysUser;
import com.dreamer.basic.common.generator.entity.SysUserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * >
 * author : dreamer
 * email : dreamers_otw@163.com
 * date : 2018/5/14 15:13
 */
@Service
public class SysUserServiceImpl implements SysUserService{
    @Autowired
    private SysUserMapper sysUserMapper;


    @Override
    public SysUser getUserById(int userId) {
        return sysUserMapper.selectByPrimaryKey(userId);
    }

    @Override
    public SysUser getUserByAccount(String userAccount) {
        SysUserExample sysUserExample = new SysUserExample();
        sysUserExample.createCriteria().andUserAccountEqualTo(userAccount).andUserAccountIsNotNull();
        List<SysUser> sysUsers = sysUserMapper.selectByExample(sysUserExample);
        if (sysUsers != null && sysUsers.size() == 1) {
            return sysUsers.get(0);
        } else if (sysUsers.size() > 1) { //TODO 该用户异常
        /*return new Exception("该用户异常");*/
        }
            return null;
    }

    @Override
    public SysUser getUserByUsername(String username) {
        SysUserExample sysUserExample = new SysUserExample();
        sysUserExample.createCriteria().andUsernameEqualTo(username).andUsernameIsNotNull();
        List<SysUser> sysUsers = sysUserMapper.selectByExample(sysUserExample);
        if (sysUsers != null && sysUsers.size() == 1) {
            return sysUsers.get(0);
        } else if (sysUsers.size() > 1) { //TODO 该用户异常
        /*return new Exception("该用户异常");*/
        }
        return null;
    }
}
