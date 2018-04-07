package com.lianjia.boxue.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "vw_department")
public class VwDepartmentEntity {
	@Id
	@Column(name = "FD_ID")
	private String deptId;
	@Column(name = "deptname")
	private String deptName;
	@Column(name = "suregionid")
	private String suregionid;

	@Column(name = "suregion")
	private String suregion;
	@Column(name = "regionid")
	private String regionid;
	@Column(name = "region")
	private String region;
	@Column(name = "subregionid")
	private String subregionid;
	@Column(name = "subregion")
	private String subregion;

	public String getSuregion() {
		return suregion;
	}

	public void setSuregion(String suregion) {
		this.suregion = suregion;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getSubregion() {
		return subregion;
	}

	public void setSubregion(String subregion) {
		this.subregion = subregion;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getSuregionid() {
		return suregionid;
	}

	public void setSuregionid(String suregionid) {
		this.suregionid = suregionid;
	}

	public String getRegionid() {
		return regionid;
	}

	public void setRegionid(String regionid) {
		this.regionid = regionid;
	}

	public String getSubregionid() {
		return subregionid;
	}

	public void setSubregionid(String subregionid) {
		this.subregionid = subregionid;
	}
}
