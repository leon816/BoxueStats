package com.lianjia.boxue;

import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.lianjia.boxue.entity.ExamTypeAssignEntity;
import com.lianjia.boxue.repository.ExamTypeAssignRepository;
import com.lianjia.boxue.repository.SysOrgUserRepository;
import com.lianjia.boxue.service.EmployeeExamInfoService;
import com.lianjia.boxue.service.ExamStatsService;
import com.lianjia.boxue.service.ExamTypeAssignService;
import com.lianjia.boxue.service.SysOrgUserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BoxueStatsApplicationTests {
	@Autowired
	ExamTypeAssignRepository re;
	@Autowired
	ExamTypeAssignService es;
	@Autowired
	SysOrgUserRepository sou_re;
	@Autowired
	//@Qualifier("redisTemplate")
	StringRedisTemplate redisClient;
	@Autowired
	SysOrgUserService userService;
	@Autowired
	EmployeeExamInfoService employeeExamInfoService;
	@Autowired
	ExamStatsService examStatsService;
	
	public void contextLoads() {
		ExamTypeAssignEntity etc = new ExamTypeAssignEntity();
		etc.setDuty("租赁");
		etc.setRegion("东部大区");
		etc.setExamType("2");
		String gid = UUID.randomUUID().toString();
		etc.setGroupid(gid);
		re.save(etc);
		System.out.println("gid:"+gid);
		re.findAll().forEach(x->System.out.println(x.getGroupid()));
		//re.deleteByGroupid(gid);
		//es.delete(gid);
		//re.findAll().forEach(x->System.out.println(x.getExamType()));
	}
	//@Test
	public void SysOrgUserRepositoryTest() {
		/*SysOrgUserEntity  a = sou_re.findById("20220805").get();
		System.out.println(a.getName());
		//VwDepartmentEntity dept = a.getDept();
		System.out.println(a.getSuregion()+">"+a.getRegion()+">"+a.getSubregion()+">"+a.getDeptName());
		System.out.println(a.getPost().getDuty());
		  a = sou_re.findById("20382828").get();
		System.out.println(a.getName());
		 //dept = a.getDept();
		System.out.println(a.getSuregion()+">"+a.getRegion()+">"+a.getSubregion()+">"+a.getDeptName());
		System.out.println(a.getPost().getDuty());*/
		
	}
	//@Test
	public void test() {
		Pageable p = PageRequest.of(0, 10);
		//List<SysOrgUserEntity> users = sou_re.findValidUser(p).getContent();
		//System.out.println(users.get(0).getName());
		/*List<SysOrgUserEngity> users = sou_re.findAll(p).getContent();
		System.out.println(users.get(0).getName());*/
	}
	
	//@Test
	public void testRedis() {
		userService.pullEmployee();
		System.out.println(redisClient.opsForValue().get("employee:12345678"));
		System.out.println(redisClient.opsForValue().get("employee:20000001"));
		System.out.println(redisClient.opsForValue().get("employee:20000002"));
		//System.out.println(employeeExamInfoService.getExamType("20000001"));
	}
	@Test
	public void test5() {
		examStatsService.queryStats(null, null, null, null, 1, "2018-03");
	}

}
