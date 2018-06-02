package com.jun.quartz.controller;

import java.util.List;

import org.quartz.JobKey;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.Page;
import com.jun.common.controller.BaseController;
import com.jun.common.dto.QueryParamDto;
import com.jun.common.dto.ResponseDto;
import com.jun.quartz.model.JobInfo;
import com.jun.quartz.service.IQuartzService;

/**
 * 
 * @Description quartz定时任务的操作
 * @author Guojun
 * @Date 2018年4月21日 下午5:49:06
 *
 */
@Controller
@RequestMapping("/job")
public class QuartzController extends BaseController {
	
	@Autowired
	private  IQuartzService quartzService;

	/**
	 * 新建一个job
	 * @param job
	 * @return
	 */
	@RequestMapping(value = "/queryAllJobInfo", method = RequestMethod.POST)
	@ResponseBody
	public ResponseDto<Page<JobInfo>> queryAllJobInfo(@RequestBody QueryParamDto<JobInfo> queryParam) {
		Page<JobInfo> result = quartzService.queryAllJobInfo(queryParam.getQueryParam(), queryParam.getPageNo(), queryParam.getPageSize());
		return this.success(result);
	}
	
	/**
	 * 新建一个job
	 * @param job
	 * @return
	 */
	@RequestMapping(value = "/createJob", method = RequestMethod.POST)
	@ResponseBody
	public ResponseDto<String> createJob(@RequestBody JobInfo job){
		quartzService.createJob(job);
		return this.success("新增成功");
	}
	
	/**
     * 立即执行job，通过新增一个执行一次的trigger实现
     * @param jobKey
     */
    @RequestMapping(value = "/executeJob", method = RequestMethod.POST)
	@ResponseBody
	public ResponseDto<String> executeJob(@RequestBody JobKey jobKey){
		quartzService.executeJob(jobKey);
		return this.success("新增成功");
	}
	
    /**
     * 暂停触发器
     * @param triggerKeys
     * @return
     */
	@RequestMapping(value = "/pauseTrigggers", method = RequestMethod.POST)
	@ResponseBody
	public ResponseDto<String> pauseTrigggers(@RequestBody List<TriggerKey> triggerKeys){
		quartzService.pauseTrigger(triggerKeys);;
		return this.success("Trigger暂停成功");
	}
	
	/**
	 * 恢复触发器
	 * @param triggerKeys
	 * @return
	 */
	@RequestMapping(value = "/resumeTrigggers", method = RequestMethod.POST)
	@ResponseBody
	public ResponseDto<String> resumeTrigggers(@RequestBody List<TriggerKey> triggerKeys){
		quartzService.resumeTrigger(triggerKeys);
		return this.success("Trigger恢复成功");
	}
	
	/**
	 * 暂停所有的job
	 * @return
	 */
	@RequestMapping(value = "/pauseAll", method = RequestMethod.GET)
	@ResponseBody
	public ResponseDto<String> pauseAll(){
		quartzService.pauseAll();
		return this.success("全部暂停成功");
	}
	
	/**
	 * 恢复所有的job
	 * @return
	 */
	@RequestMapping(value = "/resumeAll", method = RequestMethod.GET)
	@ResponseBody
	public ResponseDto<String> resumeAll(){
		quartzService.resumeAll();
		return this.success("全部恢复成功");
	}
	
	/**
	 * 删除job
	 * @param jobKeys
	 * @return
	 */
	@RequestMapping(value = "/deleJobs", method = RequestMethod.POST)
	@ResponseBody
	public ResponseDto<String> deleJobs(@RequestBody List<JobKey> jobKeys){
		quartzService.deleJobs(jobKeys);
		return this.success("job删除成功");
	}
}
