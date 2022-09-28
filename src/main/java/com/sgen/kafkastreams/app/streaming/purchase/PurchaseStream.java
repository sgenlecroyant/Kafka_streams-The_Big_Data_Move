package com.sgen.kafkastreams.app.streaming.purchase;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.kstream.ValueMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.sgen.kafkastreams.app.streaming.config.GlobalKafkaStreamsConfig;
import com.sgen.kafkastreams.app.streaming.helloworld.DataProducer;
import com.sgen.kafkastreams.app.streaming.runner.DefaultStreamsRunner;
import com.sgen.kafkastreams.app.streaming.runner.StreamsRunner;

//@SpringBootApplication
public class PurchaseStream {

	public static ValueMapper<? super String, ? super String> valueMapper = (v) -> v;

	public static void main(String[] args) {
//		SpringApplication.run(PurchaseStream.class, args);

		// creating the Global Configuration Settings instance by using the Singleton
		// Design Pattern
		GlobalKafkaStreamsConfig globalKafkaStreamsConfig = GlobalKafkaStreamsConfig.getInstance();
		StreamsConfig streamsConfig = globalKafkaStreamsConfig.applyDefaultConfigSettings();

		StreamsBuilder streamsBuilder = new StreamsBuilder();

		// SERDES
		Serde<String> keySerde = Serdes.String();
		Serde<String> purchaseStringSerde = Serdes.String();
		// the source processor which is reading from a Kafka Topic: hello-world
		KStream<String, String> purchasesSourceStream = streamsBuilder.stream("purchases",
				Consumed.with(keySerde, purchaseStringSerde));
		// sending the result back to a specific topic since Kafka Streams is from Kafka
		// to Kafka
		purchasesSourceStream.to("purchase-transactions", Produced.with(keySerde, purchaseStringSerde));
		// bulding the KafkaStreams instance to be able to start our Streaming App later
		// on
		KafkaStreams kafkaStreams = globalKafkaStreamsConfig.getKafkaStreamsInstance(streamsBuilder, streamsConfig);

		StreamsRunner streamsRunner = new DefaultStreamsRunner(kafkaStreams);
		streamsRunner.start();

		DataProducer randomPurchaseProducer = new DataProducer();

		while (true) {
			randomPurchaseProducer.sendRandomPurchase();
		}

	}
}
