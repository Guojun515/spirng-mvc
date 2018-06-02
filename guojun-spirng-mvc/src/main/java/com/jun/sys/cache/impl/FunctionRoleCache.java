package com.jun.sys.cache.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jun.common.redis.RedisService;
import com.jun.sys.cache.IFunctionRoleCache;
import com.jun.sys.service.IFunctionRoleService;

/**
 * 
 * @Description 用户信息缓存
 * @author Guojun
 * @Date 2018年5月6日 下午6:26:01
 *
 */
@Service
public class FunctionRoleCache implements IFunctionRoleCache{
	
	private static final String USER_CACHE_KEY = "sys:function:role";
	
	@Autowired
	RedisService<List<String>> redisService;
	
	@Autowired
	private IFunctionRoleService functionRoleService;

	@Override
	public void initCache() {
		Map<String, List<String>> functionRoles = functionRoleService.queryAllRFunctionRoles();
		if (functionRoles != null && functionRoles.size() > 0) {
			redisService.hmset(USER_CACHE_KEY, functionRoles);
		}
	}


	@Override
	public void updateFunctionRoles() {
		this.initCache();
	}

	@Override
	public List<String> getFunctionUrls() {
		List<String> result = new ArrayList<>();
		
		Map<String, List<String>> functionRoles = redisService.hmget(USER_CACHE_KEY);
		if (functionRoles != null && functionRoles.size() > 0) {
			functionRoles.forEach((functionUrl, roles) -> {
				result.add(functionUrl);
			});
		}
		
		return result;
	}

	@Override
	public List<String> getRoles(String functionUrl) {
		
		return redisService.hmget(USER_CACHE_KEY, functionUrl);
	}
}
