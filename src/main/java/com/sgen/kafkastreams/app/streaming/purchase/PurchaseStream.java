package com.sgen.kafkastreams.app.streaming.purchase;

import java.io.Serializable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.kafka.clients.producer.MockProducer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Printed;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.kstream.ValueMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.support.serializer.JsonSerde;

import com.sgen.kafkastreams.app.model.Purchase;
import com.sgen.kafkastreams.app.model.PurchasePattern;
import com.sgen.kafkastreams.app.streaming.config.GlobalKafkaStreamsConfig;
import com.sgen.kafkastreams.app.streaming.helloworld.DataProducer;
import com.sgen.kafkastreams.app.streaming.runner.DefaultStreamsRunner;
import com.sgen.kafkastreams.app.streaming.runner.StreamsRunner;
import com.sgen.kafkastreams.app.thread.PurchaseGeneratorThread;

@SpringBootApplication
// // @formatter:off
public class PurchaseStream {

	public static void main(String[] args) {
		SpringApplication.run(PurchaseStream.class, args);

		// creating the Global Configuration Settings instance by using the Singleton
		// Design Pattern
		GlobalKafkaStreamsConfig globalKafkaStreamsConfig = GlobalKafkaStreamsConfig.getInstance();
		StreamsConfig streamsConfig = globalKafkaStreamsConfig.applyDefaultConfigSettings();

		StreamsBuilder streamsBuilder = new StreamsBuilder();

		// SERDES
		Serde<String> keySerde = Serdes.String();
		Serde<String> purchaseStringSerde = Serdes.String();
		Serde<PurchasePattern> purchasePatternSerde = new JsonSerde<PurchasePattern>(PurchasePattern.class);

		// Let's use the JSON SERDE HERE
		Serde<Purchase> purchaseSerde = new JsonSerde<Purchase>(Purchase.class);

		// the source processor which is reading from a Kafka Topic: hello-world
		KStream<String, Purchase> purchasesSourceStream = streamsBuilder
				.stream("purchases", Consumed.with(keySerde, purchaseSerde))
				.mapValues((purchase) -> Purchase.newBuilder(purchase).maskCreditCard().build());
		// sending the result back to a specific topic since Kafka Streams is from Kafka
		// to Kafka
		purchasesSourceStream.to("purchase-transactions", Produced.with(keySerde, purchaseSerde));
		
		KStream<String, PurchasePattern> purchasePatternStream = purchasesSourceStream.mapValues((purchase) -> PurchasePattern.builder(purchase).build());
		
		purchasePatternStream.to("patterns", Produced.with(keySerde, purchasePatternSerde));
		// bulding the KafkaStreams instance to be able to start our Streaming App later on
		KafkaStreams kafkaStreams = globalKafkaStreamsConfig.getKafkaStreamsInstance(streamsBuilder, streamsConfig);

		StreamsRunner streamsRunner = new DefaultStreamsRunner(kafkaStreams);
		streamsRunner.start();
		
		
//		 Logger LOGGER = LoggerFactory.getLogger(PurchaseStream.class);
//
//		DataProducer randomPurchaseProducer = new DataProducer();
//
//		AtomicInteger countPurchase = new AtomicInteger(0);

		
//		while (true) {
//			countPurchase.getAndIncrement();
//			countPurchase.incrementAndGet();
//			randomPurchaseProducer.sendRandomPurchase();
//			LOGGER.info("count: " + countPurchase + ", THREAD: " + Thread.currentThread().getName());
//		}

		int dummyThreads = 5;
		ExecutorService executorService = Executors.newFixedThreadPool(dummyThreads);

		for (int threadCount = 0; threadCount <= dummyThreads; threadCount++) {

			Runnable purchaseRunnable = new PurchaseGeneratorThread();
			Thread purchaseThread = new Thread(purchaseRunnable);
			executorService.execute(purchaseThread);
		}

	}
}
