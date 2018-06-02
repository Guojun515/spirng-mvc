
package com.jun.quartz.service;

import java.util.List;

import org.quartz.JobKey;
import org.quartz.TriggerKey;

import com.github.pagehelper.Page;
import com.jun.quartz.model.JobInfo;

/**
 * 
 * @Description 定时任务操作
 * @author Guojun
 * @Date 2018年4月21日 下午5:45:14
 *
 */
public interface IQuartzService {
	
	/**
	 * 查询所有job的信息
	 * @param queryParam
	 * @param pageNo
	 * @param pageSiez
	 * @return
	 */
	Page<JobInfo> queryAllJobInfo(JobInfo queryParam, int pageNo, int pageSiez);

	/**
	 * 新增job操作
	 * @param job
	 * @return
	 */
    void createJob(JobInfo job);
    
    /**
     * 立即执行job，通过新增一个执行一次的trigger实现
     * @param jobKey
     */
    void executeJob(JobKey jobKey);
    
    /**
     * 暂停job,会暂停当前job的 所有trigger
     * @param jobs
     */
    void pauseJob(List<JobKey> jobKeys);
    
    /**
     * 恢复job,会恢复当前job的 所有trigger
     * @param jobKeys
     */
    void resumeJob(List<JobKey> jobKeys);
    
    /**
     * 暂停触发器
     * @param triggerKeys
     */
    void pauseTrigger (List<TriggerKey> triggerKeys);
    
    /**
     * 恢复触发器
     * @param triggerKeys
     */
    void resumeTrigger (List<TriggerKey> triggerKeys);
    
    /**
     * 暂停所有的job
     */
    void pauseAll();
    
    /**
     * 恢复所有的job
     */
    void resumeAll();
    
    /**
     * 删除job
     * @param jobKeys
     */
    void deleJobs(List<JobKey> jobKeys);

}
