package com.dreamer.basic.common.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * > SpringContext 工具类
 * author : dreamer
 * email : dreamers_otw@163.com
 * date : 2018/5/15
 */

public class SpringUtils {
	private static ApplicationContext applicationContext = null;

	public static void setApplicationContext(ApplicationContext applicationContext){
		if(SpringUtils.applicationContext == null){
			SpringUtils.applicationContext  = applicationContext;
		}

	}

	//获取applicationContext
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	//通过name获取 Bean.
	public static Object getBean(String name){
		return getApplicationContext().getBean(name);

	}

	//通过class获取Bean.
	public static <T> T getBean(Class<T> clazz){
		return getApplicationContext().getBean(clazz);
	}

	//通过name,以及Clazz返回指定的Bean
	public static <T> T getBean(String name,Class<T> clazz){
		return getApplicationContext().getBean(name, clazz);
	}
}