package com.jun.sys.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.jun.common.model.BaseModel;

/**
 * 
 * @Description 功能信息
 * @author Guojun
 * @Date 2018年5月27日 下午4:47:39
 *
 */
@Table(name = "sys_function")
public class SysFunction extends BaseModel {
	private static final long serialVersionUID = 6446461622449351434L;

	/**
	 * 功能ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long functionId;
	
	/**
	 * 资源Id
	 */
	private long resourceId;
	
	/**
	 * 对应的功能代码
	 */
	private String functionCode;
	
	/**
	 * 对应的功能名称
	 */
	private String functionName;
	
	/**
	 * 功能对应的路径
	 */
	private String functionUrl;
	
	/**
	 * 功能描述
	 */
	private String description;

	public long getFunctionId() {
		return functionId;
	}

	public void setFunctionId(long functionId) {
		this.functionId = functionId;
	}

	public long getResourceId() {
		return resourceId;
	}

	public void setResourceId(long resourceId) {
		this.resourceId = resourceId;
	}

	public String getFunctionCode() {
		return functionCode;
	}

	public void setFunctionCode(String functionCode) {
		this.functionCode = functionCode;
	}

	public String getFunctionName() {
		return functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	public String getFunctionUrl() {
		return functionUrl;
	}

	public void setFunctionUrl(String functionUrl) {
		this.functionUrl = functionUrl;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
