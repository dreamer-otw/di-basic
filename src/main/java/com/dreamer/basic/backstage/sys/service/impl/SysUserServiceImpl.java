package com.dreamer.basic.backstage.sys.service.impl;

import com.dreamer.basic.backstage.sys.dao.SysUserDao;
import com.dreamer.basic.backstage.sys.data.SysUserData;
import com.dreamer.basic.backstage.sys.service.SysUserService;
import com.dreamer.basic.backstage.sys.utils.Page;
import com.dreamer.basic.common.dao.BaseCommonDao;
import com.dreamer.basic.common.generator.dao.SysUserMapper;
import com.dreamer.basic.common.generator.dao.SysUserRoleMapper;
import com.dreamer.basic.common.generator.entity.SysUser;
import com.dreamer.basic.common.generator.entity.SysUserExample;
import com.dreamer.basic.common.generator.entity.SysUserRole;
import com.dreamer.basic.common.generator.entity.SysUserRoleExample;
import com.dreamer.basic.common.util.DateUtil;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * >
 * author : dreamer
 * email : dreamers_otw@163.com
 * date : 2018/5/14 15:13
 */
@Service
@Transactional(readOnly = true)
public class SysUserServiceImpl implements SysUserService{
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysUserDao sysUserDao;
    @Autowired
    private BaseCommonDao baseCommonDao;
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Override
    public SysUserData getUserInfoById(String userId) {
        SysUser sysUser= sysUserMapper.selectByPrimaryKey(userId);
        SysUserRoleExample sysUserRoleExample = new SysUserRoleExample();
        sysUserRoleExample.createCriteria().andUserIdIsNotNull().andUserIdEqualTo(userId);
        List<SysUserRole> sysUserRoles = sysUserRoleMapper.selectByExample(sysUserRoleExample);
        List<String> roleIds = new ArrayList<>();
        for (SysUserRole sysUserRole : sysUserRoles) {
            roleIds.add(sysUserRole.getRoleId());
        }
        SysUserData sysUserData = new SysUserData();
        sysUserData.setUserId(sysUser.getUserId());
        sysUserData.setUserAccount(sysUser.getUserAccount());
        sysUserData.setUsername(sysUser.getUsername());
        sysUserData.setUserAlias(sysUser.getUserAlias());
        sysUserData.setUserPwd(sysUser.getUserPwd());
        sysUserData.setEmail(sysUser.getEmail());
        sysUserData.setMobile(sysUser.getMobile());
        sysUserData.setCreateTime(sysUser.getCreateTime());
        sysUserData.setUserStatus(sysUser.getUserStatus());
        sysUserData.setRoleIdList(roleIds);
        return sysUserData;
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
    @Transactional
    public int delUser(String[] userIds) {
        int num = 0;
        for (String userId : userIds) {
            num += sysUserMapper.deleteByPrimaryKey(userId);
            SysUserRoleExample sysUserRoleExample = new SysUserRoleExample();
            sysUserRoleExample.createCriteria().andUserIdIsNotNull().andUserIdEqualTo(userId);
            sysUserRoleMapper.deleteByExample(sysUserRoleExample);
        }
        return num;
    }

    @Override
    @Transactional
    public void updateUser(SysUserData sysUserData) {
        SysUser sysUser = new SysUser();
        sysUser.setUserId(sysUserData.getUserId());
        sysUser.setUserAccount(sysUserData.getUserAccount());
        sysUser.setUserPwd(new Sha256Hash(sysUserData.getUserPwd()).toHex());
        sysUser.setUsername(sysUserData.getUsername());
        sysUser.setUserAlias(sysUserData.getUserAlias());
        sysUser.setEmail(sysUserData.getEmail());
        sysUser.setMobile(sysUserData.getMobile());
        sysUser.setUserStatus(sysUserData.getUserStatus());
        sysUserMapper.updateByPrimaryKeySelective(sysUser);
        for (String roleId : sysUserData.getRoleIdList()) {
            SysUserRoleExample sysUserRoleExample = new SysUserRoleExample();
            sysUserRoleExample.createCriteria().andUserIdIsNotNull().andUserIdEqualTo(sysUserData.getUserId());
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setRoleId(roleId);
            sysUserRoleMapper.updateByExampleSelective(sysUserRole, sysUserRoleExample);
        }
    }

    @Override
    @Transactional
    public void saveUser(SysUserData sysUserData) {
        String userId = baseCommonDao.getUUID();
        SysUser sysUser = new SysUser();
        sysUser.setUserId(userId);
        sysUser.setUserAccount(sysUserData.getUserAccount());
        sysUser.setUserPwd(new Sha256Hash(sysUserData.getUserPwd()).toHex());
        sysUser.setUsername(sysUserData.getUsername());
        sysUser.setUserAlias(sysUserData.getUserAlias());
        sysUser.setEmail(sysUserData.getEmail());
        sysUser.setMobile(sysUserData.getMobile());
        sysUser.setUserStatus(sysUserData.getUserStatus());
        sysUser.setCreateTime(DateUtil.format(new Date(), DateUtil.NEW_FORMAT));
        sysUserMapper.insert(sysUser);
        for (String roleId : sysUserData.getRoleIdList()) {
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setId(baseCommonDao.getUUID());
            sysUserRole.setUserId(userId);
            sysUserRole.setRoleId(roleId);
            sysUserRoleMapper.insert(sysUserRole);
        }
    }
}
