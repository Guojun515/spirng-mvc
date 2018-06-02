package com.jun.common.job.plug;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.matchers.EverythingMatcher;
import org.quartz.spi.ClassLoadHelper;
import org.quartz.spi.SchedulerPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jun.common.job.listener.JobRunningListener;

public class JobRunningPlug implements SchedulerPlugin {
	
	private static final Logger logger = LoggerFactory.getLogger(JobRunningPlug.class);
	
	/**
	 * 插件的名字
	 */
	private String name ;

	@Override
	public void initialize(String name, Scheduler scheduler, ClassLoadHelper loadHelper) throws SchedulerException {
		this.name = name;
		scheduler.getListenerManager().addJobListener(new JobRunningListener(), EverythingMatcher.allJobs());
	}

	@Override
	public void start() {
		logger.info(name + "*********开始执行***********");
		
	}

	@Override
	public void shutdown() {
		logger.info(name + "*********停止执行***********");
	}

}
