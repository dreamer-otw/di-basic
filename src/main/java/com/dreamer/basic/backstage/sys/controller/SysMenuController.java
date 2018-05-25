package com.dreamer.basic.backstage.sys.controller;

import com.dreamer.basic.backstage.sys.service.SysMenuService;
import com.dreamer.basic.backstage.sys.utils.Page;
import com.dreamer.basic.common.generator.entity.SysMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
    public Page<SysMenu> getMenuList() {
        Page<SysMenu> menuList = sysMenuService.getMenuList(1, 10);
        return menuList;
    }
}
