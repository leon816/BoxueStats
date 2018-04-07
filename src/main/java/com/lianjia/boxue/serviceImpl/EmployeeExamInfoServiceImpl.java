package com.lianjia.boxue.serviceImpl;

import java.io.IOException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lianjia.boxue.amqp.ExamMessageSender;
import com.lianjia.boxue.commons.ObjectMapperUtils;
import com.lianjia.boxue.commons.Utils;
import com.lianjia.boxue.domain.EmployeeExamInfoDomain;
import com.lianjia.boxue.domain.EmployeeRemarkDomain;
import com.lianjia.boxue.domain.ExamDataDomain;
import com.lianjia.boxue.entity.EmployeeExamInfoEntity;
import com.lianjia.boxue.entity.RemarkRecordEntity;
import com.lianjia.boxue.enums.ExamStatus;
import com.lianjia.boxue.repository.EmployeeExamInfoRepository;
import com.lianjia.boxue.repository.RemarkRecordRepository;
import com.lianjia.boxue.service.EmployeeExamInfoService;

@Service
public class EmployeeExamInfoServiceImpl implements EmployeeExamInfoService {
	@Autowired
	StringRedisTemplate redisClient;
	@Autowired
	EmployeeExamInfoRepository eeiRepository;
	@Autowired
	RemarkRecordRepository remarkRecordRepository;
	@Autowired
	ExamMessageSender examMessageSender;
	@Autowired
	EntityManager em;
	Logger logger = LoggerFactory.getLogger(this.getClass());

	/*
	 * @Override public Map<String, String> getExamTypeAndStatus(String employeeNo)
	 * { String content =
	 * redisClient.opsForValue().get(Utils.generateKey(employeeNo)); if
	 * (StringUtils.isEmpty(content)) { return null; } ObjectMapper objMapper =
	 * ObjectMapperUtils.getObjectMapper(); EmployeeExamInfoEntity eeiEntity = null;
	 * try { eeiEntity = objMapper.readValue(content, EmployeeExamInfoEntity.class);
	 * } catch (IOException e) { logger.error(e.getMessage(), e); } if (eeiEntity ==
	 * null) { return null; } Map<String, String> map = Maps.newHashMap();
	 * map.put("examType", eeiEntity.getExamType()); map.put("status",
	 * eeiEntity.getStatus().toString()); return map; }
	 */

