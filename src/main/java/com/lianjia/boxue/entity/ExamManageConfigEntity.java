package com.lianjia.boxue.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * filed包括总开关zkg、每场考试时间examTime。
 * 
 * @author liliang
 *
 */
@Entity
@Table(name = "exam_manage_config")
public class ExamManageConfigEntity {
	@Id
	@Column
	private String id;
	@Column(unique = true)
	private String fieldName;
	@Column
	private String value;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
