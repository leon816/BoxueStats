package com.lianjia.boxue.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import io.swagger.annotations.ApiModel;

@Entity
@Table(name = "exam_employee_info")
@EntityListeners(AuditingEntityListener.class)
@ApiModel
public class EmployeeExamInfoEntity {
	@Id
	@Column
	private String id;
	@Column(length = 12, unique = true)
	private String userNo;
	@Column(length = 20, nullable = true)
	private String name;
	@Column(length = 10)
	private String duty;
	@Column(length = 20)
	private String positionId;
	@Column(length = 32)
	private String position;
	@Column
	private String region;
	@Column
	private String regionId;

	@Column
	private String suregion;
	@Column
	private String suregionId;

	@Column
	private String subregion;
	@Column
	private String subregionId;
	@Column
	private String deptId;
	@Column
	private String deptName;

	@Column
	private String examType;
	@Column(nullable = true)
	private Integer status;
	@Column
	private Integer score;
	@Column
	private String remarkId;
	@Column
	@CreatedDate
	@LastModifiedDate
	private Date createdDate;
	@Column
	@LastModifiedDate
	private Date updateDate;
	@Column
	private Date examMonth;
	@Column
	private String paperId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDuty() {
		return duty;
	}

	public void setDuty(String duty) {
		this.duty = duty;
	}

	public String getPositionId() {
		return positionId;
	}

	public void setPositionId(String positionId) {
		this.positionId = positionId;
	}

	public String getPosition() {
		return position;
	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getRegionId() {
		return regionId;
	}

	public void setRegionId(String regionId) {
		this.regionId = regionId;
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

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public String getRemarkId() {
		return remarkId;
	}

	public void setRemarkId(String remarkId) {
		this.remarkId = remarkId;
	}

	public String getSuregion() {
		return suregion;
	}

	public void setSuregion(String suregion) {
		this.suregion = suregion;
	}

	public String getSuregionId() {
		return suregionId;
	}

	public void setSuregionId(String suregionId) {
		this.suregionId = suregionId;
	}

	public String getSubregion() {
		return subregion;
	}

	public void setSubregion(String subregion) {
		this.subregion = subregion;
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

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Date getExamMonth() {
		return examMonth;
	}

	public void setExamMonth(Date examMonth) {
		this.examMonth = examMonth;
	}

	public String getPaperId() {
		return paperId;
	}

	public void setPaperId(String paperId) {
		paperId = paperId;
	}

}
