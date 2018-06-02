package com.jun.common.annotation.service;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @Description 用于方法的属性，表示该属性需要更改
 * @author Guojun
 * @Date 2018年4月5日 下午8:10:00
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface NeedUpdate {

}
