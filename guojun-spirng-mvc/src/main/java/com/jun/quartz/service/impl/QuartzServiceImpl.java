package com.jun.quartz.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.ScheduleBuilder;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jun.common.exception.ServiceException;
import com.jun.common.job.AbstractJob;
import com.jun.quartz.enums.TriggerStateEnum;
import com.jun.quartz.mapper.JobInfoMapper;
import com.jun.quartz.model.JobInfo;
import com.jun.quartz.service.IQuartzService;

/**
 * 
 * @Description 定时任务操作
 * @author Guojun
 * @Date 2018年4月21日 下午5:46:54
 *
 */
@Service
public class QuartzServiceImpl implements IQuartzService {

	private final Logger LOGGER = LoggerFactory.getLogger(QuartzServiceImpl.class);

	@Autowired
	private Scheduler quartzScheduler;

	@Autowired
	private JobInfoMapper jobInfoMapper;
	
	@Override
	public Page<JobInfo> queryAllJobInfo(JobInfo queryParam, int pageNo, int pageSiez) {
		PageHelper.startPage(pageNo, pageSiez);
		Page<JobInfo> result = (Page<JobInfo>) jobInfoMapper.queryAllJobDetails(queryParam);
		try {
			if (result != null && result.size() > 0) {
				for (JobInfo job : result) {
					job.setTriggerState(TriggerStateEnum.getValue(job.getTriggerState()));
					
					JobKey jobKey = new JobKey(job.getJobName(), job.getGroupName());
					JobDetail jobDetail = quartzScheduler.getJobDetail(jobKey);
					job.setJobData(jobDetail.getJobDataMap());
					
					TriggerKey triggerKey = new TriggerKey(job.getTriggerName(),job.getGroupName());
					Trigger trigger = quartzScheduler.getTrigger(triggerKey);
					if (trigger == null) {
						continue;
					}
					
					if (job.getTriggerType().equals(JobInfo.TRIGGER_TYPE_SIMPLE)) {
						SimpleTrigger simpleTrigger = (SimpleTrigger) trigger;
						job.setRepeatCount(simpleTrigger.getRepeatCount());
						job.setRepeatInterval((int)simpleTrigger.getRepeatInterval());
					} else {
						CronTrigger cronTrigger = (CronTrigger) trigger;
						job.setCronExpress(cronTrigger.getCronExpression());
					}
				}
			}
			
			return result;
		} catch (SchedulerException e) {
			LOGGER.error("queryAllJobInfo异常:", e);

			throw new ServiceException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void createJob(JobInfo job) {
		if (StringUtils.isBlank(job.getJobClassName())) {
			throw new ServiceException("jobClassName不能为空");
		}

		if (StringUtils.isBlank(job.getJobName())) {
			throw new ServiceException("jobName的不能为空");
		}

		if (StringUtils.isBlank(job.getTriggerName())) {
			throw new ServiceException("jobName的不能为空");
		}

		if (StringUtils.isBlank(job.getGroupName())) {
			throw new ServiceException("triggerName不能为空");
		}

		if (StringUtils.isBlank(job.getTriggerType())) {
			throw new ServiceException("triggerType不能为空");
		}

		try {
			Class<?> jobClass = Class.forName(job.getJobClassName());
			if (!AbstractJob.class.isAssignableFrom(jobClass)) {
				throw new ServiceException("jobClassName参数错误");
			}

			JobKey jobKey = new JobKey(job.getJobName(), job.getGroupName());
			//通过jobBuilder 构建jobDetail
			JobBuilder jobBuilder = JobBuilder.newJob((Class<AbstractJob>)jobClass)
					//job的描述
					.withDescription(job.getDescription())
					//job的name与group
					.withIdentity(jobKey)
					//设置job参数
					.usingJobData(job.getJobData());
			
			//生成jobDetail
			JobDetail jobDetail = jobBuilder.build();

			//生成trigger
			Trigger trigger = this.buildTrigger(job, jobDetail);
			quartzScheduler.scheduleJob(jobDetail, trigger);
		} catch (Exception e) {
			LOGGER.error("createJob异常:", e);

			throw new ServiceException(e);
		}

	}
	
	@Override
	public void executeJob(JobKey jobKey) {
		try {
			if (!quartzScheduler.checkExists(jobKey)) {
				return ;
			}
			
			JobInfo jobInfo = new JobInfo();
			jobInfo.setTriggerType(JobInfo.TRIGGER_TYPE_SIMPLE);
			jobInfo.setRepeatInterval(60);
			jobInfo.setRepeatCount(1);
			jobInfo.setTriggerName(StringUtils.join(jobKey.getName(),"-execute-trigger"));
			jobInfo.setGroupName(jobKey.getGroup());
			JobDetail jobDetail = quartzScheduler.getJobDetail(jobKey);
			
			Trigger trigger = this.buildTrigger(jobInfo, jobDetail);
			quartzScheduler.scheduleJob(trigger);
		} catch (SchedulerException e) {
			LOGGER.error("executeJob异常:", e);

			throw new ServiceException(e);
		}
	}

	/**
	 * 生成trigger
	 * @param job
	 * @param jobDetail
	 * @return
	 */
	private Trigger buildTrigger(JobInfo job, JobDetail jobDetail) {
		//创建Trigger
		TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger()
				//trigger的name与group
				.withIdentity(job.getTriggerName(), job.getGroupName())
				//设置优先级，默认为5
				.withPriority(5)
				//关联jobDetail
				.forJob(jobDetail);
		//设置开始日期
		if (job.getStartDate() != null) {
			triggerBuilder.startAt(job.getStartDate());
		}

		//设置结束日期
		if (job.getEndDate() != null) {
			triggerBuilder.endAt(job.getEndDate());
		}

		//任务调度器
		ScheduleBuilder<?> scheduleBuilder = null;

		//corn表达式的trigger
		if (job.getTriggerType().equals(JobInfo.TRIGGER_TYPE_CRON)) {
			if (StringUtils.isBlank(job.getCronExpress())) {
				throw new ServiceException("corn表达式能为空");
			}
			scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpress());
		} else {
			if (job.getRepeatInterval() == -1) {
				throw new ServiceException("repeatInterval：job运行周期为空");
			}

			if (job.getRepeatCount() < 1) {
				scheduleBuilder = SimpleScheduleBuilder.repeatSecondlyForever(job.getRepeatInterval());
			} else {
				scheduleBuilder = SimpleScheduleBuilder.repeatSecondlyForTotalCount(job.getRepeatCount(), 
						job.getRepeatInterval());
			}
		}

		if (scheduleBuilder != null) {
			triggerBuilder.withSchedule(scheduleBuilder);
		}

		return triggerBuilder.build();
	}

	@Override
	public void pauseJob(List<JobKey> jobKeys) {
		if (jobKeys == null || jobKeys.size() <= 0 ) {
			return ;
		}

		jobKeys.forEach(jobKey -> {
			try {
				quartzScheduler.pauseJob(jobKey);
			} catch (Exception e) {
				LOGGER.error("pauseJob异常:", e);

				throw new ServiceException(e);
			}
		});
	}

	@Override
	public void resumeJob(List<JobKey> jobKeys) {
		if (jobKeys == null || jobKeys.size() <= 0 ) {
			return ;
		}

		jobKeys.forEach(jobKey -> {
			try {
				quartzScheduler.resumeJob(jobKey);;
			} catch (Exception e) {
				LOGGER.error("resumeJob异常:", e);

				throw new ServiceException(e);
			}
		});

	}

	@Override
	public void pauseTrigger(List<TriggerKey> triggerKeys) {
		if (triggerKeys == null || triggerKeys.size() <= 0 ) {
			return;
		}

		triggerKeys.forEach(triggerKey -> {
			try {
				quartzScheduler.pauseTrigger(triggerKey);
			} catch (Exception e) {
				LOGGER.error("pauseTrigger异常:", e);

				throw new ServiceException(e);
			}
		});
	}

	@Override
	public void resumeTrigger(List<TriggerKey> triggerKeys) {
		if (triggerKeys == null || triggerKeys.size() <= 0 ) {
			return;
		}
		
		triggerKeys.forEach(triggerKey -> {
			try {
				quartzScheduler.resumeTrigger(triggerKey);
			} catch (Exception e) {
				LOGGER.error("resumeTrigger异常:", e);

				throw new ServiceException(e);
			}
		});
	}

	@Override
	public void pauseAll() {
		try {
			quartzScheduler.pauseAll();
		} catch (SchedulerException e) {
			LOGGER.error("pauseAll异常:", e);

			throw new ServiceException(e);
		}
	}

	@Override
	public void resumeAll() {
		try {
			quartzScheduler.resumeAll();
		} catch (SchedulerException e) {
			LOGGER.error("resumeAll异常:", e);

			throw new ServiceException(e);
		}

	}

	@Override
	public void deleJobs(List<JobKey> jobKeys) {
		try {
			quartzScheduler.deleteJobs(jobKeys);
		} catch (SchedulerException e) {
			LOGGER.error("deleJobs异常:", e);

			throw new ServiceException(e);
		}
	}

}