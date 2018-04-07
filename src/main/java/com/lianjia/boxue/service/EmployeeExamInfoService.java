package com.lianjia.boxue.service;

import java.util.Map;

import com.lianjia.boxue.domain.EmployeeExamInfoDomain;
import com.lianjia.boxue.domain.EmployeeRemarkDomain;
import com.lianjia.boxue.domain.ExamDataDomain;
import com.lianjia.boxue.entity.EmployeeExamInfoEntity;
import com.lianjia.boxue.entity.RemarkRecordEntity;

public interface EmployeeExamInfoService {

	//Map<String, String> getExamTypeAndStatus(String employeeNo);

	Map<String,Object> queryExamInfos(EmployeeExamInfoDomain eei, int pageNo, int pageSize);

	EmployeeRemarkDomain queryExaminfo(String id);

	EmployeeExamInfoEntity queryEmpBasicInfo(String userNo);

	void deleteExaminfo(String id);

	void updateExaminfo(String id, Integer status, Integer point);

	void addBeiAn(RemarkRecordEntity r);

	void cancelBeiAn(String userNo);

	void sumbitExamData(ExamDataDomain examData);
}
