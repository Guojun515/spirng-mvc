package com.jun.sys.cache;

import java.util.List;

import com.jun.common.cache.ICacheService;

/**
 * 
 * @Description 用户信息缓存
 * @author Guojun
 * @Date 2018年5月6日 下午6:26:24
 *
 */
public interface IFunctionRoleCache extends ICacheService {
	
	/**
	 * 获取缓存的地址
	 * @return
	 */
	public List<String> getFunctionUrls();
	
	/**
	 * 通过URL获取对应的角色信息
	 * @param userId
	 * @return
	 */
	public List<String> getRoles(String functionUrl);
	
	/**
	 * 更新用户缓存
	 * @param user
	 */
	public void updateFunctionRoles();
}
