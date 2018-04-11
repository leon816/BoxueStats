package com.lianjia.boxue.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Maps;
import com.lianjia.boxue.commons.Utils;
import com.lianjia.boxue.domain.ResponseData;
import com.lianjia.boxue.entity.RemarkTimeConfigEntity;
import com.lianjia.boxue.service.ExamManageConfigService;
import com.lianjia.boxue.service.RemarkTimeConfigService;
import com.lianjia.boxue.service.SysOrgUserService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/ExamManage")
public class ExamManageConfigController {
	@Autowired
	private SysOrgUserService sysOrgUserService;
	@Autowired
	private ExamManageConfigService examManageConfigService;
	@Autowired
	private RemarkTimeConfigService remarkTimeConfigService;

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@ApiOperation(value = "查询总开关状态", notes = "")
	@GetMapping("/getZkg")
	public ResponseData<String> getZkg() {
		String zkg = examManageConfigService.queryZkg();
		return Utils.getResponseData(zkg);
	}

	/**
	 * 考试总开关
	 * 
	 * @param value
	 *            开：1， 关：0,
	 * @return
	 */
	@ApiOperation(value = "开启关闭总开关", notes = "1:开启；0：关闭;2:开启中；3：开启失败")
	@PutMapping("/switchZkg")
	public ResponseData<String> switchZkg(@RequestParam(required = true) String value) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 判断是否在考试中
		String[] times = examManageConfigService.queryExamTime();
		if (ArrayUtils.isNotEmpty(times)) {
			try {
				Date timeBegin = sdf.parse(times[0]);
				Date timeEnd = sdf.parse(times[1]);
				Date now = new Date();
				if (now.after(timeBegin) && now.before(timeEnd)) {
					return Utils.getResponseData("考试中不允许开关！！！");
				}
			} catch (ParseException e) {
				logger.error(e.getMessage(), e);
			}
		}
		examManageConfigService.switchZkg(value);
		return Utils.getResponseData("1".equals(value) ? "2" : value);
	}

	@ApiOperation(value = "获取设定的考试时间", notes = "")
	@GetMapping("/getExamTime")
	public ResponseData<Map<String, String>> getExamTime() {
		String[] times = examManageConfigService.queryExamTime();
		if (times == null) {
			return Utils.getResponseData(null);
		}
		Map<String, String> map = Maps.newHashMap();
		map.put("startTime", times[0]);
		map.put("endTime", times[1]);
		return Utils.getResponseData(map);

	}

	@ApiOperation(value = "改变设定的考试时间", notes = "")
	/*
	 * @ApiImplicitParams(value = { @ApiImplicitParam(name = "startTime", required =
	 * true, dataType = "String", example = "2018-03 09:00:00"),
	 * 
	 * @ApiImplicitParam(name = "endTime", required = true, dataType = "String",
	 * example = "2018-03 10:00:00") })
	 */

	@PutMapping("/updateExamTime")
	public ResponseData<String> updateExamTime(@RequestParam(required = true) String startTime, @RequestParam(required = true) String endTime) {
		String joinTime = startTime + "~" + endTime;
		examManageConfigService.updateExamTime(new String[] { startTime, endTime });
		return Utils.getResponseData(joinTime);

	}

	/**
	 * 添加备案时间区间
	 * 
	 * @param remarkTime
	 * @param operator
	 * @return
	 */
	@PostMapping("/addRemarkTime")
	@ApiOperation(value = "添加备案时间区间", notes = "")
	public ResponseData<RemarkTimeConfigEntity> addRemarkTime(@RequestParam(required = true) Date remarkStartTime, @RequestParam(required = true) Date remarkEndTime,
	        @RequestParam(required = true) String operator) {
		RemarkTimeConfigEntity rtc = new RemarkTimeConfigEntity();
		rtc.setRemarkStartTime(remarkStartTime);
		rtc.setRemarkEndTime(remarkEndTime);
		rtc.setOperator(operator);
		return Utils.getResponseData(remarkTimeConfigService.add(rtc));
	}

	@DeleteMapping("/deleteRemarkTime")
	@ApiOperation(value = "删除备案时间区间", notes = "")
	public ResponseData<Object> deleteRemarkTime(@RequestParam(required = true) String id) {
		remarkTimeConfigService.delete(id);
		return Utils.getResponseData(null);
	}

	@ApiOperation(value = "查询备案时间区间", notes = "")
	@GetMapping("/queryRemarkTimes")
	public ResponseData<List<RemarkTimeConfigEntity>> queryRemarkTimes() {
		List<RemarkTimeConfigEntity> rtcs = remarkTimeConfigService.queryRemarkTimes();
		return Utils.getResponseData(rtcs);
	}

	@ApiOperation(value = "更新备案时间区间", notes = "")
	@PutMapping("/updateRemarkTime")
	public ResponseData<RemarkTimeConfigEntity> updateRemarkTime(@RequestParam(required = true) Date remarkStartTime, @RequestParam(required = true) Date remarkEndTime,
	        @RequestParam(required = true) String operator, @RequestParam(required = true) String id) {
		RemarkTimeConfigEntity rtc = new RemarkTimeConfigEntity();
		rtc.setRemarkStartTime(remarkStartTime);
		rtc.setRemarkEndTime(remarkEndTime);
		rtc.setOperator(operator);
		rtc.setId(id);
		remarkTimeConfigService.update(rtc);
		return Utils.getResponseData(rtc);
	}
}
