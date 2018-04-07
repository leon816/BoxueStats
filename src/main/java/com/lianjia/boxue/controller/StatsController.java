package com.lianjia.boxue.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lianjia.boxue.commons.Utils;
import com.lianjia.boxue.domain.ResponseData;
import com.lianjia.boxue.service.ExamStatsService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/stats")
public class StatsController {
	@Autowired
	ExamStatsService examStatsService;

	/**
	 * 
	 * @param suregionId
	 * @param regionId
	 * @param subregionId
	 * @param deptId
	 * @param examType
	 * @param examTime
	 *            "yyyy-MM"
	 * @return
	 */
	@GetMapping("/getstats")
	@ApiOperation(value = "查询统计数据", notes = "")
	public ResponseData<String> getStatsResult(String suregionId, String regionId, String subregionId, String deptId, @RequestParam(required=true) Integer examType, @RequestParam(required=true)String examTime) {
		String result = examStatsService.queryStats(suregionId, regionId, subregionId, deptId, examType, examTime);
		return Utils.getResponseData(result);
	}
}
