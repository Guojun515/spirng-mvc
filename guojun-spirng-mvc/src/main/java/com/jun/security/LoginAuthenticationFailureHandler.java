package com.jun.security;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.alibaba.fastjson.JSON;
import com.jun.common.dto.ResponseDto;

/**
 * 
 * @Description 处理登录失败
 * @author Guojun
 * @Date 2018年3月26日 下午10:08:45
 *
 */
public class LoginAuthenticationFailureHandler implements AuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		response.setContentType("application/json;charset=utf-8");
		
		ResponseDto<String> result = new ResponseDto<>();
		result.setSuccess(false);
		result.setMessage("登录失败");
		PrintWriter out =response.getWriter();
		out.write(JSON.toJSONString(result));
		out.flush();
		out.close();
	}

}
