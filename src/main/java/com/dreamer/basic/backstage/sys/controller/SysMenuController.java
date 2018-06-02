package com.dreamer.basic.backstage.sys.controller;

import com.dreamer.basic.backstage.sys.data.SysMenuData;
import com.dreamer.basic.backstage.sys.service.SysMenuService;
import com.dreamer.basic.backstage.sys.utils.Page;
import com.dreamer.basic.backstage.sys.utils.Result;
import com.dreamer.basic.common.generator.entity.SysMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * > 菜单管理
 * author : dreamer
 * email : dreamers_otw@163.com
 * date : 2018/5/16 18:10
 */
@Controller
@RequestMapping("/sys/menu")
public class SysMenuController extends AbstractController {

    @Autowired
    private SysMenuService sysMenuService;

    @GetMapping("/list")
    @ResponseBody
    public Page<SysMenu> getMenuList(String pageNo, String pageSize, String sidx, String odrer) {
        Page<SysMenu> menuList = sysMenuService.getMenuList(Integer.valueOf(pageNo), Integer.valueOf(pageSize));
        return menuList;
    }
    @RequestMapping(value = "/menuTree", method = RequestMethod.GET)
    @ResponseBody
    public Result getMentTree() {
        List<SysMenuData> menuTree = sysMenuService.getMenuTree(getUserId());
        Map<String,Object> retData = new HashMap<>();
        retData.put("menuTree", menuTree);
        return Result.ok(retData);
    }
}
