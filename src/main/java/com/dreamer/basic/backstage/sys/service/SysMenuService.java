package com.dreamer.basic.backstage.sys.service;

import com.dreamer.basic.backstage.sys.data.SysMenuData;
import com.dreamer.basic.backstage.sys.utils.Page;
import com.dreamer.basic.common.generator.entity.SysMenu;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * >
 * author : dreamer
 * email : dreamers_otw@163.com
 * date : 2018/5/14 16:38
 */
@Service("sysMenuService")
public interface SysMenuService {
    Page<SysMenu> getMenuList(Integer pageNo, Integer pageSize);
    List<SysMenuData> getMenuTree(Integer userId);
    int insertMenu(SysMenu sysMenu);
    int updateMenu(SysMenu sysMenu);
    int delMenu(String[] menuIds);
    SysMenuData getMenuInfoByMenuId(String menuId);
}
