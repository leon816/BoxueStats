package com.lianjia.boxue.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "lj_org_post")
public class LjOrgPostEntity {
	@Id
	@Column(name = "FD_ID")
	private String id;
	@Column(name = "LOP_DUTY")
	private String duty;
	@Column(name = "FD_NAME")
	private String postName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDuty() {
		return duty;
	}

	public void setDuty(String duty) {
		this.duty = duty;
	}

	public String getPostName() {
		return postName;
	}

	public void setPostName(String postName) {
		this.postName = postName;
	}
}
