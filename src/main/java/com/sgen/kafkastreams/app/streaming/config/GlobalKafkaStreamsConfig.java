package com.sgen.kafkastreams.app.streaming.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;

import com.sgen.kafkastreams.app.streaming.timestampextractor.PurchaseTimestampExtractor;

public class GlobalKafkaStreamsConfig {

	private KafkaStreams kafkaStreams;
	private KStream<String, Object> kstream;

	private GlobalKafkaStreamsConfig() {
		// left empty and private to apply Singleton Design Pattern
	}

	private Map<String, Object> streamsProps = new HashMap<>();
	StreamsBuilder streamsBuilder = new StreamsBuilder();

	public static GlobalKafkaStreamsConfig getInstance() {
		return new GlobalKafkaStreamsConfig();
	}

	public StreamsConfig applyDefaultConfigSettings() {
		this.streamsProps.put(StreamsConfig.APPLICATION_ID_CONFIG, KafkaStreamsConfig.APP_ID);
		this.streamsProps.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaStreamsConfig.BOOTSTRAP_SERVER);
		this.streamsProps.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, KafkaStreamsConfig.DEFAULT_KEY_SERIALIZER);
		this.streamsProps.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG,
				KafkaStreamsConfig.DEFAULT_VALUE_SERIALIZER);
		this.streamsProps.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG, Integer.toString(100));

//		this.streamsProps.put(StreamsConfig.DEFAULT_TIMESTAMP_EXTRACTOR_CLASS_CONFIG, PurchaseTimestampExtractor.class);

		StreamsConfig streamsConfig = new StreamsConfig(streamsProps);

		return streamsConfig;
	}

	public KafkaStreams getKafkaStreamsInstance(StreamsBuilder streamsBuilder, StreamsConfig streamsConfig) {
		return new KafkaStreams(streamsBuilder.build(), streamsConfig);
	}

}
