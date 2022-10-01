package com.sgen.kafkastreams.app.model;

import java.time.LocalDateTime;

import com.sgen.kafkastreams.app.pattern.PurchasePatternBuilder;

public class PurchasePattern {

	private String itemName;
	private LocalDateTime dateTime;
	private double amount;

	public PurchasePattern(PurchasePatternBuilder builder) {
		this.itemName = builder.getItemName();
		this.dateTime = builder.getDateTime();
		this.amount = builder.getAmount();
	}

	public String getItemName() {
		return itemName;
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public double getAmount() {
		return amount;
	}
	
	public static PurchasePatternBuilder builder(Purchase purchase) {
		return new PurchasePatternBuilder(purchase);
	}

}
