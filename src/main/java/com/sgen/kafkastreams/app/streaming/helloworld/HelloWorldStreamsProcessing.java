package com.sgen.kafkastreams.app.streaming.helloworld;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KeyValueMapper;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.kstream.ValueMapper;
import org.apache.kafka.streams.kstream.ValueMapperWithKey;

import com.sgen.kafkastreams.app.model.Purchase;
import com.sgen.kafkastreams.app.streaming.config.GlobalKafkaStreamsConfig;
import com.sgen.kafkastreams.app.streaming.runner.DefaultStreamsRunner;
import com.sgen.kafkastreams.app.streaming.runner.StreamsRunner;

//@SpringBootApplication
public class HelloWorldStreamsProcessing {

	public static ValueMapper<? super String, ? super String> valueMapper = (v) -> v;

	public static void main(String[] args) throws InterruptedException {
//		SpringApplication.run(KafkaStreamsBigDataMoveApplication.class, args);

		// start working with Serdes

		Serde<String> keySerde = Serdes.String();
		Serde<String> valueSerde = Serdes.String();

		// creating the Global Configuration Settings instance by using the Singleton
		// Design Pattern
		GlobalKafkaStreamsConfig globalKafkaStreamsConfig = GlobalKafkaStreamsConfig.getInstance();
		StreamsConfig streamsConfig = globalKafkaStreamsConfig.applyDefaultConfigSettings();

		StreamsBuilder streamsBuilder = new StreamsBuilder();

		// the source processor which is reading from a Kafka Topic: hello-world
		KStream<String, String> sourceStream = streamsBuilder.stream("hello-world",
				Consumed.with(keySerde, valueSerde));
		// sending the result back to a specific topic since Kafka Streams is from Kafka
		// to Kafka

		sourceStream.mapValues(new ValueMapper<String, String>() {

			@Override
			public String apply(String value) {
				return value.toUpperCase();
			}
		}).to("hello-world-output", Produced.with(keySerde, valueSerde));

		// bulding the KafkaStreams instance to be able to start our Streaming App later
		// on
		KafkaStreams kafkaStreams = globalKafkaStreamsConfig.getKafkaStreamsInstance(streamsBuilder, streamsConfig);

		StreamsRunner streamsRunner = new DefaultStreamsRunner(kafkaStreams);
		streamsRunner.start();

		DataProducer greetingsDataProducer = new DataProducer();

		while (true) {
			greetingsDataProducer.sendRandomGreetings();
		}
	}
}
