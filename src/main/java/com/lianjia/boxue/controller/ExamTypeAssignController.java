package com.lianjia.boxue.controller;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lianjia.boxue.commons.Utils;
import com.lianjia.boxue.domain.ExamTypeAssignDomain;
import com.lianjia.boxue.domain.RegionInfo;
import com.lianjia.boxue.domain.ResponseData;
import com.lianjia.boxue.entity.ExamTypeAssignEntity;
import com.lianjia.boxue.entity.ExamTypeEntity;
import com.lianjia.boxue.service.ExamTypeAssignService;

import io.swagger.annotations.ApiOperation;

/**
 * 为不同职务和区域对象的员工分配不同的考题类型。
 * 
 * @author liliang
 *
 */
@RestController
@RequestMapping("/ExamTypeAssign")
public class ExamTypeAssignController {

	@Autowired
	private ExamTypeAssignService etcService;

	/**
	 * 添加考题分配
	 */
	@PostMapping("/add")
	@ApiOperation(value = "添加考题分配", notes = "")
	public ResponseData<Object> add(@RequestBody ExamTypeAssignDomain ead) {
		String duty = ead.getDuty();
		String examType = ead.getExamType();
		List<RegionInfo> regions = ead.getRegions();
		String groupid = UUID.randomUUID().toString();
		List<ExamTypeAssignEntity> etcs = Lists.newArrayList();
		for (RegionInfo region : regions) {
			ExamTypeAssignEntity etc = new ExamTypeAssignEntity();
			etc.setDuty(duty);
			etc.setRegion(region.getRegion());
			etc.setRegionId(region.getRegionId());
			etc.setExamType(examType);
			etc.setGroupid(groupid);
			etcs.add(etc);
		}
		etcService.saveAll(etcs);
		return Utils.getResponseData(null);
	}

	/**
	 * 更新考题分配
	 */
	@PutMapping("/update")
	@ApiOperation(value = "更新考题分配", notes = "")
	public ResponseData<Object> update(@RequestBody ExamTypeAssignDomain ead) {
		String duty = ead.getDuty();
		String examType = ead.getExamType();
		List<RegionInfo> regions = ead.getRegions();
		String groupid = ead.getGroupid();
		List<ExamTypeAssignEntity> etcs = Lists.newArrayList();
		for (RegionInfo region : regions) {
			ExamTypeAssignEntity etc = new ExamTypeAssignEntity();
			etc.setDuty(duty);
			etc.setRegion(region.getRegion());
			etc.setRegionId(region.getRegionId());
			etc.setExamType(examType);
			etc.setGroupid(groupid);
			etcs.add(etc);
		}
		etcService.update(etcs);
		return Utils.getResponseData(null);
	}

	/**
	 * 删除考题分配
	 */
	@DeleteMapping("/delete")
	@ApiOperation(value = "删除考题分配", notes = "")
	public ResponseData<Object> delete(@RequestParam(required = true) String groupid) {
		etcService.delete(groupid);
		return Utils.getResponseData(null);
	}

	/**
	 * 查询考题分配
	 */
	@GetMapping("/query")
	@ApiOperation(value = "查询考题分配", notes = "")
	public ResponseData<List<ExamTypeAssignDomain>> query() {
		List<ExamTypeAssignEntity> etcEntities = etcService.queryAll();
		Map<String, List<RegionInfo>> groupidRegionMap = Maps.newHashMap();
		for (ExamTypeAssignEntity etcEntity : etcEntities) {
			List<RegionInfo> regions = groupidRegionMap.get(etcEntity.getGroupid());
			if (regions == null) {
				regions = Lists.newArrayList();
				groupidRegionMap.put(etcEntity.getGroupid(), regions);
			}
			regions.add(new RegionInfo(etcEntity.getRegionId(), etcEntity.getRegion()));
		}

		List<ExamTypeAssignDomain> etc_domains = Lists.newArrayList();
		for (Map.Entry<String, List<RegionInfo>> entry : groupidRegionMap.entrySet()) {
			ExamTypeAssignDomain etcDomain = new ExamTypeAssignDomain();
			String groupid = entry.getKey();
			etcDomain.setGroupid(groupid);
			etcDomain.setRegions(entry.getValue());
			ExamTypeAssignEntity etc_e = getOneExamTypeConfigByGroupid(etcEntities, groupid);
			etcDomain.setDuty(etc_e.getDuty());
			etcDomain.setExamType(etc_e.getExamType());
			etc_domains.add(etcDomain);
		}
		return Utils.getResponseData(etc_domains);
	}

	private ExamTypeAssignEntity getOneExamTypeConfigByGroupid(List<ExamTypeAssignEntity> etcEntities, String groupid) {
		return etcEntities.stream().filter(e -> groupid.equals(e.getGroupid())).findFirst().get();

	}

	@GetMapping("/getDuties")
	@ApiOperation(value = "获取职务列表", notes = "")
	ResponseData<List<String>> getDuties() {
		return Utils.getResponseData(etcService.getDuties());
	}

	@GetMapping("/getExamTypes")
	@ApiOperation(value = "获取考题类型列表", notes = "")
	ResponseData<List<ExamTypeEntity>> getExamTypes() {
		return Utils.getResponseData(etcService.getExamTypes());
	}
	@ApiOperation(value = "添加考题类型到列表", notes = "")
	@PostMapping("/addExamType")
	ResponseData<Object> addExamType(@RequestBody @Valid ExamTypeEntity ete) {
		etcService.addExamType(ete);
		return Utils.getResponseData(null);
	}
	
	@DeleteMapping("/deleteExamType")
	@ApiOperation(value = "删除考题类型从列表", notes = "")
	ResponseData<Object> deleteExamType(String id) {
		etcService.deleteExamType(id);
		return Utils.getResponseData(null);
	}
}
