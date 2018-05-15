package com.dreamer.basic.backstage.sys.controller;

import com.dreamer.basic.backstage.sys.utils.ShiroUtils;
import com.dreamer.basic.common.generator.entity.SysUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * > Controller 公共组件
 * author : dreamer
 * email : dreamers_otw@163.com
 * date : 2018/5/13 12:13
 */
public abstract class AbstractController {
	protected Logger logger = LoggerFactory.getLogger(getClass());

	protected SysUser getUser() {
		return ShiroUtils.getUserEntity();
	}

	protected String getUserId() {
		return getUser().getUserId();
	}
}
