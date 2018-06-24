package com.jun.sys.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jun.common.controller.BaseController;
import com.jun.common.dto.ResponseDto;
import com.jun.sys.model.SysVmInfo;
import com.jun.sys.service.ISysVmInfoService;

/**
 * 
 * @Description 获取系统的内部信息
 * @author Guojun
 * @Date 2018年4月5日 下午3:16:05
 *
 */
@Controller
@RequestMapping("/vm")
public class SysVmInfoController extends BaseController {
	
	@Autowired
	private ISysVmInfoService sysVmInfoService;
	
	@RequestMapping(value = "/getVmConfig", method = RequestMethod.GET)
	@ResponseBody
	public ResponseDto<SysVmInfo> getSysConfig(HttpSession session){
		SysVmInfo sysVmInfo = sysVmInfoService.getSysVmConfig();
		return this.success(sysVmInfo);
	}
}
