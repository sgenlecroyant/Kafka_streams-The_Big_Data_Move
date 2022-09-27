package com.sgen.kafkastreams.app.streaming.helloworld;

import java.util.Locale;

import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KeyValueMapper;
import org.apache.kafka.streams.kstream.ValueMapper;
import org.apache.kafka.streams.kstream.ValueMapperWithKey;

import com.sgen.kafkastreams.app.streaming.config.GlobalKafkaStreamsConfig;
import com.sgen.kafkastreams.app.streaming.runner.DefaultStreamsRunner;
import com.sgen.kafkastreams.app.streaming.runner.StreamsRunner;

//@SpringBootApplication
public class HelloWorldStreamsProcessing {

	public static ValueMapper<? super String, ? super String> valueMapper = (v) -> v;

	public static void main(String[] args) {
//		SpringApplication.run(KafkaStreamsBigDataMoveApplication.class, args);

		// creating the Global Configuration Settings instance by using the Singleton
		// Design Pattern
		GlobalKafkaStreamsConfig globalKafkaStreamsConfig = GlobalKafkaStreamsConfig.getInstance();
		StreamsConfig streamsConfig = globalKafkaStreamsConfig.applyDefaultConfigSettings();

		StreamsBuilder streamsBuilder = new StreamsBuilder();

		// the source processor which is reading from a Kafka Topic: hello-world
		KStream<String, String> sourceStream = streamsBuilder.stream("hello-world");
		// sending the result back to a specific topic since Kafka Streams is from Kafka
		// to Kafka

		sourceStream.mapValues(new ValueMapper<String, String>() {

			@Override
			public String apply(String value) {
				return value.toUpperCase();
			}
		}).to("hello-world-output");

		// bulding the KafkaStreams instance to be able to start our Streaming App later
		// on
		KafkaStreams kafkaStreams = globalKafkaStreamsConfig.getKafkaStreamsInstance(streamsBuilder, streamsConfig);

		StreamsRunner streamsRunner = new DefaultStreamsRunner(kafkaStreams);
		streamsRunner.start();
	}
}
