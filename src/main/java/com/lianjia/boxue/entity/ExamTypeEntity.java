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
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "exam_type")
@EntityListeners(AuditingEntityListener.class)
public class ExamTypeEntity {
	@Id
	@Column
	@GenericGenerator(name = "g_uid", strategy = "uuid")
	@GeneratedValue(generator = "g_uid")
	@ApiModelProperty(hidden=true)
	private String id;
	@Column(length = 20)
	@NotNull
	@ApiModelProperty(required=true)
	private String typeName;
	@Column(length = 64)
	@NotNull
	@ApiModelProperty(required=true)
	private String operatorId;
	@Column(length = 20)
	@NotNull
	@ApiModelProperty(required=true)
	private String operator;
	@Column
	@CreatedDate
	@LastModifiedDate
	@ApiModelProperty(hidden=true)
	private Date operateTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
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

	public Date getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

}
