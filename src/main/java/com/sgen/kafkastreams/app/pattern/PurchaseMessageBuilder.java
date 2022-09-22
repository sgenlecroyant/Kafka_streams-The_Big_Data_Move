package com.sgen.kafkastreams.app.pattern;

import java.time.LocalDateTime;

import com.sgen.kafkastreams.app.broker.message.PurchaseMessage;
import com.sgen.kafkastreams.app.model.Purchase;

public class PurchaseMessageBuilder {
	
	private Integer id;
	private String itemName;
	private Integer quantity;
	private double amount;
	private LocalDateTime dateTime;
	private String location;
	
	public PurchaseMessageBuilder(Purchase purchase) {
		this.id = purchase.getId();
		this.itemName = purchase.getItemName();
		this.quantity = purchase.getQuantity();
		this.amount = purchase.getAmount();
		this.dateTime = purchase.getDateTime();
		this.location = purchase.getLocation();
	}
	
	public Integer getId() {
		return id;
	}
	public String getItemName() {
		return itemName;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public double getAmount() {
		return amount;
	}
	public LocalDateTime getDateTime() {
		return dateTime;
	}
	public String getLocation() {
		return location;
	}
	
	public PurchaseMessage build() {
		return new PurchaseMessage(this);
	}

}
