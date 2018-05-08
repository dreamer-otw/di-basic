package com.dreamer.basic.backstage.sys.controller;

import com.dreamer.basic.backstage.sys.utils.ShiroUtils;
import com.dreamer.basic.common.generator.entity.SysUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controller公共组件
 * @author R & D
 * @email 908350381@qq.com
 * @date 2016年11月9日 下午9:42:26
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
