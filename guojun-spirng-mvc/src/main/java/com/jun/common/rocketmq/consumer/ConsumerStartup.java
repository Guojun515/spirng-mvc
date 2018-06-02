package com.jun.common.rocketmq.consumer;

import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.jun.common.handler.SpringContextHolder;
import com.jun.common.rocketmq.consumer.service.IPushConsumer;

/**
 * 
 * @Description 启动consummer消费
 * @author Guojun
 * @Date 2018年5月27日 下午1:01:31
 *
 */
public class ConsumerStartup implements InitializingBean {

	ThreadPoolTaskExecutor taskExecutor;

	public void setTaskExecutor(ThreadPoolTaskExecutor taskExecutor) {
		this.taskExecutor = taskExecutor;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Map<String, IPushConsumer> pushConsumers = SpringContextHolder.getBeans(IPushConsumer.class);
		if (pushConsumers != null && pushConsumers.size() > 0) {
			pushConsumers.forEach((beanName, pushConsumer) -> {
				taskExecutor.execute(() -> {
					pushConsumer.startConsumption();
				});
			});
		}
	}

}
