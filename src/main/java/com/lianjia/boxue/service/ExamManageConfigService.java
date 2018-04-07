package com.lianjia.boxue.service;

public interface ExamManageConfigService {

	String queryZkg();

	String switchZkg(String value);

	String[] queryExamTime();

	String[] updateExamTime(String[] values);
}
