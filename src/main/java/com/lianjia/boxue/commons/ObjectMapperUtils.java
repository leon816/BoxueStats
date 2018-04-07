package com.lianjia.boxue.commons;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectMapperUtils {

	public static ObjectMapper getObjectMapper() {
		return staticClassLazy.single;
	}

	private static class staticClassLazy {
		private static ObjectMapper single = new ObjectMapper();
	}
}
