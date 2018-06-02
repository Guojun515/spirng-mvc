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
 * @Description 角色信息
 * @author Guojun
 * @Date 2018年5月27日 下午5:08:04
 *
 */
@Table(name = "sys_role")
public class SysRole extends BaseModel {
	private static final long serialVersionUID = 8001649252101830176L;

	/**
	 * 角色ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long roleId;
	
	/**
	 * 角色名称
	 */
	private String roleName;
	
	/**
	 * 角色描述
	 */
	private String roleDesc;
	
	/**
	 * 资源信息
	 */
	@Transient
	private List<SysResources> sysResources;

	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleDesc() {
		return roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	public List<SysResources> getSysResources() {
		return sysResources;
	}

	public void setSysResources(List<SysResources> sysResources) {
		this.sysResources = sysResources;
	}
}
