package com.lianjia.boxue.service;

import java.util.List;
import java.util.Optional;

import com.lianjia.boxue.entity.DutyRegionId;
import com.lianjia.boxue.entity.ExamTypeAssignEntity;
import com.lianjia.boxue.entity.ExamTypeEntity;

public interface ExamTypeAssignService {

	ExamTypeAssignEntity save(ExamTypeAssignEntity etc);

	List<ExamTypeAssignEntity> saveAll(List<ExamTypeAssignEntity> etcs);

	void delete(String groupid);

	String update(List<ExamTypeAssignEntity> etcs);

	//Optional<ExamTypeAssignEntity> query(DutyRegionId dri);
	
	List<ExamTypeAssignEntity> query(List<DutyRegionId> dris);

	List<ExamTypeAssignEntity> queryAll();
	
	List<String> getDuties();
	List<ExamTypeEntity> getExamTypes();
	ExamTypeEntity  addExamType(ExamTypeEntity ete);
	void deleteExamType(String id);
	
}
