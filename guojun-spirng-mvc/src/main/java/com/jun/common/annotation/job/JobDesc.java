package com.jun.common.annotation.job;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @Description job说明注解
 * @author Guojun
 * @Date 2018年4月21日 下午10:41:53
 *
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface JobDesc {
	
	/**
	 * job的名称
	 * @return
	 */
	String value();
	
	/**
	 * job执行的表达式
	 * @return
	 */
	String cornExpression();
}
