package com.lianjia.boxue.domain;

import java.util.Date;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;

public class EmployeeExamInfoDomain {

	private String userNo;

	private String name;

	private String regionId;

	private String suregionId;

	private String subregionId;

	private String deptId;

	private String examType;

	private Integer status;
	@NotNull
	@ApiModelProperty(required=true,value="考试月份",example="2018-03")
	private String examMonth;
	@NotNull
	@ApiModelProperty(required=true,value="页号")
	private Integer pageNo;
	@NotNull
	@ApiModelProperty(required=true,value="每页行数")
	private Integer pageSize;

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRegionId() {
		return regionId;
	}

	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}

	public String getSuregionId() {
		return suregionId;
	}

	public void setSuregionId(String suregionId) {
		this.suregionId = suregionId;
	}

	public String getSubregionId() {
		return subregionId;
	}

	public void setSubregionId(String subregionId) {
		this.subregionId = subregionId;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getExamType() {
		return examType;
	}

	public void setExamType(String examType) {
		this.examType = examType;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getExamMonth() {
		return examMonth;
	}

	public void setExamMonth(String examMonth) {
		this.examMonth = examMonth;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

}
