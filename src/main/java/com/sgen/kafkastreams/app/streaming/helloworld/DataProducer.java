package com.sgen.kafkastreams.app.streaming.helloworld;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import com.sgen.kafkastreams.app.config.JsonConfig;
import com.sgen.kafkastreams.app.config.PurchaseProducer;
import com.sgen.kafkastreams.app.config.StringProducer;
import com.sgen.kafkastreams.app.model.Purchase;

public class DataProducer {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private KafkaProducer<String, Purchase> purchaseKafkaProducer;
	private KafkaProducer<String, String> stringKafkaProducerProducer;

	public DataProducer() {
		this.purchaseKafkaProducer = new PurchaseProducer().initPurchaseProducer();
		this.stringKafkaProducerProducer = new StringProducer().initStringLiteralKafkaProducer();
	}

	public void sendRandomGreetings() {

		String randomFirstName = this.getFakerApi().name().firstName();
		String greetingMessage = String.format("Hello %s !", randomFirstName);
		ProducerRecord<String, String> producerRecord = new ProducerRecord<String, String>("hello-world",
				greetingMessage);

		Future<RecordMetadata> asynchSendResult = this.stringKafkaProducerProducer.send(producerRecord, new Callback() {

			@Override
			public void onCompletion(RecordMetadata metadata, Exception exception) {
				if (exception != null) {
					logger.error("Something went wrong while sending data !!!");
				}
			}
		});

	}

	public void sendRandomPurchase() {
//		this.objectMapper = JsonConfig.getDefaultConfiguredJsonInstance();
		Purchase purchase = this.generatePurchase();

		try {
			ProducerRecord<String, Purchase> purchaseProducerRecord = new ProducerRecord<String, Purchase>("purchases",
					purchase);
			this.purchaseKafkaProducer.send(purchaseProducerRecord);
			this.logger.info("published: {}", purchase);
		} catch (Exception e) {
			this.logger.error("failed to send purchase record due to: {}", e.getMessage());
		}
	}

	public Faker getFakerApi() {
		return new Faker(Locale.US);
	}

	public String generateRandomDepartment() {
		String[] departments = { "coffee", "electronics" };
		Random random = new Random();
		int randomPosition = random.nextInt(0, 2);
		return departments[randomPosition];

	}

	public Purchase generatePurchase() {
		Faker purchaseApi = this.getFakerApi();
		String id = purchaseApi.idNumber().valid();
		String itemName = purchaseApi.food().dish();
		int quantity = ThreadLocalRandom.current().nextInt(5, 30);
		double amount = ThreadLocalRandom.current().nextDouble(300, 800);
		String creditcardNumber = purchaseApi.business().creditCardNumber();
		LocalDateTime dateTime = LocalDateTime.now();
		String location = purchaseApi.country().name();
		String department = this.generateRandomDepartment();

		return Purchase.builder().id(ThreadLocalRandom.current().nextInt(1, 1000)).itemName(itemName).quantity(quantity)
				.amount(amount).dateTime(dateTime).location(location).creditcardNumber(creditcardNumber)
				.department(department).build();
	}

}
