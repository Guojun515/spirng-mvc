package com.jun.common.rocketmq.consumer.init;

import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.jun.common.init.ISpringInitService;
import com.jun.common.rocketmq.consumer.service.IPushConsumer;

/**
 * 
 * @Description 启动consummer消费
 * @author Guojun
 * @Date 2018年5月27日 下午1:01:31
 *
 */
public class ConsumerInitServiceImpl implements ISpringInitService, InitializingBean {

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
		Map<String, IPushConsumer> pushConsumers = context.getBeansOfType(IPushConsumer.class);
		if (pushConsumers != null && pushConsumers.size() > 0) {
			pushConsumers.forEach((beanName, pushConsumer) -> {
				taskExecutor.execute(() -> {
					pushConsumer.startConsumption();
				});
			});
		}
	}

}
