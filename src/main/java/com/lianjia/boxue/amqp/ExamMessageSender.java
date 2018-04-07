package com.lianjia.boxue.amqp;

import java.util.UUID;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ConfirmCallback;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ReturnCallback;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ExamMessageSender implements ReturnCallback, ConfirmCallback {
	@Value("${amqp.examExchange}")
	private String examExchange;
	@Value("${amqp.examScoreQueue}")
	private String examScoreQueue;
	@Value("${amqp.examDataQueue}")
	private String examDataQueue;
	@Autowired
	RabbitTemplate rabbitTemplate;
	Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	RetryCache retryCache;

	@PostConstruct
	public void init() {
		rabbitTemplate.setConfirmCallback(this);
		rabbitTemplate.setReturnCallback(this);
	}

	public void sendExamScore(String msg) {
		rabbitTemplate.convertAndSend(examExchange, examScoreQueue, msg);
		// rabbitTemplate.setConfirmCallback(this);
	}

	public DetailRes sendExamData(String msg) {
		// rabbitTemplate.convertAndSend(examExchange, examDataQueue, msg);
		return this.sendExamData(msg, true);
	}

	/**
	 * 
	 * @param msg
	 * @param cache 本地cache发送的消息，以便消息发送失败时重发。
	 * @return
	 */
	public DetailRes sendExamData(String msg, boolean cache) {
		// rabbitTemplate.convertAndSend(examExchange, examDataQueue, msg);
		try {
			String id = UUID.randomUUID().toString();
			if (cache) {//
				retryCache.add(id, msg);
			}
			rabbitTemplate.convertAndSend(examExchange, examDataQueue, msg, new CorrelationData(id));

		} catch (Exception e) {
			logger.error(e.getMessage());
			return new DetailRes(false, e.getMessage());
		}
		return new DetailRes(true, "");
	}
	/**
	 * 配置中开启return=true时生效。(非必须)
	 * 确认消息到达后删除本地cache。
	 */
	@Override
	public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		rabbitTemplate.send(message);
	}

	/**
	 * 配置中开启confirm=true时生效。
	 * 确认消息到达后删除本地cache。
	 */
	@Override
	public void confirm(CorrelationData correlationData, boolean ack, String cause) {
		if (!ack) {
			if (logger.isInfoEnabled()) {
				logger.info("send message failed: " + cause + correlationData.toString());
			}
		} else {
			if (logger.isDebugEnabled()) {
				logger.debug("delete "+correlationData.getId());
			}
			retryCache.del(correlationData.getId());
		}
	}

}
