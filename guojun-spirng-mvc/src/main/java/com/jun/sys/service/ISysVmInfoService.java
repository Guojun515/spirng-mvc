package com.jun.sys.service;

import com.jun.sys.model.SysVmInfo;

/**
 * 
 * @Description 系统信息
 * @author Guojun
 * @Date 2018年6月23日 下午2:47:22
 *
 */
public interface ISysVmInfoService {
	
	/**
	 * 获取系统的运行状况
	 * @return
	 */
	SysVmInfo getSysVmConfig();
}
