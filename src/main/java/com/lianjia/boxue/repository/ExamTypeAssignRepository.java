package com.lianjia.boxue.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lianjia.boxue.entity.DutyRegionId;
import com.lianjia.boxue.entity.ExamTypeAssignEntity;

public interface ExamTypeAssignRepository extends JpaRepository<ExamTypeAssignEntity, DutyRegionId> {

	void deleteByGroupid(String groupid);
	
	/**
	 * 
	 * @param duty
	 * @param regionId
	 * @param groupid
	 * @return
	 */
	ExamTypeAssignEntity findByDutyAndRegionIdAndGroupidNot(String duty, String regionId, String groupid);
	
	ExamTypeAssignEntity findByDutyAndRegionId(String duty, String regionId);
	
}
