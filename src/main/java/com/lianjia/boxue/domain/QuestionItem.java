package com.lianjia.boxue.domain;

import io.swagger.annotations.ApiModelProperty;

public class QuestionItem {
	@ApiModelProperty(required = true)
	private String id;
	@ApiModelProperty(required = true)
	private Integer isRight;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getIsRight() {
		return isRight;
	}

	public void setIsRight(Integer isRight) {
		this.isRight = isRight;
	}
}
