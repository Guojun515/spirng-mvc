package com.jun.met.service.impl;

import org.apache.rocketmq.client.producer.SendResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.jun.common.exception.ServiceException;
import com.jun.common.rocketmq.enums.TopicEnum;
import com.jun.common.rocketmq.producer.ProducerFactory;
import com.jun.met.model.MyMet;
import com.jun.met.service.IMyMetService;

/**
 * 
 * @Description 发送MyMet消息的服务
 * @author Guojun
 * @Date 2018年5月27日 上午11:47:19
 *
 */
@Service
public class MyMetServiceImpl implements IMyMetService {
	
	private final Logger LOGGER = LoggerFactory.getLogger(MyMetServiceImpl.class);
	
	@Autowired
	private ProducerFactory producerFactory;

	@Override
	public String sendMyMet(MyMet myMet) {
		try {
			String myMetJson = JSON.toJSONString(myMet);
			SendResult result = producerFactory.sendMessage(TopicEnum.GUO_JUN_TOPIC.name(), myMetJson);
			return result.toString();
		} catch (Exception e) {
			LOGGER.error("sendMyMet异常:", e);

			throw new ServiceException(e);
		}
	}
}
