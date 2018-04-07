package com.lianjia.boxue.amqp;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lianjia.boxue.enums.ExamStatus;
import com.lianjia.boxue.repository.EmployeeExamInfoRepository;




@Component
@RabbitListener(queues = "${amqp.examScoreQueue}",concurrency="${amqp.concurrency}")
public class ExamScoreMessageReceiver {
	@Autowired
	private EmployeeExamInfoRepository eeiRepository;
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@RabbitHandler
    public void process(String msg, Message m) {
		if (StringUtils.isBlank(msg)) {
			logger.error("msg from the queue is blank.->" + msg);
			throw new RuntimeException("msg from the queue is blank.->" + msg);
		}
		String[] items = StringUtils.split(msg, ";");
		if (ArrayUtils.isEmpty(items) || items.length != 4) {
			logger.error("msg error.->" + msg);
			throw new RuntimeException("msg error.->" + msg);
		}
		eeiRepository.submitExamPoint(ExamStatus.YiKao.getIndex(), Integer.parseInt(items[0]), items[1], items[2], items[3]);
	}
}
