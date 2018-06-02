package com.jun.common.intercept.spring;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.jun.common.intercept.mybatis.DtoTokenBuildInterceptor;
import com.jun.common.utils.dto.DtoDataTokenUtils;

/**
 * 
 * @Description Spring拦截器，获取token，放在mybatis拦截器的ThreadLocal中
 * @author Guojun
 * @Date 2018年4月5日 上午11:48:50
 *
 */
public class DtoTokenInterceptor extends HandlerInterceptorAdapter {

	/**
	 * 请求前的拦截
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//getSession(false)：session存在返回，否则返回null
		HttpSession session = request.getSession(false);
		if (session != null) {
			DtoTokenBuildInterceptor.LOCAL_TOKEN.remove();
			DtoTokenBuildInterceptor.LOCAL_TOKEN.set(DtoDataTokenUtils.getDtoDataToken(session));
		}
		return super.preHandle(request, response, handler);
	}

	
}
