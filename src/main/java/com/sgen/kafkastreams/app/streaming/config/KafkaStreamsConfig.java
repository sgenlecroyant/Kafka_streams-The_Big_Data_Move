package com.sgen.kafkastreams.app.streaming.config;

import org.apache.kafka.common.serialization.Serdes;

public class KafkaStreamsConfig {

	public final static String BOOTSTRAP_SERVER = "localhost:9092";

	public final static Object DEFAULT_KEY_SERIALIZER = Serdes.String().getClass();

	public final static Object DEFAULT_VALUE_SERIALIZER = Serdes.String().getClass();

	public final static String APP_ID = "X-BANK-DATA-STREAMING-APP";

}
