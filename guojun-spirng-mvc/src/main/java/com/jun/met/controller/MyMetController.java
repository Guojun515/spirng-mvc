package com.jun.met.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jun.common.controller.BaseController;
import com.jun.common.dto.ResponseDto;
import com.jun.met.model.MyMet;
import com.jun.met.service.IMyMetService;

/**
 * 
 * @Description MyMetController
 * @author Guojun
 * @Date 2018年5月27日 下午1:08:21
 *
 */
@Controller
@RequestMapping("/myMet")
public class MyMetController extends BaseController {
	
	@Autowired
	private IMyMetService myMetService;
	
	@RequestMapping(value = "/sendMyMet", method = RequestMethod.POST)
	@ResponseBody
	public ResponseDto<String> sendMyMet(@RequestBody MyMet myMet) {
		String result = myMetService.sendMyMet(myMet);
		return this.success(result);
	}
}
