package com.dreamer.basic.backstage.sys.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 系统页面视图
 * @author R & D
 * @email 908350381@qq.com
 * @date 2016年11月24日 下午11:05:27
 */
@Controller
public class SysPageController {
	@RequestMapping("sys/{url}.html")
	public String page(@PathVariable("url") String url) {
		return "sys/" + url + ".html";
	}
	@RequestMapping("biz/{url}.html")
	public String pages(@PathVariable("url") String url) {
		return "biz/" + url + ".html";
	}
}
