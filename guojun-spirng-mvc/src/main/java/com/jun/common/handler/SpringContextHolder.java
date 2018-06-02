package com.jun.common.handler;

import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * http://www.cnblogs.com/wcyBlog/p/4657885.html
 * @Description 以静态变量保存Spring ApplicationContext, 可在任何代码任何地方任何时候中取出ApplicaitonContext.
 * @author Guojun
 * @Date 2018年4月8日 下午10:29:01
 *
 */
public class SpringContextHolder implements ApplicationContextAware {
	
	private static ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringContextHolder.applicationContext = applicationContext;
	}
	
	public static ApplicationContext getApplicationContext(){
		SpringContextHolder.checkApplicationContext();
		return SpringContextHolder.applicationContext;
	}
	
	/**
	 * 更具bean的ID获取
	 * @param beanName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String beanName) {
		SpringContextHolder.checkApplicationContext();
		return (T) applicationContext.getBean(beanName);
	}
	
	/**
	 * 根据bean的类型获取
	 * @param beanClazz
	 * @return
	 */
	public static <T> T getBean (Class<T> beanClazz) {
		SpringContextHolder.checkApplicationContext();
		return applicationContext.getBean(beanClazz);
	}
	
	/**
	 * 根据bean的类型获取,返回Map集合
	 * @param beanClazz
	 * @return
	 */
	public static <T> Map<String, T> getBeans (Class<T> beanClazz) {
		SpringContextHolder.checkApplicationContext();
		Map<String, T> beanMaps =  applicationContext.getBeansOfType(beanClazz);
		return beanMaps;
	}

	/**
	 * 判断applicationContext是否被注入
	 */
	private static void checkApplicationContext() {
        if (applicationContext == null) {
            throw new IllegalStateException("applicaitonContext未注入,请在applicationContext.xml中定义SpringContextHolder");
        }
    }
}
