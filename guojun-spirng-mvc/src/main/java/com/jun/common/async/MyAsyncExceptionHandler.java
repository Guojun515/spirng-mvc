package com.jun.common.async;

import java.lang.reflect.Method;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

import com.alibaba.fastjson.JSON;

public class MyAsyncExceptionHandler implements AsyncUncaughtExceptionHandler  {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MyAsyncExceptionHandler.class);

	@Override
	public void handleUncaughtException(Throwable ex, Method method, Object... params) {
		LOGGER.error(StringUtils.join(method.getName(),":", JSON.toJSONString(params)), ex);
	}

}
