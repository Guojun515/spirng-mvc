package com.jun.sys.job;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.jun.common.annotation.job.JobDesc;
import com.jun.common.annotation.job.JobParams;
import com.jun.common.job.AbstractJob;
import com.jun.quartz.mapper.JobInfoMapper;

/**
 * 
 * @Description 测试Job
 * @author Guojun
 * @Date 2018年4月21日 下午11:06:55
 *
 */
@JobDesc(value = "测试Job", cornExpression = "0 0 0/1 * * ? ")
@DisallowConcurrentExecution
public class SysTestJob extends AbstractJob {
	
	@Autowired
	private JobInfoMapper jobInfoMapper;
	
	@JobParams("测试Job参数")
	private String testJobParamKey = "test_job_param";
	
	@JobParams("测试Trigger参数")
	private String testTriggerParamKey = "test_trigger_param";

	@Override
	public void safeExecute(JobExecutionContext context) throws Exception {
		JobDataMap params =  context.getMergedJobDataMap();
		System.out.println(params.get(testJobParamKey));
		System.out.println(params.get(testTriggerParamKey));
		
		System.out.println(JSON.toJSONString(jobInfoMapper.queryAllJobDetails(null)));
	}

}
