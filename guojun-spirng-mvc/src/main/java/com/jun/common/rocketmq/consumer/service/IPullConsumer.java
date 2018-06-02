package com.jun.common.rocketmq.consumer.service;

/**
 * 
 * @Description 实现meta去消费的接口
 * @author Guojun
 * @Date 2018年5月13日 下午8:58:28
 *
 */
public interface IPullConsumer {

	/**
	 * 启动消费
	 */
	void startConsumption();
	
	/**
	 * 停止消费
	 */
	void shutdown();
}
