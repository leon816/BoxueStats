package com.lianjia.boxue.entity;

import java.io.Serializable;

public class DutyRegionId implements Serializable {
	private static final long serialVersionUID = -6693210720724321965L;
	String duty;
	String regionId;

	public DutyRegionId(String duty, String regionId) {
		this.duty = duty;
		this.regionId = regionId;
	}
	
	public DutyRegionId() {
		super();
	}

	public String getDuty() {
		return duty;
	}

	public void setDuty(String duty) {
		this.duty = duty;
	}

	public String getRegionId() {
		return regionId;
	}

	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}
}
