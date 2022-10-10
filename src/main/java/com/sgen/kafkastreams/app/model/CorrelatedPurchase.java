package com.sgen.kafkastreams.app.model;

import java.time.LocalDateTime;
import java.util.List;

public class CorrelatedPurchase {

	private String customerId;
	private List<String> itemsPurchased;
	private double totalAmount;
	private LocalDateTime firstPurchaseTime;
	private LocalDateTime secondPurchaseTime;

	public CorrelatedPurchase() {
	}

	public String getCustomerId() {
		return customerId;
	}

	public List<String> getItemsPurchased() {
		return itemsPurchased;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public LocalDateTime getFirstPurchaseTime() {
		return firstPurchaseTime;
	}

	public LocalDateTime getSecondPurchaseTime() {
		return secondPurchaseTime;
	}

	@Override
	public String toString() {
		return "CorrelatedPurchase [customerId=" + customerId + ", itemsPurchased=" + itemsPurchased + ", totalAmount="
				+ totalAmount + ", firstPurchaseTime=" + firstPurchaseTime + ", secondPurchaseTime="
				+ secondPurchaseTime + "]";
	}

}
