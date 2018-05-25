package com.dreamer.basic.backstage.sys.service.impl;

import com.dreamer.basic.backstage.sys.dao.SysMenuDao;
import com.dreamer.basic.backstage.sys.service.SysMenuService;
import com.dreamer.basic.backstage.sys.utils.Page;
import com.dreamer.basic.common.generator.dao.SysMenuMapper;
import com.dreamer.basic.common.generator.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * >
 * author : dreamer
 * email : dreamers_otw@163.com
 * date : 2018/5/14 16:46
 */
@Service
public class SysMenuServiceImpl implements SysMenuService {
    @Autowired
    private SysMenuMapper sysMenuMapper;
    @Autowired
    private SysMenuDao sysMenuDao;

    @Override
    public Page<SysMenu> getMenuList(Integer pageNo, Integer pageSize) {
        long count = sysMenuMapper.countByExample(null);
        int startNum = (pageNo - 1) * pageSize + 1;
        int endNum = pageNo * pageSize;
        List<SysMenu> menuList = sysMenuDao.getMenuList(startNum, endNum);
        Page<SysMenu> sysMenuPage = new Page<>();
        sysMenuPage.setPageNo(pageNo);
        sysMenuPage.setPageSize(pageSize);
        sysMenuPage.setTotalRecord(count);
        sysMenuPage.setResults(menuList);
        return sysMenuPage;
    }
}
