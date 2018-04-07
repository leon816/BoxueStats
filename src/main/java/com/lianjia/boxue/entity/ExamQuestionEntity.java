package com.lianjia.boxue.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "exam_question")
public class ExamQuestionEntity {
	@Id
	/*@GenericGenerator(name="g_uid", strategy="uuid")
	@GeneratedValue(generator="g_uid")*/
	@Column
	private String id;
	@Column(nullable = false)
	private String userNo;

	@Column
	private Date examTime;
	@Column
	private String paperId;
	@Column(nullable = false)
	private String questionId;
	@Column(nullable = false)
	private Integer isRight;
	@Column(nullable = false)
	private Integer examType;

	public Integer getExamType() {
		return examType;
	}

	public void setExamType(Integer examType) {
		this.examType = examType;
	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getPaperId() {
		return paperId;
	}

	public void setPaperId(String paperId) {
		this.paperId = paperId;
	}

	public String getQuestionId() {
		return questionId;
	}

	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}

	public Integer getIsRight() {
		return isRight;
	}

	public void setIsRight(Integer isRight) {
		this.isRight = isRight;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getExamTime() {
		return examTime;
	}

	public void setExamTime(Date examTime) {
		this.examTime = examTime;
	}

}
