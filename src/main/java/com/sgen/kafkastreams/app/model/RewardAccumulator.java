package com.sgen.kafkastreams.app.model;

import com.sgen.kafkastreams.app.pattern.RewardAccumulatorBuilder;

public class RewardAccumulator {

	private String customerId;
	private double purchaseTotalAmount;
	private int currentRewardPoints;
	private int totalRewardPoints;
	private int daysFromLastPurchase;

	public RewardAccumulator(RewardAccumulatorBuilder builder) {
		this.customerId = builder.getCustomerId();
		this.purchaseTotalAmount = builder.getPurchaseTotalAmount();
		this.currentRewardPoints = builder.getCurrentRewardPoints();
		this.totalRewardPoints = builder.getTotalRewardPoints();
		this.daysFromLastPurchase = builder.getDaysFromLasyPurchase();
	}

	public String getCustomerId() {
		return customerId;
	}

	public double getPurchaseTotalAmount() {
		return purchaseTotalAmount;
	}

	public Integer getCurrentRewardPoints() {
		return currentRewardPoints;
	}

	public Integer getTotalRewardPoints() {
		return totalRewardPoints;
	}

	public int getDaysFromLastPurchase() {
		return daysFromLastPurchase;
	}

	public void addRewardPoints(int previousTotalRewardPoints) {
		this.totalRewardPoints += previousTotalRewardPoints + this.currentRewardPoints;
	}

	public static RewardAccumulatorBuilder builder(Purchase purchase) {
		return new RewardAccumulatorBuilder(purchase);
	}

	@Override
	public String toString() {
		return "RewardAccumulator [customerId=" + customerId + ", purchaseTotalAmount=" + purchaseTotalAmount
				+ ", currentRewardPoints=" + currentRewardPoints + ", totalRewardPoints=" + totalRewardPoints
				+ ", daysFromLastPurchase=" + daysFromLastPurchase + "]";
	}

}
