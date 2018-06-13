package com.dreamer.basic.backstage.sys.dao;

import com.dreamer.basic.backstage.sys.data.SysMenuData;
import com.dreamer.basic.common.generator.entity.SysMenu;

import java.util.List;


/**
 * >
 * author : dreamer
 * email : dreamers_otw@163.com
 * date : 2018/5/17 10:49
 */
public interface SysMenuDao {
    List<SysMenu> getMenuList(Integer startNum, Integer endNum);
    SysMenuData getMenuByMenuId(Integer menuId);
    List<SysMenuData> getMenuDataList();
}