	/**
	 * String examType, Integer status, Date examMonth, String userNo, String
	 * userName,
	 */
	@Override
	public Map<String,Object> queryExamInfos(EmployeeExamInfoDomain eeo, int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo, pageSize);
		Specification<EmployeeExamInfoEntity> spec = new Specification<EmployeeExamInfoEntity>() {
			private static final long serialVersionUID = 6886994210632134130L;

			@Override
			public Predicate toPredicate(Root<EmployeeExamInfoEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				// Predicate examMonth_p = cb.equal(root.get("examMonth").as(String.class),
				// eeo.getExamMonth());
				Date begin = null;
				// Date end = null;
				String examMonth = eeo.getExamMonth();
				try {
					begin = DateUtils.parseDate(examMonth, "yyyy-MM");
					// end = DateUtils.addMonths(begin, 1);
					if (logger.isDebugEnabled()) {
						logger.debug(examMonth);
					}
				} catch (ParseException e) {
					logger.error(e.getMessage(), e);
				}
				Predicate examMonth_p = cb.equal(root.get("examMonth").as(Date.class), begin);
				Predicate examType_p = null;
				Predicate status_p = null;
				Predicate userNo_p = null;
				Predicate userName_p = null;
				if (eeo.getExamType() != null) {
					examType_p = cb.equal(root.get("examType").as(String.class), eeo.getExamType());
				}
				if (eeo.getStatus() != null) {
					status_p = cb.equal(root.get("status").as(Integer.class), eeo.getStatus());
				}
				if (eeo.getUserNo() != null) {
					userNo_p = cb.equal(root.get("userNo").as(String.class), eeo.getUserNo());
				}
				if (eeo.getName() != null) {
					userName_p = cb.like(root.get("name").as(String.class), "%" + eeo.getName() + "%");
				}
				Predicate realRegion_p = null;
				Predicate p = null;
				if (StringUtils.isNotEmpty(eeo.getDeptId())) {
					// deptid
					realRegion_p = cb.equal(root.get("deptId").as(String.class), eeo.getDeptId());
				} else if (StringUtils.isNotEmpty(eeo.getSubregionId())) {
					// subregion
					realRegion_p = cb.equal(root.get("subregionId").as(String.class), eeo.getSubregionId());
				} else if (StringUtils.isNotEmpty(eeo.getRegionId())) {
					// region
					realRegion_p = cb.equal(root.get("regionId").as(String.class), eeo.getRegionId());
				} else if (StringUtils.isNotEmpty(eeo.getSuregionId())) {
					// suregion
					realRegion_p = cb.equal(root.get("suregionId").as(String.class), eeo.getSuregionId());
				}
				List<Predicate> predicates = Lists.newArrayList();
				predicates.add(examMonth_p);
				if (examType_p != null) {
					predicates.add(examType_p);
				}
				if (status_p != null) {
					predicates.add(status_p);
				}
				if (userNo_p != null) {
					predicates.add(userNo_p);
				}
				if (userName_p != null) {
					predicates.add(userName_p);
				}
				if (realRegion_p != null) {
					predicates.add(realRegion_p);
				}
				p = cb.and(predicates.toArray(new Predicate[predicates.size()]));
				return p;
			}
		};
		Page<EmployeeExamInfoEntity> page = eeiRepository.findAll(spec, pageable);
		Map<String, Object> map = Maps.newHashMap();
		map.put("data", page.getContent());
		map.put("count", page.getTotalElements());
		return map;
	}

	/**
	 * CriteriaBuilder cb = em.getCriteriaBuilder();
	 * CriteriaQuery<ExamQuestionEntity> query =
	 * cb.createQuery(ExamQuestionEntity.class); Root<ExamQuestionEntity> root =
	 * query.from(ExamQuestionEntity.class);
	 * query.multiselect(root.get("questionId"),cb.count(root.get("userNo")));
	 * Predicate examType_p = cb.equal(root.get("examType").as(String.class),
	 * examType); Predicate ym_p = cb.equal(root.get("yearMonth").as(Date.class),
	 * yearMonth); query.groupBy(root.get("questionId").as(String.class));
	 * query.where(examType_p, ym_p); em.createQuery(query).getResultList();
	 */

	@Override
	public EmployeeRemarkDomain queryExaminfo(String id) {
		Optional<EmployeeExamInfoEntity> eei_opt = eeiRepository.findById(id);
		RemarkRecordEntity rre = null;
		if (eei_opt.isPresent()) {
			EmployeeExamInfoEntity eei = eei_opt.get();
			if (eei.getStatus() == ExamStatus.BeiAn.getIndex()) {
				List<RemarkRecordEntity> records = remarkRecordRepository.findByUserIdOrderByAddTimeDesc(id);
				rre = CollectionUtils.isEmpty(records) ? null : records.get(0);
			}
		}
		EmployeeRemarkDomain domain = new EmployeeRemarkDomain();
		domain.setEmpInfo(eei_opt.orElse(null));
		domain.setRemarkRecord(rre);
		return domain;
	}

	@Override
	public EmployeeExamInfoEntity queryEmpBasicInfo(String userNo) {

		List<Object> objs = redisClient.opsForHash().multiGet(Utils.generateKey(userNo), Lists.newArrayList("content", "score", "paperId", "status"));
		if (CollectionUtils.isEmpty(objs) || objs.get(0) == null) {
			// 从db里取
			Date c_date = new Date();
			Optional<EmployeeExamInfoEntity> eei = eeiRepository.findByUserNoAndExamMonthBetween(userNo, DateUtils.truncate(c_date, Calendar.MONTH), DateUtils.ceiling(c_date, Calendar.MONTH));
			return eei.orElse(null);
		}
		Object content_o = objs.get(0);
		String content = content_o.toString();

		ObjectMapper objMapper = ObjectMapperUtils.getObjectMapper();
		EmployeeExamInfoEntity eeiEntity = null;
		try {
			eeiEntity = objMapper.readValue(content, EmployeeExamInfoEntity.class);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		if (eeiEntity == null) {
			return null;
		}
		if (objs.get(1) != null) {
			eeiEntity.setScore(Integer.valueOf(objs.get(1).toString()));
		}
		if (objs.get(2) != null) {
			eeiEntity.setPaperId(objs.get(2).toString());
		}
		if (objs.get(3) != null) {
			eeiEntity.setStatus(Integer.valueOf(objs.get(3).toString()));
		}
		return eeiEntity;
	}

	@Override
	@Transactional
	public void deleteExaminfo(String id) {
		eeiRepository.deleteById(id);
		// TODO 是否删除试卷信息（或将试卷信息disable）

	}

	@Override
	@Transactional
	public void updateExaminfo(String id, Integer status, Integer point) {
		eeiRepository.updateExaminfo(status, point, id);
		if (status == ExamStatus.DaiKao.getIndex()) {
			// 清除试卷信息
		}
	}

	@Transactional
	@Override
	public void addBeiAn(RemarkRecordEntity r) {
		eeiRepository.updateStatus(ExamStatus.BeiAn.getIndex(), r.getId());
		remarkRecordRepository.save(r);

	}

	@Override
	@Transactional
	public void cancelBeiAn(String id) {
		eeiRepository.updateStatus(ExamStatus.DaiKao.getIndex(), id);

	}

	@Override
	public void sumbitExamPoint(String userNo, Integer score, String paperId, String examMonth) {
		// eeiRepository.submitExamPoint(ExamStatus.YiKao.getIndex(), point, paperId,
		// userNo);
		// 直接操作redis
		Map<String, String> map = Maps.newHashMap();
		map.put("score", String.valueOf(score));
		map.put("paperId", paperId);
		map.put("status", String.valueOf(ExamStatus.YiKao.getIndex()));
		redisClient.opsForHash().putAll(Utils.generateKey(userNo), map);
		if (logger.isDebugEnabled()) {
			List<Object> list = redisClient.opsForHash().multiGet(Utils.generateKey(userNo), Lists.newArrayList("score", "paperId", "status"));
			list.forEach(x -> logger.debug(x.toString()));
		}
		// 通过队列存到DB
		// examMessageService.sendMsg(score + ";" + paperId + ";" + userNo,
		// Variables.ExamScoreQueue);
		examMessageSender.sendExamScore(score + ";" + paperId + ";" + userNo + ";" + examMonth);
	}

	@Override
	public void sumbitExamData(ExamDataDomain examData) {
		String examData_json = null;
		try {
			examData_json = ObjectMapperUtils.getObjectMapper().writeValueAsString(examData);
		} catch (JsonProcessingException e) {
			logger.error(e.getMessage(), e);
		}
		if (StringUtils.isBlank(examData_json)) {
			throw new RuntimeException("试卷数据不能为空");
		}
		examMessageSender.sendExamData(examData_json);
		String userNo = examData.getUserNo();
		Integer score = examData.getScore();
		String paperId = examData.getPaperId();
		// 直接操作redis
		Map<String, String> map = Maps.newHashMap();
		map.put("score", String.valueOf(score));
		map.put("paperId", paperId);
		map.put("status", String.valueOf(ExamStatus.YiKao.getIndex()));
		redisClient.opsForHash().putAll(Utils.generateKey(userNo), map);
		if (logger.isDebugEnabled()) {
			List<Object> list = redisClient.opsForHash().multiGet(Utils.generateKey(userNo), Lists.newArrayList("score", "paperId", "status"));
			list.forEach(x -> logger.debug(x.toString()));
		}
		
	}

}
