package com.jun.quartz.enums;

/**
 * 
 * @Description 触发器的执行状态
 * @author Guojun
 * @Date 2018年4月29日 下午4:10:41
 *
 */
public enum TriggerStateEnum {

	/**
	 * 等待
	 */
	WAITING("WAITING", "等待"),
	
	/**
	 * 暂停 
	 */
	PAUSED("PAUSED", "暂停 "),
	
	/**
	 * 正常执行 
	 */
	ACQUIRED("ACQUIRED", "正常执行"),
	
	/**
	 * 阻塞 
	 */
	BLOCKED("BLOCKED", "阻塞 "),
	
	/**
	 * 错误
	 */
	ERROR("ERROR", "错误"),
	
	;
	
	private String key;
	
	private String value;

	private TriggerStateEnum (String key, String value) {
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}
	
	public static String getValue(String key) {
		if (WAITING.getKey().equals(key)) {
			return WAITING.getValue();
		}
		
		if (PAUSED.getKey().equals(key)) {
			return PAUSED.getValue();
		}
		
		if (ACQUIRED.getKey().equals(key)) {
			return ACQUIRED.getValue();
		}
		
		if (BLOCKED.getKey().equals(key)) {
			return BLOCKED.getValue();
		}

		if (ERROR.getKey().equals(key)) {
			return ERROR.getValue();
		}
		
		return null;
	}
}
