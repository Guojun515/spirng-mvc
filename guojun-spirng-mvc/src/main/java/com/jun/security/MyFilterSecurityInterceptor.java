package com.jun.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

/**
 * 
 * @Description Spring-Security 自定义拦截器
 * @author Guojun
 * @Date 2018年3月12日 下午10:10:22
 *
 */
public class MyFilterSecurityInterceptor extends AbstractSecurityInterceptor implements Filter {
	
	private FilterInvocationSecurityMetadataSource securityMetadataSource;

	/**
	 * 登录后，每次访问资源都会通过这个拦截器拦截
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		//invocation里边有一个被拦截的URL
		FilterInvocation invocation = new FilterInvocation(request, response, filterChain);
		
		//token里面调用myInvocationSecurityMetadataSource的getAttributes(Object object)这个方法获取invocation对应所有的权限
		//再调用MyAccessDecisionManager的decide方法来检验用户的授权是否足够
		InterceptorStatusToken token = super.beforeInvocation(invocation);
		try {
			//执行下一个拦截器
			invocation.getChain().doFilter(invocation.getRequest(), invocation.getResponse());
		} finally {
			super.afterInvocation(token, null);
		}
	}

	/**
	 * 过滤器初始化
	 */
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}
	
	/**
	 * 过滤器销毁
	 */
	@Override
	public void destroy() {
		
	}
	
	@Override
	public Class<?> getSecureObjectClass() {
		return FilterInvocation.class;
	}

	@Override
	public SecurityMetadataSource obtainSecurityMetadataSource() {
		return this.securityMetadataSource;
	}

	public void setSecurityMetadataSource(FilterInvocationSecurityMetadataSource securityMetadataSource) {
		this.securityMetadataSource = securityMetadataSource;
	}
	
}
