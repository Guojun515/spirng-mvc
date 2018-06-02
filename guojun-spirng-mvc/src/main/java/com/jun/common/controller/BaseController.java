package com.jun.common.controller;

import org.springframework.stereotype.Controller;

import com.jun.common.dto.ResponseDto;

@Controller
public class BaseController {
	
	/**
	 * 返回成功的结果
	 * @param t 需要返回的参数
	 * @return
	 */
	protected <T> ResponseDto<T> success(T t) {
		ResponseDto<T> result = new ResponseDto<T>();
		result.setResult(t);
		return result;
	}
	
	/**
	 * 返回失败的结果
	 * @param errorMsg 失败的原因
	 * @return
	 */
	protected <T> ResponseDto<T> error(String errorMsg) {
		ResponseDto<T> result = new ResponseDto<T>();
		result.setSuccess(false);
		result.setMessage(errorMsg);
		return result;
	}
	
}
