package com.lianjia.boxue.domain;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.lianjia.boxue.enums.Duty;

import io.swagger.annotations.ApiModelProperty;

/**
 * 考题类型配置对象domain
 * @author liliang
 *
 */
public class ExamTypeAssignDomain {

	private String duty;
	@NotNull
	@ApiModelProperty(required = true)
	private List<RegionInfo> regions;
	@NotNull
	@ApiModelProperty(required = true)
	private String examType;
	
	private String groupid;

	public String getDuty() {
		return duty;
	}

	public void setDuty(String duty) {
		this.duty = duty;
	}

	public List<RegionInfo> getRegions() {
		return regions;
	}

	public void setRegions(List<RegionInfo> regions) {
		this.regions = regions;
	}

	public String getExamType() {
		return examType;
	}

	public void setExamType(String examType) {
		this.examType = examType;
	}

	public String getGroupid() {
		return groupid;
	}

	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}
	
}
