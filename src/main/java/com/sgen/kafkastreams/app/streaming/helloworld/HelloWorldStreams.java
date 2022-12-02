package com.sgen.kafkastreams.app.streaming.helloworld;

import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.internals.KStreamImpl;
import org.springframework.kafka.config.KafkaStreamsInfrastructureCustomizer;

import com.sgen.kafkastreams.app.streaming.config.GlobalKafkaStreamsConfig;
import com.sgen.kafkastreams.app.streaming.runner.DefaultStreamsRunner;
import com.sgen.kafkastreams.app.streaming.runner.StreamsRunner;

//@SpringBootApplication
public class HelloWorldStreams {

	public static void main(String[] args) {
//		SpringApplication.run(KafkaStreamsBigDataMoveApplication.class, args);

		// creating the Global Configuration Settings instance by using the Singleton
		// Design Pattern
		GlobalKafkaStreamsConfig globalKafkaStreamsConfig = GlobalKafkaStreamsConfig.getInstance();
		StreamsConfig streamsConfig = globalKafkaStreamsConfig.applyDefaultConfigSettings();

		StreamsBuilder streamsBuilder = new StreamsBuilder();

		// the source processor which is reading from a Kafka Topic: hello-world
		KStream<Object, Object> sourceStream = streamsBuilder.stream("hello-world");
		// sending the result back to a specific topic since Kafka Streams is from Kafka
		// to Kafka
		sourceStream.to("hello-world-output");

		// bulding the KafkaStreams instance to be able to start our Streaming App later
		// on
		KafkaStreams kafkaStreams = globalKafkaStreamsConfig.getKafkaStreamsInstance(streamsBuilder, streamsConfig);

		StreamsRunner streamsRunner = new DefaultStreamsRunner(kafkaStreams);
		streamsRunner.start();
	}
}
