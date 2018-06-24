package com.jun.common.cache.init;

import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.jun.common.cache.ICacheService;
import com.jun.common.init.ISpringInitService;

/**
 * 
 * @Description 初始化缓存
 * @author Guojun
 * @Date 2018年5月6日 下午6:30:35
 *
 */
public class CacheInitServiceImpl implements ISpringInitService, InitializingBean {
	
	private ThreadPoolTaskExecutor taskExecutor;
	
	public void setTaskExecutor(ThreadPoolTaskExecutor taskExecutor) {
		this.taskExecutor = taskExecutor;
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		if (taskExecutor == null) {
			taskExecutor = new ThreadPoolTaskExecutor();
		}
	}

	@Override
	public void init(ApplicationContext context) {
		Map<String, ICacheService> cacheServices = context.getBeansOfType(ICacheService.class);
		if (cacheServices != null) {
			cacheServices.forEach((key, cacheService) -> {
				if (cacheService.isStartupInitCache()) {
					taskExecutor.execute(()->{
						cacheService.initCache();
					});
				}
			});
		}
	}
}
