package com.jun.common.rocketmq.consumer.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.consumer.MQConsumer;
import org.apache.rocketmq.client.consumer.MQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jun.common.rocketmq.consumer.service.IPushConsumer;

/**
 * 
 * @Description consumer实现的基类（有序消费）
 * @author Guojun
 * @Date 2018年5月13日 下午9:01:42
 *
 */
public abstract class AbstractPushConsumerOrderly implements IPushConsumer {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractPushConsumerOrderly.class); 
	
	private MQPushConsumer pushConsumer;

	@Override
	public void startConsumption() {
		MQConsumer mqConsumer  = (MQPushConsumer) this.getConsumerBuilder().build(this.getConsumerGroup());
		if (mqConsumer == null || !(mqConsumer instanceof MQPushConsumer)) {
			LOGGER.error(StringUtils.join("消费者构建失败，消费组：", this.getConsumerGroup(), "，消费主题：", this.getTopic()));
			return;
		}
		pushConsumer = (MQPushConsumer) mqConsumer;
		
		try {
			pushConsumer.subscribe(this.getTopic(), this.getSubExpression());
			pushConsumer.registerMessageListener(new MessageListenerConcurrently() {
				
				@Override
				public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
					processData(msgs.get(0));
					
					return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
				}
			});
			
			pushConsumer.start();
		} catch (MQClientException e) {
		
		}
		
		return;
		
	}
	
	@Override
	public void shutdown() {
		if (pushConsumer != null) {
			pushConsumer.shutdown();
		}
	}
}
