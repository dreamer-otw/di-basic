package com.dreamer.basic.backstage.sys.service.impl;

import com.dreamer.basic.backstage.sys.dao.SysRoleDao;
import com.dreamer.basic.backstage.sys.data.SysRoleData;
import com.dreamer.basic.backstage.sys.service.SysRoleService;
import com.dreamer.basic.backstage.sys.utils.Page;
import com.dreamer.basic.common.generator.dao.SysRoleMapper;
import com.dreamer.basic.common.generator.entity.SysRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * > 角色管理Service实现
 *
 * @author : dreamer-otw
 * @email : dreamers_otw@163.com
 * @date : 2018/07/24 10:41
 */
@Service
public class SysRoleServiceImpl implements SysRoleService{
    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysRoleDao sysRoleDao;
    @Override
    public List<SysRole> getRoleList() {
        return sysRoleMapper.selectByExample(null);
    }

    @Override
    public int saveRole(SysRoleData sysRoleData) {
        return 0;
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
