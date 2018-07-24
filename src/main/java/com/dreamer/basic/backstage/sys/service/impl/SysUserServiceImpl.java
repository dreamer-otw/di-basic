package com.dreamer.basic.backstage.sys.service.impl;

import com.dreamer.basic.backstage.sys.dao.SysUserDao;
import com.dreamer.basic.backstage.sys.service.SysUserService;
import com.dreamer.basic.backstage.sys.utils.Page;
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
    @Autowired
    private SysUserDao sysUserDao;


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

    @Override
    public Page<SysUser> getUserList(Integer pageNo, Integer pageSize) {
        long count = sysUserMapper.countByExample(null);
        int startNum = (pageNo - 1) * pageSize;
        int endNum = pageNo * pageSize - 1;
        List<SysUser> menuList = sysUserDao.getUserList(startNum, endNum);
        Page<SysUser> sysUserPage = new Page<>();
        sysUserPage.setPageNo(pageNo);
        sysUserPage.setPageSize(pageSize);
        sysUserPage.setTotalRecord(count);
        sysUserPage.setResults(menuList);
        return sysUserPage;
    }

    @Override
    public int delUser(Integer[] userIds) {
        int num = 0;
        for (Integer userId : userIds) {
            num += sysUserMapper.deleteByPrimaryKey(userId);
        }
        return num;
    }

    @Override
    public int updateUser(SysUser sysUser) {
        return sysUserMapper.updateByPrimaryKey(sysUser);
    }

    @Override
    public int saveUser(SysUser sysUser) {
        return sysUserMapper.insert(sysUser);
    }
}
