package com.dreamer.basic.backstage.sys.service.impl;

import com.dreamer.basic.backstage.sys.service.SysMenuService;
import com.dreamer.basic.backstage.sys.utils.Constant;
import com.dreamer.basic.backstage.sys.utils.Page;
import com.dreamer.basic.common.generator.dao.SysMenuMapper;
import com.dreamer.basic.common.generator.dao.SysRoleMenuMapper;
import com.dreamer.basic.common.generator.dao.SysUserRoleMapper;
import com.dreamer.basic.common.generator.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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

    @Override
    public List<SysMenu> getMenuList() {
        return null;
    }
}
