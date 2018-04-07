package com.lianjia.boxue.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "sys_org_user")
public class SysOrgUserEntity {
	@Id
	@Column(name = "FD_USERID")
	private String id;
	@Column(name = "FD_USERNO")
	private String userNo;
	@Column(name = "FD_USERNAME")
	private String name;
	@Column(name = "fd_is_available")
	private Integer available;
	@Column(name = "fd_deptid")
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
	/*@ManyToOne(cascade = CascadeType.ALL, optional = true) // (fetch=FetchType.LAZY)
	@JoinColumn(name = "fd_deptid",referencedColumnName = "FD_ID")
	@NotFound(action = NotFoundAction.IGNORE)
	private VwDepartmentEntity dept;*/
	/*@ManyToOne(cascade = CascadeType.ALL, optional = true)
	@JoinColumn(name = "fd_postid",referencedColumnName = "FD_ID")//, 
	@NotFound(action = NotFoundAction.IGNORE)
	private LjOrgPostEntity post;*/
	@Column(name = "fd_postid")
	private String postId;
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAvailable() {
		return available;
	}

	public void setAvailable(Integer available) {
		this.available = available;
	}


	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getSuregionid() {
		return suregionid;
	}

	public void setSuregionid(String suregionid) {
		this.suregionid = suregionid;
	}

	public String getSuregion() {
		return suregion;
	}

	public void setSuregion(String suregion) {
		this.suregion = suregion;
	}

	public String getRegionid() {
		return regionid;
	}

	public void setRegionid(String regionid) {
		this.regionid = regionid;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getSubregionid() {
		return subregionid;
	}

	public void setSubregionid(String subregionid) {
		this.subregionid = subregionid;
	}

	public String getSubregion() {
		return subregion;
	}

	public void setSubregion(String subregion) {
		this.subregion = subregion;
	}

	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}

}
