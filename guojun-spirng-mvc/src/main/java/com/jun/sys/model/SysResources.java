package com.jun.sys.model;

import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.jun.common.model.BaseModel;

/**
 * 
 * @Description 页面资源信息
 * @author Guojun
 * @Date 2018年5月27日 下午4:47:39
 *
 */
@Table(name = "sys_resources")
public class SysResources extends BaseModel {
	private static final long serialVersionUID = -1872574290535988608L;

	/**
	 * 对应的资源ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long resourceId;
	
	/**
	 * 对应的资源名称
	 */
	private String resourceName;
	
	/**
	 * 对应的资源路径
	 */
	private String resourceUrl;
	
	/**
	 * 资源描述
	 */
	private String description;
	
	/**
	 * 功能信息
	 */
	@Transient
	private List<SysFunction> syFunctions;

	public long getResourceId() {
		return resourceId;
	}

	public void setResourceId(long resourceId) {
		this.resourceId = resourceId;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public String getResourceUrl() {
		return resourceUrl;
	}

	public void setResourceUrl(String resourceUrl) {
		this.resourceUrl = resourceUrl;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
