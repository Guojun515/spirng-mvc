package com.jun.common.rocketmq.enums;

/**
 * 
 * @Description 消费组
 * @author Guojun
 * @Date 2018年5月27日 上午11:17:27
 *
 */
public enum ConsumerGroupEnum {
	
	/**
	 * 学习使用的消费组
	 */
	GUO_JUN_CONSUMER_GROUP(TopicEnum.GUO_JUN_TOPIC);
	
	/**
	 * 消费组对用的topic
	 */
	private TopicEnum topic ;

	private ConsumerGroupEnum(TopicEnum topic) {
		this.topic = topic;
	}
	
	public String topic() {
		return topic.name();
	}
}
