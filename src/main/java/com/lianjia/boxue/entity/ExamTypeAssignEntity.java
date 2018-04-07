package com.lianjia.boxue.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

/**
 * 考题类型配置对象domain
 * 
 * @author lilia
 *
 */
@Entity
@Table(name = "exam_type_config")
@IdClass(DutyRegionId.class)
public class ExamTypeAssignEntity implements Serializable {
	private static final long serialVersionUID = 2990637869558128852L;

	/**
	 * 职务 0:无;1:买卖;2:租赁;3:新房,4:直销,5:综合,6:(买卖、租赁、综合)
	 */
	@Id
	@Column(length = 10)
	// @Convert(converter=Duty.Convert.class)
	private String duty;
	@Id
	@Column(name = "region_id", nullable = true)
	private String regionId;
	@Column(name = "region", length = 20)
	private String region;

	@Column(name = "exam_type", nullable = true, length = 2)
	private String examType;

	/**
	 * 分组id
	 */
	@Column(nullable = true)
	private String groupid;

	public String getDuty() {
		return duty;
	}

	public void setDuty(String duty) {
		this.duty = duty;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
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

	public String getRegionId() {
		return regionId;
	}

	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}

}
