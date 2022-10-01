package com.sgen.kafkastreams.app.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;

import com.sgen.kafkastreams.app.model.Purchase;
import com.sgen.kafkastreams.app.util.PurchaseSerializer;

public class PurchaseProducer {

	private static final String BOOTSTRAP_SERVERS = "localhost:9092";
	private static final Class<StringSerializer> DEFAULT_KEY_SERIALIZER = StringSerializer.class;
	private static final Class<StringSerializer> DEFAULT_VALUE_SERIALIZER = StringSerializer.class;

	private Map<String, Object> producerProps;
	private KafkaProducer<String, Purchase> kafkaProducer;

	private Map<String, Object> getProducerProps() {
		this.producerProps = new HashMap<>();
		this.producerProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
		this.producerProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, DEFAULT_KEY_SERIALIZER);
		this.producerProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, PurchaseSerializer.class);
		return this.producerProps;
	}

	public KafkaProducer<String, Purchase> initPurchaseProducer() {
		this.kafkaProducer = new KafkaProducer<>(this.getProducerProps());
		return this.kafkaProducer;
	}

}
