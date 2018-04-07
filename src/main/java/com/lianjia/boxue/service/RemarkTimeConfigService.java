package com.lianjia.boxue.service;

import java.util.List;

import com.lianjia.boxue.entity.RemarkTimeConfigEntity;

public interface RemarkTimeConfigService {

	List<RemarkTimeConfigEntity> queryRemarkTimes();

	RemarkTimeConfigEntity add(RemarkTimeConfigEntity rec);

	void delete(String id);

	void update(RemarkTimeConfigEntity rec);
}
