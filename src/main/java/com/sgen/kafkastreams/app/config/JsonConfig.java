package com.sgen.kafkastreams.app.config;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class JsonConfig {

	private static ObjectMapper configuredObjectMapper = new ObjectMapper();

	public static final ObjectMapper getDefaultConfiguredJsonInstance() {
		Module dataTimeModule = new JavaTimeModule();
		configuredObjectMapper.registerModule(dataTimeModule);
		return configuredObjectMapper;
	}

}
