package com.sgen.kafkastreams.app.streaming.helloworld;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.javafaker.Faker;
import com.sgen.kafkastreams.app.config.JsonConfig;
import com.sgen.kafkastreams.app.model.Purchase;

public class DataProducer {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private ObjectMapper objectMapper;
	private static final String BOOTSTRAP_SERVERS = "localhost:9092";
	private static final Class<StringSerializer> DEFAULT_KEY_SERIALIZER = StringSerializer.class;
	private static final Class<StringSerializer> DEFAULT_VALUE_SERIALIZER = StringSerializer.class;

	private Map<String, Object> producerProps;

	private KafkaProducer<String, Object> kafkaProducer;

	public void sendRandomGreetings() {
		this.kafkaProducer = new KafkaProducer<>(this.getProducerProps());

		String randomFirstName = this.getFakerApi().name().firstName();
		Object greetingMessage = String.format("Hello %s !", randomFirstName);
		ProducerRecord<String, Object> producerRecord = new ProducerRecord<String, Object>("hello-world",
				greetingMessage);

		Future<RecordMetadata> asynchSendResult = this.kafkaProducer.send(producerRecord, new Callback() {

			@Override
			public void onCompletion(RecordMetadata metadata, Exception exception) {
				if (exception != null) {
					logger.error("Something went wrong while sending data !!!");
				}
			}
		});

	}

	public void sendRandomPurchase() {
		this.objectMapper = JsonConfig.getDefaultConfiguredJsonInstance();
		this.kafkaProducer = new KafkaProducer<>(this.getProducerProps());
		Purchase purchase = this.generatePurchase();

		try {
			String purchaseAsString = this.objectMapper.writeValueAsString(purchase);
			ProducerRecord<String, Object> purchaseProducerRecord = new ProducerRecord<String, Object>("purchases",
					purchaseAsString);
			this.kafkaProducer.send(purchaseProducerRecord);
			this.logger.info("published: {}", purchaseAsString);
		} catch (Exception e) {
			this.logger.error("failed to send purchase record due to: {}", e.getMessage());
		}
	}

	private Map<String, Object> getProducerProps() {
		this.producerProps = new HashMap<>();
		this.producerProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
		this.producerProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, DEFAULT_KEY_SERIALIZER);
		this.producerProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, DEFAULT_VALUE_SERIALIZER);
		return this.producerProps;
	}

	public Faker getFakerApi() {
		return new Faker(Locale.US);
	}

	public Purchase generatePurchase() {
		Faker purchaseApi = this.getFakerApi();
		String id = purchaseApi.idNumber().valid();
		String itemName = purchaseApi.food().dish();
		int quantity = ThreadLocalRandom.current().nextInt(5, 30);
		double amount = ThreadLocalRandom.current().nextDouble(300, 800);

		LocalDateTime dateTime = LocalDateTime.now();
		String location = purchaseApi.country().name();

		return Purchase.builder().id(ThreadLocalRandom.current().nextInt(1, 1000)).itemName(itemName).quantity(quantity)
				.amount(amount).dateTime(dateTime).location(location).build();
	}

}
