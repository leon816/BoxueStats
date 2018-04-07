package com.lianjia.boxue.domain;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;

public class RegionInfo {
	/**
	 * 区域id
	 */
	@NotNull
	@ApiModelProperty(required = true)
	private String regionId;

	/**
	 * 区域名
	 */
	@NotNull
	@ApiModelProperty(required = true)
	private String region;

	public String getRegionId() {
		return regionId;
	}

	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public RegionInfo() {
		super();
	}

	public RegionInfo(String regionId, String region) {
		this.regionId = regionId;
		this.region = region;
	}
}
