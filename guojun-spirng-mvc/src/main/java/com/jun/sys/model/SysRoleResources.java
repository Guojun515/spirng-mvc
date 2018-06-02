package com.jun.sys.model;

import javax.persistence.Table;

import com.jun.common.model.BaseModel;

/**
 * 
 * @Description 角色与资源的映射关系
 * @author Guojun
 * @Date 2018年5月27日 下午4:45:10
 *
 */
@Table(name = "sys_role_resources")
public class SysRoleResources extends BaseModel {
	private static final long serialVersionUID = -6152845377653155000L;

	/**
	 * 角色ID
	 */
	private Long roleId;
	
	/**
	 * 资源ID
	 */
	private Long resourceId;

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	
	public Long getResourceId() {
		return resourceId;
	}

	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}
}
