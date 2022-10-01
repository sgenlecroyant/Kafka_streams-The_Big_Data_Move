package com.sgen.kafkastreams.app.util;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.common.utils.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sgen.kafkastreams.app.config.JsonConfig;
import com.sgen.kafkastreams.app.model.Purchase;

public class PurchaseSerializer implements Serializer<Purchase> {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private ObjectMapper objectMapper = JsonConfig.getDefaultConfiguredJsonInstance();

	@Override
	public byte[] serialize(String topic, Purchase purchase) {
		String purchaseAsString = null;
		try {
			purchaseAsString = this.objectMapper.writeValueAsString(purchase);
		} catch (JsonProcessingException e) {
			this.logger.error("Failed to serialize data: {}", purchase);
		}
		return purchaseAsString.getBytes(StandardCharsets.UTF_8);
	}

}
