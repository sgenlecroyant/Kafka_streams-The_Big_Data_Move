package com.sgen.kafkastreams.app.pattern;

import java.time.LocalDateTime;
import java.util.List;

public class CorrelatedPurchaseBuilder {

	private String customerId;
	private List<String> itemsPurchased;
	private double totalAmount;
	private LocalDateTime firstPurchaseTime;
	private LocalDateTime secondPurchaseTime;

	public CorrelatedPurchaseBuilder() {

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

	public CorrelatedPurchaseBuilder customerId(String customerId) {
		this.customerId = customerId;
		return this;
	}

	public CorrelatedPurchaseBuilder itemsPurchased(List<String> itemsPurchased) {
		this.itemsPurchased = itemsPurchased;
		return this;
	}

	public CorrelatedPurchaseBuilder totalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
		return this;
	}

	public CorrelatedPurchaseBuilder firstPurchaseTime(LocalDateTime firstPurchaseTime) {
		this.firstPurchaseTime = firstPurchaseTime;
		return this;
	}

	public CorrelatedPurchaseBuilder secondPurchaseTime(LocalDateTime secondPurchaseTime) {
		this.secondPurchaseTime = secondPurchaseTime;
		return this;
	}

}
