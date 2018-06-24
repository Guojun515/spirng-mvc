package com.jun.sys.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jun.common.controller.BaseController;
import com.jun.common.dto.ResponseDto;
import com.jun.sys.model.SysUser;
import com.jun.sys.service.ISysUserService;

/**
 * 
 * @Description 
 * @author Guojun
 * @Date 2018年4月5日 下午3:16:05
 *
 */
@Controller
@RequestMapping("/user")
public class SysUserController extends BaseController {
	
	@Autowired
	private ISysUserService sysUserService;
	
	@RequestMapping(value = "/info", method = RequestMethod.GET)
	@ResponseBody
	public ResponseDto<List<SysUser>> info(){
		List<SysUser> users = sysUserService.selectAll();
		return this.success(users);
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	@ResponseBody
	public ResponseDto<SysUser> create(SysUser sysUser){
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		sysUser.setPassword(passwordEncoder.encode(sysUser.getPassword()));
		SysUser user = sysUserService.insert(sysUser);
		return this.success(user);
	}
}
