package com.jun.common.rocketmq.consumer.builder;

import org.apache.rocketmq.client.consumer.MQConsumer;

/**
 * 
 * @Description rocketmq消费者构造器
 * @author Guojun
 * @Date 2018年5月13日 下午8:46:42
 *
 */
public interface IConsumerBuilder<T extends MQConsumer> {

	/**
	 * rocketmq消费者构造器
	 * @return
	 */
	public T build(String consumerGroup);
}
