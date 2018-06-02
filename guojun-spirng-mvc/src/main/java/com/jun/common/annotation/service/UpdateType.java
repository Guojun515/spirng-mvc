package com.jun.common.annotation.service;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.jun.common.constants.UpdateTypeEnum;

/**
 * 
 * @Description 注解用于新增或更新时自动填入创建人或更新人
 * @author Guojun
 * @Date 2018年4月5日 下午5:31:37
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface UpdateType {
	
	/**
	 * 更新的类型
	 * @return
	 */
	UpdateTypeEnum value();
}
