package com.jun.common.handler;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jun.common.dto.ResponseDto;

/**
 * 
 * @Description 全局统一异常处理https://www.cnblogs.com/junzi2099/p/7840294.html
 * @author Guojun
 * @Date 2018年3月29日 下午10:14:36
 *
 */
@ControllerAdvice
public class GlobalExceptionHandler {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@ExceptionHandler({Exception.class})
	@ResponseBody
	public <T> ResponseDto<T> exceptionHandler(Exception e) {
		logger.error("系统异常", e);
		
		ResponseDto<T> result = new ResponseDto<>();
		result.setSuccess(false);
		result.setMessage("系统异常：" + e.getMessage());
		return result;
	}
}
