package com.lianjia.boxue.repository;

import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import com.lianjia.boxue.entity.SysOrgUserEntity;

public interface SysOrgUserRepository extends JpaRepository<SysOrgUserEntity, String>, JpaSpecificationExecutor<SysOrgUserEntity> {

	/*@Query(value = "select u from SysOrgUserEntity u where u.available=1 and u.post is not null and u.deptId is not null")
	Page<SysOrgUserEntity> findValidUser(Pageable Pageable);*/

	@Query(value = "SELECT u.fd_userno,u.fd_username,u.suregionid,u.suregion,u.regionid,u.region,u.subregionid,u.subregion,u.fd_deptid,u.deptname,"
	        + "u.fd_postid,p.lop_duty,e.fd_name, u.fd_userid FROM sys_org_user u LEFT JOIN lj_org_post p ON u.fd_postid = p.fd_id LEFT JOIN sys_org_element e ON u.fd_postid = e.fd_id"
	        + " WHERE u.fd_is_available=1  AND u.fd_postid IS NOT NULL AND u.fd_deptid IS NOT NULL AND u.fd_userNo IS NOT NULL",
	        countQuery="select count(1) FROM sys_org_user u LEFT JOIN lj_org_post p ON u.fd_postid = p.fd_id LEFT JOIN sys_org_element e ON u.fd_postid = e.fd_id WHERE u.fd_is_available=1  AND u.fd_postid IS NOT NULL AND u.fd_deptid IS NOT NULL AND u.fd_userNo IS NOT NULL",
	        nativeQuery = true)
	@QueryHints(value= {@QueryHint(name="org.hibernate.fetchSize", value="50")})
	Page<Object[]> findValidUserNative(Pageable Pageable);


	@Query(value = "SELECT distinct p.lop_duty FROM sys_org_user u LEFT JOIN lj_org_post p ON u.fd_postid = p.fd_id ",nativeQuery=true)
	List<Object> getAllDuties();
}
