package com.sgen.kafkastreams.app.streaming.util;

import org.apache.kafka.streams.kstream.Predicate;

import com.sgen.kafkastreams.app.model.Purchase;

public class StreamsUtil {
	
	public static final Predicate<String, Purchase> isCheap(){
		return (key, purchase) -> purchase.getAmount() <= 500;
	}

}
