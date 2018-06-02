package com.jun.security;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

/**
 * 
 * @Description 没有权限处理的类
 * @author Guojun
 * @Date 2018年3月27日 下午11:09:08
 *
 */
public class MyAccessDeniedHandler implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		PrintWriter printWriter = response.getWriter();
		printWriter.append(accessDeniedException.getMessage());
		printWriter.flush();
	}

}
