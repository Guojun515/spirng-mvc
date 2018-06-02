package com.jun.sys.service;

import java.util.List;

import com.jun.common.service.IBaseService;
import com.jun.sys.model.SysUser;

public interface ISysUserService extends IBaseService<SysUser> {
	
	/**
	 * 获取用户信息，一并获取角色信息
	 * @param queryParam
	 * @return
	 */
	public List<SysUser> queryUserRoles(SysUser queryParam);
}
