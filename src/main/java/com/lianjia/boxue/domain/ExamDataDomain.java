package com.lianjia.boxue.domain;

import java.util.List;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;

public class ExamDataDomain {
	@NotNull
	@ApiModelProperty(required = true)
	private String userNo;
	@NotNull
	@ApiModelProperty(required = true)
	private Integer score;
	@NotNull
	@ApiModelProperty(required = true,example="2018")
	private Integer year;
	@NotNull
	@ApiModelProperty(required = true,example="3")
	private Integer month;
	@NotNull
	@ApiModelProperty(required = true)
	private String paperId;
	@NotNull
	@ApiModelProperty(required = true)
	private Integer examType;
	
	private List<QuestionItem> questions;

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

	public List<QuestionItem> getQuestions() {
		return questions;
	}

	public void setQuestions(List<QuestionItem> questions) {
		this.questions = questions;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public Integer getExamType() {
		return examType;
	}

	public void setExamType(Integer examType) {
		this.examType = examType;
	}
}


