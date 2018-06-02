package com.jun.sys.service;

import java.util.List;
import java.util.Map;

/**
 * 
 * @Description 功能角色对应关系，做权限使用
 * @author Guojun
 * @Date 2018年5月27日 下午9:42:22
 *
 */
public interface IFunctionRoleService {
	
	/**
	 * 获取资源对应的角色权限
	 * @return
	 */
	public Map<String, List<String>> queryAllRFunctionRoles();
}
