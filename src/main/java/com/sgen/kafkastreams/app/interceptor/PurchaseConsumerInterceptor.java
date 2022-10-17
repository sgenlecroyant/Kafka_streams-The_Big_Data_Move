package com.sgen.kafkastreams.app.interceptor;

import java.util.Iterator;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerInterceptor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;

public class PurchaseConsumerInterceptor implements ConsumerInterceptor<Object, Object> {

	@Override
	public void configure(Map<String, ?> configs) {
		// TODO Auto-generated method stub

	}

	@Override
	public ConsumerRecords<Object, Object> onConsume(ConsumerRecords<Object, Object> records) {
		System.out.println("consumed: " + this.buildMessageFromRecords(records.iterator()));
		return records;
	}

	@Override
	public void onCommit(Map<TopicPartition, OffsetAndMetadata> offsets) {
		// TODO Auto-generated method stub

	}

	@Override
	public void close() {
		// TODO Auto-generated method stub

	}

	public String buildMessageFromRecords(Iterator<ConsumerRecord<Object, Object>> records) {
		StringBuilder stringBuilder = new StringBuilder();
		while (records.hasNext()) {
			stringBuilder.append(records.next());
		}
		return stringBuilder.toString();
	}

}
