package com.jun.common.job.listener;

import org.apache.commons.lang3.StringUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JobRunningListener implements JobListener {
	
	private static final Logger logger = LoggerFactory.getLogger(JobRunningListener.class);

	/**
	 * 返回监听器的名字
	 */
	@Override
	public String getName() {
		return this.getClass().getName();
	}

	/**
	 * 在 JobDetail 将要被执行时调用这个方法。
	 */
	@Override
	public void jobToBeExecuted(JobExecutionContext context) {
		logger.info( StringUtils.join(this.getName(), "触发对",
				context.getJobDetail().getJobClass().getName(), "的开始执行的监听工作，这里可以完成任务前的一些资源准备工作或日志记录"));
	}

	/**
	 * 在 JobDetail 即将被执行，但又被 TriggerListener 否决了时调用这个方法。
	 */
	@Override
	public void jobExecutionVetoed(JobExecutionContext context) {
		logger.info(StringUtils.join(context.getJobDetail().getJobClass().getName(),"被否决执行，这里可以做日志记录"));
	}

	/**
	 * 在 JobDetail 被执行之后调用这个方法。
	 */
	@Override
	public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
		if (jobException == null) {
			logger.info(StringUtils.join(context.getJobDetail().getJobClass().getName(),"完成执行"));
		} else {
			logger.error(StringUtils.join(context.getJobDetail().getJobClass().getName(),"执行异常了：", jobException.getMessage()));
		}
	}

}
