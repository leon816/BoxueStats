package com.lianjia.boxue.service;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import javax.jms.Destination;
import javax.transaction.Transactional;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.lianjia.boxue.commons.ObjectMapperUtils;
import com.lianjia.boxue.commons.Variables;
import com.lianjia.boxue.domain.ExamDataDomain;
import com.lianjia.boxue.entity.ExamQuestionEntity;
import com.lianjia.boxue.enums.ExamStatus;
import com.lianjia.boxue.repository.EmployeeExamInfoRepository;
import com.lianjia.boxue.repository.ExamQuestionRepository;

/**
 * ActiveMQ service
 * 
 * @author liliang
 *
 */
@Service
public class ExamMessageService {

	@Autowired
	private JmsMessagingTemplate template;

	@Autowired
	EmployeeExamInfoRepository eeiRepository;
	@Autowired
	ExamQuestionRepository examQuestionRepository;
	@Autowired
	StringRedisTemplate redisClient;
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	public void sendMsg(String msg, String queueName) {
		Destination dest = new ActiveMQQueue(queueName);
		template.convertAndSend(dest, msg);
	}
	@Transactional
	@JmsListener(concurrency = "10-20", destination = Variables.ExamScoreQueue)
	public void receiveScoreMsg(String msg) {
		if (StringUtils.isBlank(msg)) {
			logger.error("msg from the queue is blank.->" + msg);
			return;
		}
		String[] items = StringUtils.split(msg, ";");
		if (ArrayUtils.isEmpty(items) || items.length != 4) {
			logger.error("msg error.->" + msg);
			return;
		}
		eeiRepository.submitExamPoint(ExamStatus.YiKao.getIndex(), Integer.parseInt(items[0]), items[1], items[2], items[3]);
	}
	@Transactional
	@JmsListener(concurrency = "10-20", destination = Variables.ExamDataQueue)
	public void receiveDataMsg(String msg) {
		if (StringUtils.isBlank(msg)) {
			logger.error("msg from the queue is blank.->" + msg);
			return;
		}
		ObjectMapper om = ObjectMapperUtils.getObjectMapper();
		ExamDataDomain edd = null;
		try {
			edd = om.readValue(msg, ExamDataDomain.class);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		final ExamDataDomain edd_f = edd;
		List<ExamQuestionEntity> examQuestions = Lists.newArrayList();
		edd.getQuestions().forEach(x -> {
			String questionId = x.getId();
			Integer isRight = x.getIsRight();
			ExamQuestionEntity eqe = new ExamQuestionEntity();
			eqe.setIsRight(isRight);
			eqe.setQuestionId(questionId);
			// eqe.setMonth(edd_f.getMonth());
			eqe.setPaperId(edd_f.getPaperId());
			eqe.setUserNo(edd_f.getUserNo());
			eqe.setExamType(edd_f.getExamType());
			String month = String.valueOf(edd_f.getMonth());
			if (StringUtils.length(month) == 1) {
				month = "0" + month;
			}
			try {
				eqe.setExamTime(DateUtils.parseDate(String.valueOf(edd_f.getYear()) + month, "yyyyMM"));
			} catch (ParseException e) {
				logger.error(e.getMessage(), e);
			}
			examQuestions.add(eqe);
		});
		examQuestionRepository.saveAll(examQuestions);
	}

}
