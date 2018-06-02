package com.jun.common.rocketmq.consumer.service;

import org.apache.rocketmq.common.message.MessageExt;

import com.jun.common.rocketmq.consumer.builder.IConsumerBuilder;

/**
 * 
 * @Description 实现meta去消费的接口
 * @author Guojun
 * @Date 2018年5月13日 下午8:58:28
 *
 */
public interface IPushConsumer {

	/**
	 * 启动消费
	 * @param consumerBuilder
	 * @param group
	 * @param topic
	 * @param subExpression
	 */
	void startConsumption();
	
	/**
	 * 获取消费者构造器
	 * @return
	 */
	IConsumerBuilder<?> getConsumerBuilder();
	
	/**
	 * 获取消费组
	 * @return
	 */
	String getConsumerGroup();
	
	/**
	 * 消费主题
	 * @return
	 */
	String getTopic();
	
	/**
	 * tag标签表达式
	 * @return
	 */
	default String getSubExpression() {
		return "*";
	};
	
	/**
	 * 数据处理
	 * @param messageExt
	 */
	void processData(MessageExt messageExt);
	
	/**
	 * 停止消费
	 */
	void shutdown();
}
