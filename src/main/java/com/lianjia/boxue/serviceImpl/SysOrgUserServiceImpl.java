package com.lianjia.boxue.serviceImpl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.lianjia.boxue.commons.ObjectMapperUtils;
import com.lianjia.boxue.commons.Utils;
import com.lianjia.boxue.entity.EmployeeExamInfoEntity;
import com.lianjia.boxue.entity.ExamTypeAssignEntity;
import com.lianjia.boxue.repository.EmployeeExamInfoRepository;
import com.lianjia.boxue.repository.ExamTypeAssignRepository;
import com.lianjia.boxue.repository.SysOrgUserRepository;
import com.lianjia.boxue.service.SysOrgUserService;

@Service
public class SysOrgUserServiceImpl implements SysOrgUserService {
	@Autowired
	SysOrgUserRepository sou_re;
	@Autowired
	EmployeeExamInfoRepository eeiRepository;
	@Autowired
	ExamTypeAssignRepository examTypeAssignRepository;
	@Autowired

	StringRedisTemplate redisClient;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 拉取数据到本地仓储
	 */
	@Override
	public Boolean pullEmployee() {
		boolean result = false;
		// 清空redis
		Set<String> keys = redisClient.keys("employee:*");
		redisClient.delete(keys);
		// 为减轻应用压力每次查询2000条
		Pageable pageable = PageRequest.of(0, 2000);
		Page<Object[]> page = sou_re.findValidUserNative(pageable);
		// ObjectMapper objMapper = ObjectMapperUtils.getObjectMapper();
		do {
			if (logger.isDebugEnabled()) {
				logger.debug("page begin");
			}
			page = process(page);
			if (logger.isDebugEnabled()) {
				logger.debug("page end");
			}
		} while (page != null);
		result = true;
		if(logger.isDebugEnabled()) {
			logger.debug("拉取完毕return true.");
		}
		return result;
	}

	private List<EmployeeExamInfoEntity> rebuild(List<Object[]> users) {
		List<EmployeeExamInfoEntity> employeeExamInfos = Lists.newArrayList();
		Date date = new Date();
		Date examMonth = DateUtils.truncate(date, Calendar.MONTH);
		String examMonth_s = DateFormatUtils.format(examMonth, "yyyyMM");
		for (Object[] user : users) {
			EmployeeExamInfoEntity outEntity = new EmployeeExamInfoEntity();
			// LjOrgPostEntity post = user.getPost();
			String duty = (String) user[11];
			duty = duty == null ? "" : duty;
			outEntity.setDuty(duty);
			outEntity.setId((String) user[0] + "-" + examMonth_s);
			outEntity.setName((String) user[1]);
			outEntity.setPosition((String) user[12]);
			outEntity.setPositionId((String) user[10]);
			outEntity.setDeptId((String) user[8]);
			outEntity.setDeptName((String) user[9]);
			outEntity.setRegion((String) user[5]);
			outEntity.setRegionId((String) user[4]);
			outEntity.setSubregion((String) user[7]);
			outEntity.setSubregionId((String) user[6]);
			outEntity.setSuregion((String) user[3]);
			outEntity.setSuregionId((String) user[2]);
			outEntity.setUserNo((String) user[0]);
			outEntity.setStatus(0);
			outEntity.setExamMonth(examMonth);

			// 查examType
			// 从最低组织层级，向上查询。员工属于各种层级均适用
			ExamTypeAssignEntity etaEntity = examTypeAssignRepository.findByDutyAndRegionId(outEntity.getDuty(), outEntity.getDeptId());
			String examType = etaEntity == null ? null : etaEntity.getExamType();
			if (examType == null && outEntity.getSubregionId() != null && !outEntity.getDeptId().equals(outEntity.getSubregionId())) {
				etaEntity = examTypeAssignRepository.findByDutyAndRegionId(outEntity.getDuty(), outEntity.getSubregionId());
				examType = etaEntity == null ? null : etaEntity.getExamType();
			}
			if (examType == null && outEntity.getRegionId() != null && !outEntity.getDeptId().equals(outEntity.getRegionId())) {
				etaEntity = examTypeAssignRepository.findByDutyAndRegionId(outEntity.getDuty(), outEntity.getRegionId());
				examType = etaEntity == null ? null : etaEntity.getExamType();
			}
			if (examType == null && outEntity.getSuregionId() != null && !outEntity.getDeptId().equals(outEntity.getSuregionId())) {
				etaEntity = examTypeAssignRepository.findByDutyAndRegionId(outEntity.getDuty(), outEntity.getSuregionId());
				examType = etaEntity == null ? null : etaEntity.getExamType();
			}
			if(StringUtils.isBlank(examType)) {
				continue;
			}
			//
			outEntity.setExamType(examType);

			employeeExamInfos.add(outEntity);
		}
		return employeeExamInfos;
	}

	@Transactional
	private Page<Object[]> process(Page<Object[]> page) {
		List<Object[]> users = page.getContent();
		// 重新封装数据
		List<EmployeeExamInfoEntity> employeeExamInfos = rebuild(users);
		// 存储employeeExamInfos到redis
		employeeExamInfos.forEach(x -> {
			try {
				// redisClient.opsForList().rightPush(Utils.generateKey(x.getUserNo()) ,
				// objMapper.writeValueAsString(x));
				redisClient.opsForHash().put(Utils.generateKey(x.getUserNo()), "content", ObjectMapperUtils.getObjectMapper().writeValueAsString(x));
			} catch (JsonProcessingException e) {
				logger.error(e.getMessage(), e);
			}
		});

		// 存储employeeExamInfos到DB
		eeiRepository.saveAll(employeeExamInfos);
		page = page.hasNext() ? sou_re.findValidUserNative(page.nextPageable()) : null;
		return page;
	}
}
