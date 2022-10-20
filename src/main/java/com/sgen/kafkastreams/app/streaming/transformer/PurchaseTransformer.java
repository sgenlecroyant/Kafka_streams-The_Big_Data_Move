package com.sgen.kafkastreams.app.streaming.transformer;

import java.util.Objects;
import java.util.function.Supplier;

import org.apache.kafka.streams.kstream.ValueTransformer;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.apache.kafka.streams.state.KeyValueStore;

import com.sgen.kafkastreams.app.model.Purchase;
import com.sgen.kafkastreams.app.model.RewardAccumulator;

public class PurchaseTransformer implements ValueTransformer<Purchase, RewardAccumulator> {

	private ProcessorContext processorContext;
	private String storeName;
	private KeyValueStore<String, Integer> keyValueStateStore;

	public PurchaseTransformer(String storeName) {
		Objects.requireNonNull(storeName, new Supplier<String>() {

			@Override
			public String get() {
				return "storeName can't be null";
			}
		});
		this.storeName = storeName;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void init(ProcessorContext processorContext) {
		this.processorContext = processorContext;
		this.keyValueStateStore = (KeyValueStore<String, Integer>) this.processorContext.getStateStore(storeName);

	}

	@Override
	public RewardAccumulator transform(Purchase purchase) {
		RewardAccumulator rewardAccumulator = RewardAccumulator.builder(purchase).build();
		String customerId = purchase.getCustomerId();
		Integer accumulatedSoFar = this.keyValueStateStore.get(customerId);

		if (accumulatedSoFar != null) {
			rewardAccumulator.addRewardPoints(accumulatedSoFar);
		}

		this.keyValueStateStore.put(customerId, rewardAccumulator.getTotalRewardPoints());

		return rewardAccumulator;
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub

	}

}
