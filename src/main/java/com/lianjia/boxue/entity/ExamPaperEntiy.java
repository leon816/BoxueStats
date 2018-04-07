package com.lianjia.boxue.entity;

import java.util.List;

import javax.persistence.Id;

//@Document(collection = "papers")
public class ExamPaperEntiy {
	@Id
	private String id;
	private Integer year;
	private Integer month;
	private Person person;
	private Content content;

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
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
	public Content getContent() {
		return content;
	}
	public void setContent(Content content) {
		this.content = content;
	}
}

class Content {
	private Normal normal;
	private CaseAnalysis caseAnalysis;
	public Normal getNormal() {
		return normal;
	}
	public void setNormal(Normal normal) {
		this.normal = normal;
	}
	public CaseAnalysis getCaseAnalysis() {
		return caseAnalysis;
	}
	public void setCaseAnalysis(CaseAnalysis caseAnalysis) {
		this.caseAnalysis = caseAnalysis;
	}

}

class CaseAnalysis {
	private List<Case> cases;

	public List<Case> getCases() {
		return cases;
	}

	public void setCases(List<Case> cases) {
		this.cases = cases;
	}
}

class Case {
	//private String description;
	private TrueOrFalse trueOrFalse;
	private SimpleChoice simpleChoice;
	private MultipleChoice multipleChoice;
	public TrueOrFalse getTrueOrFalse() {
		return trueOrFalse;
	}
	public void setTrueOrFalse(TrueOrFalse trueOrFalse) {
		this.trueOrFalse = trueOrFalse;
	}
	public SimpleChoice getSimpleChoice() {
		return simpleChoice;
	}
	public void setSimpleChoice(SimpleChoice simpleChoice) {
		this.simpleChoice = simpleChoice;
	}
	public MultipleChoice getMultipleChoice() {
		return multipleChoice;
	}
	public void setMultipleChoice(MultipleChoice multipleChoice) {
		this.multipleChoice = multipleChoice;
	}

}

class Normal {
	private TrueOrFalse trueOrFalse;
	private SimpleChoice simpleChoice;
	private MultipleChoice multipleChoice;
	public TrueOrFalse getTrueOrFalse() {
		return trueOrFalse;
	}
	public void setTrueOrFalse(TrueOrFalse trueOrFalse) {
		this.trueOrFalse = trueOrFalse;
	}
	public SimpleChoice getSimpleChoice() {
		return simpleChoice;
	}
	public void setSimpleChoice(SimpleChoice simpleChoice) {
		this.simpleChoice = simpleChoice;
	}
	public MultipleChoice getMultipleChoice() {
		return multipleChoice;
	}
	public void setMultipleChoice(MultipleChoice multipleChoice) {
		this.multipleChoice = multipleChoice;
	}
}

class MultipleChoice {
	private List<QuestionItem> questions;

	public List<QuestionItem> getQuestions() {
		return questions;
	}

	public void setQuestions(List<QuestionItem> questions) {
		this.questions = questions;
	}
}

class SimpleChoice {
	private List<QuestionItem> questions;

	public List<QuestionItem> getQuestions() {
		return questions;
	}

	public void setQuestions(List<QuestionItem> questions) {
		this.questions = questions;
	}
}

class TrueOrFalse {
	private List<QuestionItem> questions;

	public List<QuestionItem> getQuestions() {
		return questions;
	}

	public void setQuestions(List<QuestionItem> questions) {
		this.questions = questions;
	}
}

class QuestionItem {
	private Integer serialNumber;
	//private String question;
	private String isRight;
	public Integer getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(Integer serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getIsRight() {
		return isRight;
	}
	public void setIsRight(String isRight) {
		this.isRight = isRight;
	}
}

class Person {
	private String workNo;

	public String getWorkNo() {
		return workNo;
	}

	public void setWorkNo(String workNo) {
		this.workNo = workNo;
	}

}
