package com.sgen.kafkastreams.app.streaming.timestampextractor;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.streams.processor.TimestampExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sgen.kafkastreams.app.model.Purchase;

public class PurchaseTimestampExtractor implements TimestampExtractor {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public long extract(ConsumerRecord<Object, Object> record, long partitionTime) {
		Purchase purchase = (Purchase) record.value();
		if (purchase == null) {
			logger.error("Failed on null record {}", purchase);
			throw new RuntimeException("can't extract timestamp with null record {}".formatted(purchase));
		}

		ZonedDateTime zonedDateTime = ZonedDateTime.of(purchase.getDateTime(), ZoneId.systemDefault());
//		return timestamp.getTime();
		return zonedDateTime.toInstant().toEpochMilli();
//		return purchase.getDateTime().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
	}

}
