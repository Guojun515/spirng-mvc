package com.jun.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import com.jun.security.utils.AntUrlPathMatcher;
import com.jun.sys.cache.IFunctionRoleCache;

/**
 * 
 * @Description 获取资源对应的权限
 * @author Guojun
 * @Date 2018年3月12日 下午10:58:33
 *
 */
public class MyInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

	/**
	 * 所有的角色和url的对应关系
	 */
	@Autowired
	private IFunctionRoleCache functionRoleCache;

	/**
	 * 返回资源对应的所有权限（或角色）
	 */
	@Override
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		Collection<ConfigAttribute> attributes = new ArrayList<>();
		
		//将参数转为URL
		String url = ((FilterInvocation)object).getRequestUrl();

		//缓存中没有对应的URL，则比表示该URL不需要权限校验
		List<String> functionUrls = functionRoleCache.getFunctionUrls();
		if (functionUrls == null || functionUrls.size() <= 0) {
			return attributes;
		}
		
		//匹配URL，获取该URL的所有权限
		functionUrls.forEach((functionUrl) -> {
			if (AntUrlPathMatcher.pathMatchesUrl(functionUrl, url)) {
				List<String> roles = functionRoleCache.getRoles(functionUrl);
				roles.forEach((role) -> {
					ConfigAttribute configAttribute = new SecurityConfig(role);
					attributes.add(configAttribute);
				});
			}
		});
		
		return attributes;
	}


	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}

}
