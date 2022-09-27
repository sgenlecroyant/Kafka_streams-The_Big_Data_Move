package com.sgen.kafkastreams.app.streaming.runner;

import org.apache.kafka.streams.KafkaStreams;

import com.sgen.kafkastreams.app.streaming.config.GlobalKafkaStreamsConfig;

public class DefaultStreamsRunner implements StreamsRunner {

	private GlobalKafkaStreamsConfig globalKafkaStreamsConfig;
	private KafkaStreams kafkaStreams;

	public DefaultStreamsRunner(KafkaStreams kafkaStreams) {
		this.kafkaStreams = kafkaStreams;
	}

	@Override
	public void start() {
		this.globalKafkaStreamsConfig = GlobalKafkaStreamsConfig.getInstance();
		kafkaStreams.start();
	}

}
