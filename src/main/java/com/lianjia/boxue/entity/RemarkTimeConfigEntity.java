package com.lianjia.boxue.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
/**
 * filed包括总开关zkg、每场考试时间examTime。
 * @author liliang
 *
 */
@Entity
@Table(name = "exam_remark_time_config")
@EntityListeners(AuditingEntityListener.class)
public class RemarkTimeConfigEntity {
	@Id
	@GenericGenerator(name="g_uid", strategy="uuid")
	@GeneratedValue(generator="g_uid")
	@Column
	private String id;
	@Column
	private Date remarkStartTime;
	@Column
	private Date remarkEndTime;
	@Column
	private String operatorId;
	@Column
	private String operator;
	@Column
	@CreatedDate
	@LastModifiedDate
	private Date operateTime;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public Date getRemarkStartTime() {
		return remarkStartTime;
	}
	public void setRemarkStartTime(Date remarkStartTime) {
		this.remarkStartTime = remarkStartTime;
	}
	public Date getRemarkEndTime() {
		return remarkEndTime;
	}
	public void setRemarkEndTime(Date remarkEndTime) {
		this.remarkEndTime = remarkEndTime;
	}
	public String getOperatorId() {
		return operatorId;
	}
	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

}
