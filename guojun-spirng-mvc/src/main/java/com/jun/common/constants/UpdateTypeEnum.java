package com.jun.common.constants;

/**
 * 
 * @Description 更新操作的类型
 * @author Guojun
 * @Date 2018年4月5日 下午5:37:12
 *
 */
public enum UpdateTypeEnum {
	
	/**
	 * 表示新增
	 */
	TYPE_ADD("add"),
	
	/**
	 * 表示修改
	 */
	TYPE_CHANGE("update");
	
	
	private String method;
	
	private UpdateTypeEnum (String method) {
		this.method = method;
	}

	public String getMethod() {
		return method;
	}

	/**
	 * equal方法
	 * @param updateTypeEnum
	 * @return
	 */
	public boolean equals(UpdateTypeEnum updateTypeEnum) {
		if (this.getMethod().equals(updateTypeEnum.getMethod())) {
			return true;
		}
		return false;
	}
}
