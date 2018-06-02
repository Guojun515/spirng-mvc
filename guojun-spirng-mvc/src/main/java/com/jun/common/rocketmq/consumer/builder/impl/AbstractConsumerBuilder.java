package com.jun.common.rocketmq.consumer.builder.impl;

import org.apache.rocketmq.client.consumer.MQConsumer;
import org.springframework.beans.factory.annotation.Value;

import com.jun.common.rocketmq.consumer.builder.IConsumerBuilder;

/**
 * 
 * @Description rocketmq消费者构造器,namesrvAddr地址公用
 * @author Guojun
 * @Date 2018年5月13日 下午8:46:30
 *
 */
public abstract class AbstractConsumerBuilder<T extends MQConsumer> implements IConsumerBuilder<T> {
	
	/**
	 * nameServer的地址
	 */
	@Value("${nameserver.host}")
	protected String namesrvAddr;
	
}
