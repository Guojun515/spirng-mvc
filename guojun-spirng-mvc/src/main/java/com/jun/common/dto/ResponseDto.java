package com.jun.common.dto;

public class ResponseDto<T> {
	/**
	 * 获取数据成功还是失败
	 */
	private boolean success = true;
	
	/**
	 * 消息提示
	 */
	private String message;
	
	/**
	 * 真正的数据
	 */
	private T result;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}
}
