package com.lianjia.boxue.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfiger implements WebMvcConfigurer {
	public void addFormatters(FormatterRegistry registry) {
		registry.addFormatter(new DateFormatter("yyyy-MM-dd HH:mm:ss"));
		registry.addFormatter(new DateFormatter("yyyy-MM-dd"));
		registry.addFormatter(new DateFormatter("yyyy-MM"));
	}
}
