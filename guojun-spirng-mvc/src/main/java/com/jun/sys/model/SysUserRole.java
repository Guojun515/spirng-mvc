package com.jun.sys.model;

import javax.persistence.Table;

import com.jun.common.model.BaseModel;

/**
 * 
 * @Description 用户与角色的映射关系
 * @author Guojun
 * @Date 2018年5月27日 下午4:45:10
 *
 */
@Table(name = "sys_user_role")
public class SysUserRole extends BaseModel {
	private static final long serialVersionUID = 381613031351828008L;

	/**
	 * 用户ID
	 */
	private Long userId;
	
	/**
	 * 角色ID
	 */
	private Long roleId;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
}
