package com.lianjia.boxue.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lianjia.boxue.entity.ExamQuestionEntity;

public interface ExamQuestionRepository extends JpaRepository<ExamQuestionEntity, String> {
	/*@Query(value="select q.questionId, count(1) from ExamQuestionEntity q where ")
	void test(Integer year, Integer month, String examType,String suRegionId,String )*/
}
