package com.sgen.kafkastreams.app.streaming.helloworld;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Future;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.javafaker.Faker;

public class DataProducer {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

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

}
