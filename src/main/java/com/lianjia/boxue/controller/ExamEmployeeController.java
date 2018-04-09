package com.lianjia.boxue.controller;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lianjia.boxue.commons.Utils;
import com.lianjia.boxue.domain.EmployeeExamInfoDomain;
import com.lianjia.boxue.domain.EmployeeRemarkDomain;
import com.lianjia.boxue.domain.ExamDataDomain;
import com.lianjia.boxue.domain.ResponseData;
import com.lianjia.boxue.entity.EmployeeExamInfoEntity;
import com.lianjia.boxue.entity.RemarkRecordEntity;
import com.lianjia.boxue.entity.RemarkTimeConfigEntity;
import com.lianjia.boxue.service.EmployeeExamInfoService;
import com.lianjia.boxue.service.ExamManageConfigService;
import com.lianjia.boxue.service.RemarkTimeConfigService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * 
 * @author liliang
 *
 */
@RestController
@RequestMapping("/ExamEmployee")
public class ExamEmployeeController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private EmployeeExamInfoService employeeExamInfoService;
	@Autowired
	private RemarkTimeConfigService remarkTimeConfigService;
	@Autowired
	private ExamManageConfigService examManageConfigService;
	@Value("${shortestExamTime}")
	private int shortestExamTime;
	/*
	 * @RequestMapping("/pull") public ResponseData pullEmployeeExamInfo() {
	 * sysOrgUserService.pullEmployee(); return Utils.getResponseData(null); }
	 */

	/*
	 * @RequestMapping("/getExamTypeAndStatus") public ResponseData
	 * getExamType(@RequestParam(required = true) String employeeNo) { Map<String,
	 * String> result = employeeExamInfoService.getExamTypeAndStatus(employeeNo);
	 * return Utils.getResponseData(result); }
	 */
	/**
	 * 通过此api获取分数等基本信息
	 * 
	 * @param employeeNo
	 * @return
	 */
	@ApiOperation(value = "获取员工基本信息", notes = "通过此api获取考题类型，分数等User基本信息")
	@ApiImplicitParam(name = "userNo", value = "工号", required = true, dataType = "String")
	@GetMapping("/getEmpBasicInfo")
	public ResponseData<EmployeeExamInfoEntity> getEmpBasicInfo(@RequestParam(required = true) String userNo) {
		EmployeeExamInfoEntity result = employeeExamInfoService.queryEmpBasicInfo(userNo);
		return Utils.getResponseData(result);
	}

	/*
	 * @ApiOperation(value = "员工查询自己分数", notes = "")
	 * 
	 * @ApiImplicitParam(name = "userNo", value = "工号", required = true, dataType =
	 * "String")
	 * 
	 * @GetMapping("/getEmpBasicInfo") public ResponseData<EmployeeExamInfoEntity>
	 * getUserScore(@RequestParam(required = true) String userNo, String examMonth)
	 * { EmployeeExamInfoEntity result =
	 * employeeExamInfoService.queryEmpBasicInfo(userNo);
	 * //employeeExamInfoService.q return Utils.getResponseData(result); }
	 */
	/**
	 * 用于月考结果查询列表
	 * 
	 * @param eei
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@ApiOperation(value = "月考结果查询列表", notes = "返回格式{\r\n" + 
			"  \"status\": 0,\r\n" + 
			"  \"code\": \"200\",\r\n" + 
			"  \"message\": \"success\",\r\n" + 
			"  \"data\": {\r\n" + 
			"    \"data\": [\r\n" + 
			"      {\r\n" + 
			"        \"id\": \"20386504-201804\",\r\n" + 
			"        \"userNo\": \"20386504\",\r\n" + 
			"        \"name\": \"叶权.蒲波\",\r\n" + 
			"        \"duty\": \"买卖\",\r\n" + 
			"        \"positionId\": \"110520172115C33E2101762B2DE6468E\",\r\n" + 
			"        \"position\": \"买卖经纪人A1\",\r\n" + 
			"        \"region\": \"北站区\",\r\n" + 
			"        \"regionId\": \"1312021334452DB719C84DC7BFC0DB37\",\r\n" + 
			"        \"suregion\": \"东北大部\",\r\n" + 
			"        \"suregionId\": \"E160002\",\r\n" + 
			"        \"subregion\": \"为民店\",\r\n" + 
			"        \"subregionId\": \"E130239\",\r\n" + 
			"        \"deptId\": \"CE100511\",\r\n" + 
			"        \"deptName\": \"为民店05组\",\r\n" + 
			"        \"examType\": \"MAIMAI\",\r\n" + 
			"        \"status\": 0,\r\n" + 
			"        \"score\": null,\r\n" + 
			"        \"remarkId\": null,\r\n" + 
			"        \"createdDate\": null,\r\n" + 
			"        \"updateDate\": \"2018-04-06T07:12:31.000+0000\",\r\n" + 
			"        \"examMonth\": \"2018-03-31T16:00:00.000+0000\",\r\n" + 
			"        \"paperId\": null\r\n" + 
			"      }\r\n" + 
			"    ],\r\n" + 
			"    \"count\": 2994\r\n" + 
			"  }\r\n" + 
			"}")
	// @ApiImplicitParam(name = "examMonth", value = "考试月份", required = true,
	// dataType = "String",example="2018-03")

	@PostMapping("/getExamInfos")
	public ResponseData<Map<String,Object>> getExamInfos(@RequestBody @Valid EmployeeExamInfoDomain eed) {
		Map<String,Object> map = employeeExamInfoService.queryExamInfos(eed, eed.getPageNo().intValue(), eed.getPageSize().intValue());
		return Utils.getResponseData(map);

	}

	/**
	 * 用于单个员工月考结果查询（非员工自己查询）
	 * 
	 * @param userNo
	 * @return
	 */
	@ApiOperation(value = "单个员工月考结果查询（非员工自己查询）", notes = " 用于单个员工月考结果查询（非员工自己查询）")
	@GetMapping("/ExamInfo/get")
	public ResponseData<EmployeeRemarkDomain> getExamInfo(@RequestParam(required = true) String id) {
		EmployeeRemarkDomain erd = employeeExamInfoService.queryExaminfo(id);
		return Utils.getResponseData(erd);

	}

	/**
	 * 用于单个员工月考结果删除
	 * 
	 * @param userNo
	 * @return
	 */
	@ApiOperation(value = "单个员工月考结果删除", notes = " 用于单个员工月考结果删除")
	@DeleteMapping("/Examinfo/delete")
	public ResponseData<Object> deleteExaminfo(@RequestParam(required = true) String id) {
		employeeExamInfoService.deleteExaminfo(id);
		return Utils.getResponseData(null);
	}

	/**
	 * 用于单个员工月考结果更新
	 * 
	 * @param userNo
	 * @param status
	 * @param score
	 * @return
	 */
	@ApiOperation(value = "单个员工月考结果更新", notes = "用于单个员工月考结果更新")
	@PutMapping("/Examinfo/update")
	public ResponseData<Object> updateExaminfo(@RequestParam(required = true) String id, @RequestParam(required = true) Integer status, @RequestParam(required = true) Integer score) {
		employeeExamInfoService.updateExaminfo(id, status, score);
		return Utils.getResponseData(null);
	}

	/**
	 * 在月考结果查询列表对员工进行备案
	 * 
	 * @param r
	 * @return
	 */
	@ApiOperation(value = "添加备案", notes = "在月考结果查询列表对员工进行备案")
	@PostMapping("/BeiAn/add")
	public ResponseData<Object> addBeiAn(@RequestBody @Valid RemarkRecordEntity r) {
		List<RemarkTimeConfigEntity> rtcs = remarkTimeConfigService.queryRemarkTimes();
		Date currentTime = new Date();
		for (RemarkTimeConfigEntity rtc : rtcs) {
			Date st = rtc.getRemarkStartTime();
			Date et = rtc.getRemarkEndTime();
			if (currentTime.after(st) && currentTime.before(et)) {
				employeeExamInfoService.addBeiAn(r);
				return Utils.getResponseData(null);
			}
		}

		throw new RuntimeException("当前不是可备案时间");
	}

	/**
	 * 在月考结果查询列表对员工进行备案撤销
	 * 
	 * @param userNo
	 * @return
	 */
	@ApiOperation(value = "取消备案", notes = "在月考结果查询列表对员工进行备案撤销")
	@PutMapping("/BeiAn/cancel")
	public ResponseData<Object> cancelBeiAn(@RequestParam(required = true) String userNo) {
		employeeExamInfoService.cancelBeiAn(userNo);
		return Utils.getResponseData(null);
	}

	/**
	 * 提交试卷
	 * 
	 * @param examData
	 * @return
	 */
	@ApiOperation(value = "提交试卷", notes = "")
	@PostMapping("/ExamData/submit")
	public ResponseData<Object> submitExamData(@RequestBody @Valid ExamDataDomain examData) {
		Date currentTime = new Date();
		String[] examTimes = examManageConfigService.queryExamTime();
		if (ArrayUtils.isEmpty(examTimes) || examTimes.length != 2) {
			throw new RuntimeException("没有设置考试时间，提交失败");
		}
		Date st = null;
		Date et = null;
		try {
			st = DateUtils.parseDate(examTimes[0], "yyyy-MM-dd HH:mm:ss");
			et = DateUtils.parseDate(examTimes[1], "yyyy-MM-dd HH:mm:ss");
		} catch (ParseException e1) {
			logger.error("", e1);
			throw new RuntimeException(e1.getMessage());
		}

		if (currentTime.before(st) || currentTime.after(et)) {
			throw new RuntimeException("考试结束，不能提交。");
		}
		if (currentTime.getTime() - st.getTime() < shortestExamTime * 60 * 1000) {
			throw new RuntimeException("考试时间不足" + shortestExamTime + "分钟，不能提交。");
		}
		String userNo = examData.getUserNo();
		
		EmployeeExamInfoEntity eei = employeeExamInfoService.queryEmpBasicInfo(userNo);
		if(eei == null) {
			throw new RuntimeException("无此考生基础数据，不能提交考卷！");
		}
		if (eei != null && eei.getStatus() == 1) {
			throw new RuntimeException("不能重复提交考试数据！");
		}
		// 提交试卷
		employeeExamInfoService.sumbitExamData(examData);
		return Utils.getResponseData(null);
	}

}
