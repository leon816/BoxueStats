package com.lianjia.boxue.amqp;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.lianjia.boxue.commons.ObjectMapperUtils;
import com.lianjia.boxue.domain.ExamDataDomain;
import com.lianjia.boxue.entity.ExamQuestionEntity;
import com.lianjia.boxue.enums.ExamStatus;
import com.lianjia.boxue.repository.EmployeeExamInfoRepository;
import com.lianjia.boxue.repository.ExamQuestionRepository;

@Component
@RabbitListener(queues = "${amqp.examDataQueue}", concurrency = "${amqp.concurrency}")
public class ExamDataMessageReceiver {
	@Autowired
	private ExamQuestionRepository examQuestionRepository;
	@Autowired
	private EmployeeExamInfoRepository eeiRepository;
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@RabbitHandler
	public void process(String msg) {
		if (StringUtils.isBlank(msg)) {
			logger.error("msg from the queue is blank.->" + msg);
			throw new RuntimeException("msg from the queue is blank.->" + msg);
		}
		ObjectMapper om = ObjectMapperUtils.getObjectMapper();
		ExamDataDomain edd = null;
		try {
			edd = om.readValue(msg, ExamDataDomain.class);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e.getMessage());
		}
		String m = String.valueOf(edd.getMonth());
		String y = String.valueOf(edd.getYear());
		if (StringUtils.length(m) == 1) {
			m = "0" + m;
		}
		String examMonth = y + "-" + m;
		eeiRepository.submitExamPoint(ExamStatus.YiKao.getIndex(), edd.getScore(), edd.getPaperId(), edd.getUserNo(), examMonth);
		
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
				eqe.setExamTime(DateUtils.parseDate(edd_f.getYear() + month, "yyyyMM"));
			} catch (ParseException e) {
				logger.error(e.getMessage(), e);
			}
			eqe.setId(edd_f.getUserNo() + "-" + edd_f.getYear() + month + "-" + questionId);
			examQuestions.add(eqe);
		});
		examQuestionRepository.saveAll(examQuestions);

	}
}
