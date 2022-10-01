package com.sgen.kafkastreams.app.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sgen.kafkastreams.app.streaming.helloworld.DataProducer;
import com.sgen.kafkastreams.app.streaming.purchase.PurchaseStream;

public class PurchaseGeneratorThread implements Runnable {
	private static Logger LOGGER = LoggerFactory.getLogger(PurchaseStream.class);
	private ExecutorService executors = Executors.newFixedThreadPool(4);

	DataProducer randomPurchaseProducer = new DataProducer();

	AtomicInteger countPurchase = new AtomicInteger(0);

	@Override
	public void run() {
		while (true) {
			countPurchase.getAndIncrement();
			countPurchase.incrementAndGet();
			randomPurchaseProducer.sendRandomPurchase();
			LOGGER.info("count: " + countPurchase + ", THREAD: " + Thread.currentThread().getName());
		}
	}

}
