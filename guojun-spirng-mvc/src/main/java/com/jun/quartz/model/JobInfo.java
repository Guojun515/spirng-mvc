package com.jun.quartz.model;

import java.util.Date;

import org.quartz.JobDataMap;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 
 * @Description job信息的描述
 * @author Guojun
 * @Date 2018年4月27日 下午8:37:38
 *
 */
public class JobInfo {
	
	/**
	 * Simple Job
	 */
	public static final String TRIGGER_TYPE_SIMPLE = "SIMPLE";
	
	/**
	 * Corn Job
	 */
	public static final String TRIGGER_TYPE_CRON = "CRON";
	
	/**
	 * Job名称
	 */
	private String jobName;
	
	/**
	 * 触发器名称
	 */
	private String triggerName;

	/**
	 * 任务组
	 */
	private String groupName;
	
	/**
	 * job相对应的类
	 */
	private String jobClassName;
	
	/**
	 * job的描述
	 */
	private String description;
	
	/**
	 * job上一次执行时间
	 */
	private Date prevFireTime;
	
	/**
	 * job下一次执行时间
	 */
	private Date nextFireTime;
	
	/**
	 * job开始日期
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date startDate;
	
	/**
	 * job结束日期
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date endDate;
	
	/**
	 * 触发器的类型，SimpleTrigger/CornTrigger
	 */
	private String triggerType;
	
	/**
	 * 状态
	 */
	private String triggerState;
	
	/**
	 * SimpleTrigger的触发周期,单位为秒
	 */
	private int repeatInterval = -1;
	
	/**
	 * SimpleTrigger重复的周期，0代表无线重复
	 */
	private int repeatCount = 0;
	
	/**
	 * CornTrigger的表达式
	 */
	private String cronExpress;
	
	/**
	 * job的参数
	 */
	private JobDataMap jobData;

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getTriggerName() {
		return triggerName;
	}

	public void setTriggerName(String triggerName) {
		this.triggerName = triggerName;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getJobClassName() {
		return jobClassName;
	}

	public void setJobClassName(String jobClassName) {
		this.jobClassName = jobClassName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getPrevFireTime() {
		return prevFireTime;
	}

	public void setPrevFireTime(Date prevFireTime) {
		this.prevFireTime = prevFireTime;
	}

	public Date getNextFireTime() {
		return nextFireTime;
	}

	public void setNextFireTime(Date nextFireTime) {
		this.nextFireTime = nextFireTime;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getTriggerType() {
		return triggerType;
	}

	public void setTriggerType(String triggerType) {
		this.triggerType = triggerType;
	}

	public String getTriggerState() {
		return triggerState;
	}

	public void setTriggerState(String triggerState) {
		this.triggerState = triggerState;
	}

	public int getRepeatInterval() {
		return repeatInterval;
	}

	public void setRepeatInterval(int repeatInterval) {
		this.repeatInterval = repeatInterval;
	}

	public int getRepeatCount() {
		return repeatCount;
	}

	public void setRepeatCount(int repeatCount) {
		this.repeatCount = repeatCount;
	}

	public String getCronExpress() {
		return cronExpress;
	}

	public void setCronExpress(String cronExpress) {
		this.cronExpress = cronExpress;
	}

	public JobDataMap getJobData() {
		return jobData;
	}

	public void setJobData(JobDataMap jobData) {
		this.jobData = jobData;
	}
}
