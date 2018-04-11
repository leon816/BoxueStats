package com.lianjia.boxue.serviceImpl;

import java.util.UUID;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.lianjia.boxue.entity.ExamManageConfigEntity;
import com.lianjia.boxue.repository.ExamManageConfigRepository;
import com.lianjia.boxue.service.ExamManageConfigService;
import com.lianjia.boxue.service.SysOrgUserService;

@Service
public class ExamManageConfigServiceImpl implements ExamManageConfigService {
	@Autowired
	private ExamManageConfigRepository emcRepository;
	@Autowired
	private SysOrgUserService sysOrgUserService;

	Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 查询开考总开关状态；1:开启。0:关闭
	 */
	@Transactional
	@Cacheable(value = "examCache", key = "'zkg'")
	@Override
	public String queryZkg() {
		ExamManageConfigEntity emc = emcRepository.findByFieldName("zkg");
		if (emc == null) {
			// throw new RuntimeException("zkg filed is not in the db table.");
			emc = new ExamManageConfigEntity();
			emc.setFieldName("zkg");
			emc.setId(UUID.randomUUID().toString());
			emc.setValue("0");
			emcRepository.save(emc);
			return "0";
		}
		return emc.getValue();
	}

	/**
	 * 更新开考总开关状态；1:开启。0:关闭 2:开启中;3：开启失败
	 */
	@Override
	@Transactional
	@CachePut(value = "examCache", key = "'zkg'")
	public String switchZkg(String value) {
		if("2".equals(this.queryZkg())&& !"3".equals(value)){
			throw new RuntimeException("正在开启中，请不要频繁开关！");
		}
		if ("1".equals(value)) {
			value = "2";
			//emcRepository.updateZkg(value);
			new Thread(new Runnable() {
				@Override
				public void run() {
					boolean result = false;
					try {
						result = sysOrgUserService.pullEmployee();
					} catch (Exception e) {
						logger.error("", e);
						emcRepository.updateZkg("3");
					}
					if (result) {
						emcRepository.updateZkg("1");
					} else {
						emcRepository.updateZkg("3");
					}
				}
			}).start();
		}
		emcRepository.updateZkg(value);
		return value;
	}

	/**
	 * 查询每场考试开始结束时间 sample: 2018-03-25 09:00:00~2018-03-25 10:00:00
	 */
	@Override
	@Transactional
	@Cacheable(value = "examCache", key = "'examTime'")
	public String[] queryExamTime() {
		ExamManageConfigEntity emc = emcRepository.findByFieldName("examTime");
		if (emc == null) {
			// throw new RuntimeException("examTime filed is not in the db table.");
			emc = new ExamManageConfigEntity();
			emc.setFieldName("examTime");
			emc.setId(UUID.randomUUID().toString());
			emc.setValue(null);
			emcRepository.save(emc);
			return null;
		}
		String timeSection = emc.getValue();
		return StringUtils.split(timeSection, "~");
	}

	@Override
	@Transactional
	@CachePut(value = "examCache", key = "'examTime'")
	public String[] updateExamTime(String[] values) {
		emcRepository.updateExamTime(values[0] + "~" + values[1]);
		return values;
	}

}
