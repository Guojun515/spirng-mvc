package com.jun.sys.mapper;

import java.util.List;

import com.jun.sys.model.SysUser;

import tk.mybatis.mapper.common.Mapper;

/**
 * 
 * @Description 用户管理
 * @author Guojun
 * @Date 2018年3月29日 下午11:22:54
 *
 */
public interface SysUserMapper extends Mapper<SysUser> {

	/**
	 * 获取用户信息，一并获取角色信息
	 * @param queryParam
	 * @return
	 */
	public List<SysUser> queryUserRoles(SysUser queryParam);
}
