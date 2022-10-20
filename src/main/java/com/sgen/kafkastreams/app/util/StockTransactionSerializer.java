package com.sgen.kafkastreams.app.util;

import org.apache.kafka.common.serialization.Serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sgen.kafkastreams.app.config.JsonConfig;
import com.sgen.kafkastreams.app.model.StockTransaction;

public class StockTransactionSerializer implements Serializer<StockTransaction> {
	private ObjectMapper objectMapper = JsonConfig.getDefaultConfiguredJsonInstance();

	@Override
	public byte[] serialize(String topic, StockTransaction data) {
		try {
			return this.objectMapper.writeValueAsBytes(data);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

}
