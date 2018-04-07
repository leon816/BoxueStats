package com.lianjia.boxue.domain;

import com.lianjia.boxue.entity.EmployeeExamInfoEntity;
import com.lianjia.boxue.entity.RemarkRecordEntity;

public class EmployeeRemarkDomain {
	private EmployeeExamInfoEntity empInfo;
	private RemarkRecordEntity remarkRecord;
	public EmployeeExamInfoEntity getEmpInfo() {
		return empInfo;
	}
	public void setEmpInfo(EmployeeExamInfoEntity empInfo) {
		this.empInfo = empInfo;
	}
	public RemarkRecordEntity getRemarkRecord() {
		return remarkRecord;
	}
	public void setRemarkRecord(RemarkRecordEntity remarkRecord) {
		this.remarkRecord = remarkRecord;
	}
	
}
