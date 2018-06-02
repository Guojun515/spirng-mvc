package com.jun.sys.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jun.common.service.impl.BaseServiceImpl;
import com.jun.sys.mapper.SysUserMapper;
import com.jun.sys.model.SysUser;
import com.jun.sys.service.ISysUserService;

@Service
public class SysUserServiceImpl extends BaseServiceImpl<SysUser> implements ISysUserService {
	
	@Autowired
	private SysUserMapper sysUserMapper;

	@Override
	public List<SysUser> queryUserRoles(SysUser queryParam) {
		return sysUserMapper.queryUserRoles(queryParam);
	}
	
}
