package com.jun.common.exception;

import com.jun.common.model.BaseModel;

/**
 * 
 * @Description token相关的异常
 * @author Guojun
 * @Date 2018年4月5日 上午10:09:13
 *
 */
public class TokenException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	/**
	 * token为空
	 */
	public static final String TOKEN_NOT_EXIST = "Token为空";
	
	/**
	 * Token值已被串改
	 */
	public static final String TOKEN_NOT_EQUAL = "Token值已被串改";
	
	/**
	 * Token值构建异常
	 */
	public static final String TOKEN_BUILD_ERROR = "Token值构建异常";
	
	private BaseModel dto;
	
	public TokenException(BaseModel dto) {
		super(TOKEN_NOT_EQUAL);
		this.dto = dto;
	}
	
	public TokenException (BaseModel dto, String errorMsg) {
		super(errorMsg);
		this.dto = dto;
	}
	
	public TokenException (BaseModel dto, String errorMsg, Throwable e) {
		super(errorMsg,e);
		this.dto = dto;
	}

	@Override
	public String getMessage() {
		StringBuilder error = new StringBuilder(super.getMessage());
		error.append(":").append(dto.getClass().getName());
		return error.toString();
	}
}
