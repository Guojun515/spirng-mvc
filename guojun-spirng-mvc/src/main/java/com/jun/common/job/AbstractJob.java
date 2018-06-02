package com.jun.common.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @Description quartz的job的父类
 * @author Guojun
 * @Date 2018年4月21日 下午5:28:00
 *
 */
public abstract class AbstractJob implements Job {

	private static final Logger logger = LoggerFactory.getLogger(AbstractJob.class);

	/**
	 * 自动处理所有Job运行时发生的异常.
	 * 
	 * @param context
	 *            Job运行时的上下文。
	 * @throws JobExecutionException
	 *            Job执行的异常
	 */
	@Override
	public final void execute(JobExecutionContext context) throws JobExecutionException {
		try {
			safeExecute(context);
		} catch (Exception e) {
			JobExecutionException jobExecutionException = new JobExecutionException(e);

			if (isRefireImmediatelyWhenException()) {
				// true 表示立即重新执行作业
				jobExecutionException.setRefireImmediately(true);
				
				throw jobExecutionException;
			}
			
			// 暂停当前的触发器
			try {
				context.getScheduler().pauseTrigger(context.getTrigger().getKey());
			} catch (SchedulerException schedulerException) {
				logger.error("AbstractJob:触发器暂停异常", schedulerException);
			}
			
			throw jobExecutionException;
		}
	}

	/**
	 * 
	 * 运行时所有的异常将会被自动处理.
	 * 
	 * @param context
	 *            Job运行时的上下文。
	 * @throws Exception
	 *            运行时异常或实现子类主动抛出的异常
	 */
	public abstract void safeExecute(JobExecutionContext context) throws Exception;

	/**
	 * 
	 * 发生异常时是否立即重新执行JOB或将JOB挂起.
	 * 
	 * @return {@code true} Job运行产生异常时，立即重新执行JOB. <br>
	 *         {@code false} Job运行产生异常时，挂起JOB等候管理员处理.
	 */
	protected boolean isRefireImmediatelyWhenException() {
		return false;
	}

}
