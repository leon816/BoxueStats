package com.lianjia.boxue.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;
import com.lianjia.boxue.entity.DutyRegionId;
import com.lianjia.boxue.entity.ExamTypeAssignEntity;
import com.lianjia.boxue.repository.ExamTypeAssignRepository;
import com.lianjia.boxue.repository.SysOrgUserRepository;
import com.lianjia.boxue.service.ExamTypeAssignService;

@Service
public class ExamTypeAssignServiceImpl implements ExamTypeAssignService {

	@Autowired
	ExamTypeAssignRepository etcRepository;
	@Autowired
	SysOrgUserRepository souRepository;

	@Override
	public ExamTypeAssignEntity save(ExamTypeAssignEntity etc) {

		return etcRepository.save(etc);
	}

	@Override
	@Transactional
	public void delete(String groupid) {
		etcRepository.deleteByGroupid(groupid);
	}

	@Override
	@Transactional
	public List<ExamTypeAssignEntity> saveAll(List<ExamTypeAssignEntity> etcs) {
		if (CollectionUtils.isEmpty(etcs)) {
			throw new RuntimeException("提交数据不能为空");
		}
		if (checkRepeation(etcs)) {
			throw new RuntimeException("提交数据与其他数据重复");
		}
		List<ExamTypeAssignEntity> result = etcRepository.saveAll(etcs);
		return result;

	}

	@Override
	@Transactional
	public String update(List<ExamTypeAssignEntity> etcs) {
		// 1.检测重复数据
		if (CollectionUtils.isEmpty(etcs)) {
			throw new RuntimeException("提交数据不能为空");
		}
		if (checkRepeation2(etcs)) {
			throw new RuntimeException("提交数据与已有数据重复");
		}
		// 2.删除同组所有数据
		delete(etcs.get(0).getGroupid());
		// 3.保存新数据
		etcRepository.saveAll(etcs);
		return "success";
	}

	@Override
	public List<ExamTypeAssignEntity> queryAll() {
		return etcRepository.findAll();

	}

	/*
	 * @Override public Optional<ExamTypeAssignEntity> query(DutyRegionId dri) {
	 * return etcRepository.findById(dri); }
	 */

	@Override
	public List<ExamTypeAssignEntity> query(List<DutyRegionId> dris) {
		return etcRepository.findAllById(dris);
	}

	private boolean checkRepeation(List<ExamTypeAssignEntity> etcs) {
		List<DutyRegionId> dris = etcs.stream().map(x -> new DutyRegionId(x.getDuty(), x.getRegionId())).collect(Collectors.toList());
		if (CollectionUtils.isEmpty(dris)) {
			return false;
		}
		List<ExamTypeAssignEntity> results = query(dris);
		if (!CollectionUtils.isEmpty(results)) {
			// 已经有相关记录
			return true;
		}
		return false;
	}

	private boolean checkRepeation2(List<ExamTypeAssignEntity> etcs) {
		List<DutyRegionId> dris = etcs.stream().map(x -> new DutyRegionId(x.getDuty(), x.getRegionId())).collect(Collectors.toList());
		if (CollectionUtils.isEmpty(dris)) {
			return false;
		}
		String groupid = etcs.get(0).getGroupid();
		for (DutyRegionId dri : dris) {
			ExamTypeAssignEntity etae = etcRepository.findByDutyAndRegionIdAndGroupidNot(dri.getDuty(), dri.getRegionId(), groupid);
			if (etae != null) {
				return true;
			}
		}

		return false;
	}

	@Override
	public List<String> getDuties() {
		List<Object> dos = souRepository.getAllDuties();
		List<String> outs = Lists.newArrayList();
		dos.forEach(x -> {
			outs.add((String) x);
		});
		return outs;
	}

}
