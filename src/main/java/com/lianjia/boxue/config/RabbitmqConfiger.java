package com.lianjia.boxue.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.retry.MessageRecoverer;
import org.springframework.amqp.rabbit.retry.RepublishMessageRecoverer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqConfiger {
	@Value("${amqp.examExchange}")
	private String examExchange;
	@Value("${amqp.examScoreQueue}")
	private String examScoreQueue;
	@Value("${amqp.examDataQueue}")
	private String examDataQueue;
	@Value("${amqp.examFailQueue}")
	private String examFailQueue;

	@Bean("examExchange")
	public DirectExchange examExchange() {
		return new DirectExchange(examExchange);
	}

	@Bean("examScoreQueue")
	public Queue examScoreQueue() {
		return new Queue(examScoreQueue);
	}

	@Bean("examScoreQueueBinding")
	public Binding examScoreQueueBinding() {
		return BindingBuilder.bind(examScoreQueue()).to(examExchange()).with(examScoreQueue);
	}

	@Bean("examFailQueue")
	public Queue examFailQueue() {
		return new Queue(examFailQueue);
	}

	@Bean("failDataBinding")
	public Binding failDataBinding() {
		return BindingBuilder.bind(examFailQueue()).to(examExchange()).with(examFailQueue);
	}

	@Bean("examDataQueue")
	public Queue examDataQueue() {
		return new Queue(examDataQueue);
	}

	@Bean("examDataQueueBinding")
	public Binding examDataQueueBinding() {
		return BindingBuilder.bind(examDataQueue()).to(examExchange()).with(examDataQueue);
	}

	/**
	 * 消费最终失败的消息转发到fail队列保存
	 * 
	 * @param rabbitTemplate
	 * @return
	 */
	@Bean
	public MessageRecoverer messageRecoverer(RabbitTemplate rabbitTemplate) {

		RepublishMessageRecoverer rmr = new RepublishMessageRecoverer(rabbitTemplate, examExchange, examFailQueue);
		return rmr;
	}

}
