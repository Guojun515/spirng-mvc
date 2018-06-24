package com.jun.common.init;

import org.springframework.context.ApplicationContext;

/**
 * 
 * @Description spring加载完后的初始化
 * @author Guojun
 * @Date 2018年6月3日 下午5:58:44
 *
 */
public interface ISpringInitService {
	
	/**
	 * 执行初始化操作
	 * @param context
	 */
	public void init(ApplicationContext context);
}
