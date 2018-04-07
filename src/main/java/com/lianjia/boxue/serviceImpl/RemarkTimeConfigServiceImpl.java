package com.lianjia.boxue.serviceImpl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lianjia.boxue.entity.RemarkTimeConfigEntity;
import com.lianjia.boxue.repository.RemarkTimeConfigRepository;
import com.lianjia.boxue.service.RemarkTimeConfigService;

@Service
@Transactional
public class RemarkTimeConfigServiceImpl implements RemarkTimeConfigService {
	@Autowired
	RemarkTimeConfigRepository rtcRepository;

	@Override
	public List<RemarkTimeConfigEntity> queryRemarkTimes() {
		return rtcRepository.findAll();
	}

	@Override
	public RemarkTimeConfigEntity add(RemarkTimeConfigEntity rec) {
		return rtcRepository.save(rec);
	}

	@Override
	public void delete(String id) {
		rtcRepository.deleteById(id);
		
	}

	@Override
	public void update(RemarkTimeConfigEntity rec) {
		rtcRepository.save(rec);
		
	}

}
