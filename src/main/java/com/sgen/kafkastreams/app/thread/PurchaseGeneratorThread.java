package com.sgen.kafkastreams.app.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sgen.kafkastreams.app.streaming.helloworld.DataProducer;
import com.sgen.kafkastreams.app.streaming.purchase.PurchaseStream;

public class PurchaseGeneratorThread implements Runnable {
	private static Logger LOGGER = LoggerFactory.getLogger(PurchaseStream.class);
	private ExecutorService executors = Executors.newFixedThreadPool(4);

	DataProducer randomPurchaseProducer = new DataProducer();

	@Override
	public void run() {
		int countPurchase = 0;
		while (true) {
			countPurchase++;
			randomPurchaseProducer.sendRandomPurchase();
			LOGGER.info("count: " + countPurchase + ", THREAD: " + Thread.currentThread().getName());
		}
	}

}
