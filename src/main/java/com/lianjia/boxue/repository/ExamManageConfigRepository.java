package com.lianjia.boxue.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lianjia.boxue.entity.ExamManageConfigEntity;

public interface ExamManageConfigRepository extends JpaRepository<ExamManageConfigEntity, String> {

	ExamManageConfigEntity findByFieldName(String fieldName);

	@Modifying
	@Transactional
	@Query(value = "update ExamManageConfigEntity set value = :v where fieldName='zkg'")
	void updateZkg(@Param(value = "v") String v);

	@Modifying
	@Query(value = "update ExamManageConfigEntity set value = ?1 where fieldName='examTime'")
	void updateExamTime(String value);
}
