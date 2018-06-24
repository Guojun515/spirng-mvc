package com.jun.sys.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jun.common.controller.BaseController;
import com.jun.common.dto.ResponseDto;

/**
 * 
 * @Description 
 * @author Guojun
 * @Date 2018年4月5日 下午3:16:05
 *
 */
@Controller
@RequestMapping("/sys")
public class SysController extends BaseController {
	
	/**
	 * 提示登录
	 * @return
	 */
	@RequestMapping(value = "/gotoLogin")
	@ResponseBody
	public ResponseDto<String> gotoLogin(HttpServletRequest request, HttpServletResponse response){
		System.out.println(request.getRequestURL().toString());
		response.setHeader("Access-Control-Allow-Origin", "*");
		return this.error("请进行登录");
	}
	
	/**
	 * 退出登录
	 * @return
	 */
	@RequestMapping(value = "/gotoLoginout")
	@ResponseBody
	public ResponseDto<String> gotoLoginout(HttpServletRequest request, HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", "*");
		return this.success("成功退出");
	}
	
	/**
	 * 获取session信息
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/getSession")
	@ResponseBody
	public ResponseDto<Map<String, Object>> getSession(HttpSession session){
		SecurityContext securityContext = (SecurityContext)session.getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);
		if (securityContext != null) {
			User user = (User) securityContext.getAuthentication().getPrincipal();
			Map<String, Object> sessionData = new HashMap<>();
			sessionData.put("user_name", user.getUsername());
			sessionData.put("role_name", user.getAuthorities());
			
			return this.success(sessionData);
		}
		
		return this.success(null);
	}
}
