package com.dreamer.basic.backstage.sys.Exception;

import com.alibaba.fastjson.JSON;
import com.dreamer.basic.backstage.sys.utils.Result;
import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * > 异常处理器
 * author : dreamer
 * email : dreamers_otw@163.com
 * date : 2018/5/14 15:13
 */
@Component
public class RRExceptionHandler implements HandlerExceptionResolver {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
		Result r = new Result();
		try {
			response.setContentType("application/json;charset=utf-8");
			response.setCharacterEncoding("utf-8");

			if (ex instanceof RRException) {
				r.put("code", ((RRException) ex).getCode());
				r.put("msg", ((RRException) ex).getMessage());
			} else if (ex instanceof DuplicateKeyException) {
				r = Result.error("数据库中已存在该记录");
			} else if (ex instanceof AuthorizationException) {
				r = Result.error("没有权限，请联系管理员授权");
			} else {
				r = Result.error();
			}

			// 记录异常日志
			logger.error(ex.getMessage(), ex);

			String json = JSON.toJSONString(r);
			response.getWriter().print(json);
		} catch (Exception e) {
			logger.error("RRExceptionHandler 异常处理失败", e);
		}
		return new ModelAndView();
	}
}
