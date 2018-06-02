package com.jun.common.cache.listener;

import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.jun.common.cache.ICacheService;

/**
 * 执行过程：https://blog.csdn.net/u014263388/article/details/78996509
 * @Description 实现spring的监听，主要用于初始化缓存(需要等待所有的bean都初始化完成后才能进行初始化缓存)
 * @author Guojun
 * @Date 2018年5月6日 下午6:30:35
 *
 */
public class CacheInitApplicationListener implements ApplicationListener<ContextRefreshedEvent>, InitializingBean {
	
	ThreadPoolTaskExecutor taskExecutor;
	
	public void setTaskExecutor(ThreadPoolTaskExecutor taskExecutor) {
		this.taskExecutor = taskExecutor;
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		if (taskExecutor == null) {
			taskExecutor = new ThreadPoolTaskExecutor();
		}
	}

	/**
	 * http://www.cnblogs.com/winkey4986/p/5424892.html
	 * 如果我们现在想做的事，是必须要等到所有的 bean都被处理完成之后再进行，
	 * 此时InitializingBean接口的实现就不合适了，所以需要深刻理解事件机制的应用场合。
	 */
	@Override
	public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
		ApplicationContext context = contextRefreshedEvent.getApplicationContext();
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
