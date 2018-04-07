package com.lianjia.boxue.serviceImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.query.NativeQuery;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lianjia.boxue.commons.ObjectMapperUtils;
import com.lianjia.boxue.service.ExamStatsService;


public class MysqlExamStatsServiceImpl implements ExamStatsService {
	@Autowired
	EntityManager em;
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public String queryStats(String suregionId, String regionId, String subregionId, String deptId, Integer examType, String examTime) {

		String sql_all = this.getSqlGroupByQuestionIdAll(suregionId, regionId, subregionId, deptId, examType, examTime);
		String sql_right = this.getSqlGroupByQuestionIdRight(suregionId, regionId, subregionId, deptId, examType, examTime);
		String mysql = "select a.question_id questionId,a.question question, a.num_all num, IFNULL(d.num_right,0) rightNum,  IFNULL(100*ROUND(d.num_right/a.num_all,2),0) rate from (" + sql_all
		        + ") a LEFT JOIN (" + sql_right + ") d on a.question_id = d.question_id";
		Query q = em.createNativeQuery(mysql.toString());
		q.setParameter("examType", examType);
		q.setParameter("examTime", examTime);
		if (StringUtils.isNotEmpty(deptId)) {
			q.setParameter("deptId", deptId);
		} else if (StringUtils.isNotEmpty(subregionId)) {
			q.setParameter("subregionId", subregionId);
		} else if (StringUtils.isNotEmpty(regionId)) {
			q.setParameter("regionId", regionId);
		} else if (StringUtils.isNotEmpty(suregionId)) {
			q.setParameter("suregionId", suregionId);
		}
		q.unwrap(NativeQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Object> list = q.getResultList();
		String json = null;
		try {
			json = ObjectMapperUtils.getObjectMapper().writeValueAsString(list);
		} catch (JsonProcessingException e) {
			logger.error(e.getMessage(), e);
		}
		if (logger.isDebugEnabled()) {
			logger.debug("Stats json: " + json);
		}
		return json;
	}

	private String getSqlGroupByQuestionIdAll(String suregionId, String regionId, String subregionId, String deptId, Integer examType, String examTime) {
		StringBuilder mysql = new StringBuilder();
		mysql.append("SELECT q.question_id,c.question, COUNT(1) num_all FROM exam_question q LEFT JOIN employee_exam_info b ON q.user_no = b.user_no ");
		mysql.append(" LEFT JOIN question c ON q.question_id = c.id ");
		mysql.append("WHERE q.exam_type=:examType AND DATE_FORMAT( q.exam_time,'%Y-%m') =:examTime");
		if (StringUtils.isNotEmpty(deptId)) {
			mysql.append(" AND b.dept_id = :deptId ");
		} else if (StringUtils.isNotEmpty(subregionId)) {
			mysql.append(" AND b.subregion_id = :subregionId ");
		} else if (StringUtils.isNotEmpty(regionId)) {
			mysql.append(" AND b.region_id = :regionId ");
		} else if (StringUtils.isNotEmpty(suregionId)) {
			mysql.append(" AND b.suregion_id = :suregionId ");
		}
		mysql.append("  GROUP BY q.question_id");
		return mysql.toString();
	}

	private String getSqlGroupByQuestionIdRight(String suregionId, String regionId, String subregionId, String deptId, Integer examType, String examTime) {
		StringBuilder mysql = new StringBuilder();
		mysql.append("SELECT q.question_id, COUNT(1) num_right FROM exam_question q LEFT JOIN employee_exam_info b ON q.user_no = b.user_no ");
		mysql.append(" LEFT JOIN question c ON q.question_id = c.id ");
		mysql.append("WHERE q.exam_type=:examType AND DATE_FORMAT( q.exam_time,'%Y-%m') =:examTime");
		mysql.append(" AND q.is_right=1 ");
		if (StringUtils.isNotEmpty(deptId)) {
			mysql.append(" AND b.dept_id = :deptId ");
		} else if (StringUtils.isNotEmpty(subregionId)) {
			mysql.append(" AND b.subregion_id = :subregionId ");
		} else if (StringUtils.isNotEmpty(regionId)) {
			mysql.append(" AND b.region_id = :regionId ");
		} else if (StringUtils.isNotEmpty(suregionId)) {
			mysql.append(" AND b.suregion_id = :suregionId ");
		}
		mysql.append("  GROUP BY q.question_id");
		return mysql.toString();
	}
}
