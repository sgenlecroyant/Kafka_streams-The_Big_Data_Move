package com.sgen.kafkastreams.app.interceptor;

import java.util.Map;

import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

public class PurchaseProducerInterceptor implements ProducerInterceptor<Object, Object> {

	@Override
	public void configure(Map<String, ?> configs) {
		// TODO Auto-generated method stub

	}

	@Override
	public ProducerRecord<Object, Object> onSend(ProducerRecord<Object, Object> record) {
		System.out.println("Produced: " + record);

		return record;
	}

	@Override
	public void onAcknowledgement(RecordMetadata metadata, Exception exception) {
		if (exception == null) {
			System.out.println("Record has been acknowledged" + metadata);
		} else {
			System.out.println("Encountered an error ");
		}
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub

	}

}
