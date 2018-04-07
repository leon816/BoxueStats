package com.lianjia.boxue.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.lianjia.boxue.entity.EmployeeExamInfoEntity;

public interface EmployeeExamInfoRepository extends JpaRepository<EmployeeExamInfoEntity, String>, JpaSpecificationExecutor<EmployeeExamInfoEntity> {
	/*
	 * @Query(
	 * value="select e from EmployeeExamInfoEntity e where e.examType=?1 and e.status=?2 and e.examMonth=?3 and e.userNo=?4 and e.name=?5 "
	 * )
	 */
	/*
	 * @Query Page<EmployeeExamInfoEntity>
	 * findExamResult(Specification<EmployeeExamInfoEntity> spec, Pageable
	 * pageable);
	 */
	Optional<EmployeeExamInfoEntity> findById(String id);

	Optional<EmployeeExamInfoEntity> findByUserNoAndExamMonthBetween(String userNo, Date examMonthFirst, Date examMonthLast);

	void deleteById(String id);

	@Modifying
	@Query(value = "update EmployeeExamInfoEntity e set e.status=?1,e.score=?2 where e.id=?3")
	void updateExaminfo(Integer status, Integer score, String id);

	@Modifying
	@Transactional
	@Query(value = "update exam_employee_info e set e.status=?1,e.score=?2,paper_id=?3 where e.user_no=?4 and to_char(exam_month,'yyyy-MM')=?5", nativeQuery = true)
	void submitExamPoint(Integer status, Integer score, String paperId, String userNo, String examMonth);

	@Modifying
	@Query(value = "update EmployeeExamInfoEntity e set e.status=?1 where e.id=?2")
	void updateStatus(Integer status, String id);

}
