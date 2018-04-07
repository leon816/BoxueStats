package com.lianjia.boxue.service;

public interface ExamStatsService {
	String queryStats(String suregionId, String regionId, String subregionId, String deptId, Integer examType, String examTime);
}
