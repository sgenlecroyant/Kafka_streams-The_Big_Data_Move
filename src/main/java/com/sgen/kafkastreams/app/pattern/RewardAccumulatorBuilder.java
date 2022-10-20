package com.sgen.kafkastreams.app.pattern;

import com.sgen.kafkastreams.app.model.Purchase;
import com.sgen.kafkastreams.app.model.RewardAccumulator;

public class RewardAccumulatorBuilder {

	private String customerId;
	private double purchaseTotalAmount;
	private int totalRewardPoints;
	private int currentRewardPoints;
	private int daysFromLasyPurchase;

	public RewardAccumulatorBuilder(Purchase purchase) {
		this.customerId = purchase.getCustomerId();
		this.purchaseTotalAmount = purchase.getAmount() * purchase.getQuantity();
		this.currentRewardPoints = (int) this.purchaseTotalAmount;
	}

	public String getCustomerId() {
		return customerId;
	}

	public double getPurchaseTotalAmount() {
		return purchaseTotalAmount;
	}

	public Integer getTotalRewardPoints() {
		return totalRewardPoints;
	}

	public int getCurrentRewardPoints() {
		return currentRewardPoints;
	}

	public int getDaysFromLasyPurchase() {
		return daysFromLasyPurchase;
	}

	@Override
	public String toString() {
		return "RewardAccumulatorBuilder [customerId=" + customerId + ", purchaseTotalAmount=" + purchaseTotalAmount
				+ ", totalRewardPoints=" + totalRewardPoints + ", currentRewardPoints=" + currentRewardPoints + "]";
	}

	public RewardAccumulator build() {
		return new RewardAccumulator(this);
	}

}
