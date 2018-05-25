package com.dreamer.basic.backstage.sys.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * > 页面跳转路由
 * author : dreamer-otw
 * email : dreamers_otw@163.com
 * date : 2018/5/18 15:26
 */
@Controller
public class SysPageController {
    @RequestMapping("sys/{url}.html")
    public String page(@PathVariable("url") String url) {
        return "sys/" + url;
    }
}
