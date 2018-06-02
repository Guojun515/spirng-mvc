package com.jun.security;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.alibaba.fastjson.JSON;
import com.jun.common.dto.ResponseDto;
import com.jun.common.utils.dto.DtoDataTokenUtils;

/**
 * 
 * @Description 登录成功处理
 * @author Guojun
 * @Date 2018年3月26日 下午10:09:59
 *
 */
public class LoginAuthenticationSuccesssHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		response.setContentType("application/json;charset=utf-8");
		
		HttpSession session = request.getSession();
		DtoDataTokenUtils.buildDtoDataToken(session);
		
		User user = (User) authentication.getPrincipal();
		Map<String, Object> datas = new HashMap<>();
		datas.put("user_name", user.getUsername());
		datas.put("role_name", user.getAuthorities());
		
		ResponseDto<Map<String, Object>> result = new ResponseDto<>();
		result.setResult(datas);
		PrintWriter out =response.getWriter();
		out.write(JSON.toJSONString(result));
		out.flush();
		out.close();
	}

}
