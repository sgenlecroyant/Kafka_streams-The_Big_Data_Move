package com.sgen.kafkastreams.app.broker.message;

import java.time.LocalDateTime;

import com.sgen.kafkastreams.app.model.Purchase;
import com.sgen.kafkastreams.app.pattern.PurchaseMessageBuilder;

public class PurchaseMessage {

	private Integer id;
	private String itemName;
	private Integer quantity;
	private double amount;
	private LocalDateTime dateTime;
	private String location;

	public PurchaseMessage(PurchaseMessageBuilder builder) {
		this.id = builder.getId();
		this.itemName = builder.getItemName();
		this.amount = builder.getAmount();
		this.dateTime = builder.getDateTime();
		this.location = builder.getLocation();
		this.quantity = builder.getQuantity();

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
	
	public static PurchaseMessageBuilder builder(Purchase purchase) {
		return new PurchaseMessageBuilder(purchase);
	}

	@Override
	public String toString() {
		return "PurchaseMessage [id=" + id + ", itemName=" + itemName + ", quantity=" + quantity + ", amount=" + amount
				+ ", dateTime=" + dateTime + ", location=" + location + "]";
	}

}
