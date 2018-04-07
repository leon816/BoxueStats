package com.lianjia.boxue.domain;

public class ExamStatsResultDomain {

	private String question_id;
	private String question;
	private Integer count;
	private Integer right_count;
	private Double right_rate;

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Integer getRight_count() {
		return right_count;
	}

	public void setRight_count(Integer right_count) {
		this.right_count = right_count;
	}

	public Double getRight_rate() {
		return right_rate;
	}

	public void setRight_rate(Double right_rate) {
		this.right_rate = right_rate;
	}

	public String getQuestion_id() {
		return question_id;
	}

	public void setQuestion_id(String question_id) {
		this.question_id = question_id;
	}
}
