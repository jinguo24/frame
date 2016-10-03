package com.simple.common.spring;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Sring 获取bean的工具 以及spring ApplicationEvent监听者
 * 
 */
public class SpringUtil implements ApplicationContextAware{

	private static ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationcontext)
			throws BeansException {
		System.out.println(">>>>>>>>>"+applicationcontext);
		applicationContext = applicationcontext;		
	}
	
	/**
	 * 获取sping管理的bean
	 * 
	 * @param beanId
	 * @return Object
	 */
	public static Object getBean(String beanId) {
		System.out.println(">>>>>>>>>"+applicationContext);
		return applicationContext.getBean(beanId);
	}
	
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}
	
	public static <T> T getBean(Class<T> clazz) {
		return applicationContext.getBean(clazz);
	}

}
