package com.sgen.kafkastreams.app.model;

public class RewardAccumulator {

	private String customerId;
	private double purchaseTotalAmount;
	private Integer currentRewardPoints;
	private Integer totalRewardPoints;
	private int daysFromLastPurchase;

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

	public void addRewardPoints(Integer previousTotalRewardPoints) {
		this.totalRewardPoints += previousTotalRewardPoints;
	}

	
}
