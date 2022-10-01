package com.sgen.kafkastreams.app.pattern;

import java.time.LocalDateTime;

import com.sgen.kafkastreams.app.model.Purchase;
import com.sgen.kafkastreams.app.model.PurchasePattern;

public class PurchasePatternBuilder {

	private String itemName;
	private LocalDateTime dateTime;
	private double amount;

	public PurchasePatternBuilder(Purchase purchase) {
		this.itemName = purchase.getItemName();
		this.amount = purchase.getAmount();
		this.dateTime = purchase.getDateTime();
	}

	public PurchasePatternBuilder itemName(String itemName) {
		this.itemName = itemName;
		return this;
	}

	public PurchasePatternBuilder dateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
		return this;
	}

	public PurchasePatternBuilder amount(double amount) {
		this.amount = amount;
		return this;
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

	public PurchasePattern build() {
		return new PurchasePattern(this);
	}

}
