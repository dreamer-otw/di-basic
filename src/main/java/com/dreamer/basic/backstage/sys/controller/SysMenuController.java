package com.dreamer.basic.backstage.sys.controller;

import com.dreamer.basic.backstage.sys.data.SysMenuData;
import com.dreamer.basic.backstage.sys.service.SysMenuService;
import com.dreamer.basic.backstage.sys.utils.Page;
import com.dreamer.basic.backstage.sys.utils.Result;
import com.dreamer.basic.backstage.sys.utils.SpringUtil;
import com.dreamer.basic.common.generator.entity.SysMenu;
import com.dreamer.basic.common.util.SpringUtils;
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

//    @Autowired
//    private SysMenuService sysMenuService;
//
    SysMenuService sysMenuService;
    public void SysMenuController() {
        sysMenuService = SpringUtil.getBean("sysMenuService",SysMenuService.class);
    }

    @GetMapping("/list")
    @ResponseBody
    public Page<SysMenu> getMenuList(String pageNo, String pageSize, String sidx, String odrer) {
        Page<SysMenu> menuList = sysMenuService.getMenuList(Integer.valueOf(pageNo), Integer.valueOf(pageSize));
        return menuList;
    }

    @RequestMapping(value = "/getMenuTree", method = RequestMethod.GET)
    @ResponseBody
    public Result getMentTree() {
        List<SysMenuData> menuTree = sysMenuService.getMenuTree(getUserId());
        Map<String,Object> retData = new HashMap<>();
        retData.put("menuTree", menuTree);
        return Result.ok(retData);
    }

    @PostMapping("/save")
    @ResponseBody
    public Result saveMenu(@RequestBody SysMenu menu) {
        int num = sysMenuService.insertMenu(menu);
        if (num > 0) {
            return Result.ok("添加成功");
        }
        return Result.error("添加失败");
    }

    @PostMapping("/update")
    @ResponseBody
    public Result updateMenu(@RequestBody SysMenu menu) {
        int num = sysMenuService.updateMenu(menu);
        if (num > 0) {
            return Result.ok("修改成功");
        }
        return Result.error("修改失败");
    }

    @PostMapping("/delMenu")
    @ResponseBody
    public Result delMenu(@RequestBody String[] menuIds) {
        int num = sysMenuService.delMenu(menuIds);
        if (num > 0) {
            return Result.ok("删除成功");
        }
        return Result.error("删除失败");
    }

    @GetMapping("/menuInfo/{menuId}")
    @ResponseBody
    public Result getMenuInfoByMenuId(@PathVariable("menuId") String menuId) {
        SysMenuData menuInfo = sysMenuService.getMenuInfoByMenuId(menuId);
        if (menuInfo != null) {
            Map<String, Object> retData = new HashMap<>();
            retData.put("menuInfo", menuInfo);
            return Result.ok(retData);
        }
        return Result.error("获取失败");
    }

}
