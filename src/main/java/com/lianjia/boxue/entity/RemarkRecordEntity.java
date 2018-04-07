package com.lianjia.boxue.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name="exam_remark_Record")
@EntityListeners(AuditingEntityListener.class)
public class RemarkRecordEntity {
	@Id
	@Column
	@GenericGenerator(name="g_uid", strategy="uuid")
	@GeneratedValue(generator="g_uid")
	private String id;
	@Column
	@NotNull
	@ApiModelProperty(required=true)
	private String userNo;
	@Column
	@NotNull
	@ApiModelProperty(required=true)
	private String userId;
	@Column
	@NotNull
	@ApiModelProperty(required=true,value="备案记录")
	private String record;
	@Column
	@NotNull
	@ApiModelProperty(required=true,value="操作人id")
	private String operatorId;
	@Column
	@NotNull
	@ApiModelProperty(required=true,value="操作人")
	private String operator;
	@Column
	@CreatedDate
	private Date addTime;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserNo() {
		return userNo;
	}
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getRecord() {
		return record;
	}
	public void setRecord(String record) {
		this.record = record;
	}
	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	public String getOperatorId() {
		return operatorId;
	}
	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
}
