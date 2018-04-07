package com.lianjia.boxue.commons;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lianjia.boxue.domain.ResponseData;

public class Utils {
	private final static Logger logger = LoggerFactory.getLogger(Utils.class);

	public static String generateKey(String origin) {
		return "employee:" + origin;
	}

	public static <T>ResponseData<T> getResponseData(T orginObject) {
		ResponseData<T> r = new ResponseData<T>();
		r.setStatus(0);
		r.setMessage("success");
		r.setCode("200");
		r.setData(orginObject);
		return r;
	}

}
