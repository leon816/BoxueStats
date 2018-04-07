package com.lianjia.boxue.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lianjia.boxue.entity.RemarkRecordEntity;

public interface RemarkRecordRepository extends JpaRepository<RemarkRecordEntity, String> {
	List<RemarkRecordEntity> findByUserIdOrderByAddTimeDesc(String userId);
}
