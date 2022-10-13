package com.sgen.kafkastreams.app.util;

import org.apache.kafka.common.serialization.Serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sgen.kafkastreams.app.config.JsonConfig;
import com.sgen.kafkastreams.app.model.StockTikerData;

public class StockTickerDataSerializer implements Serializer<StockTikerData> {

	private ObjectMapper objectMapper = JsonConfig.getDefaultConfiguredJsonInstance();

	@Override
	public byte[] serialize(String topic, StockTikerData data) {

		try {
			return this.objectMapper.writeValueAsString(data).getBytes();
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

}
