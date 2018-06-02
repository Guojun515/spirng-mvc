package com.jun.common.annotation.job;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @Description job参数注解
 * @author Guojun
 * @Date 2018年4月21日 下午10:31:08
 *
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface JobParams {

	/**
	 * 参数的名称
	 * @return
	 */
	String value();
}
