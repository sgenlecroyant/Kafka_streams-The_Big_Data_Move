package com.sgen.kafkastreams.app.model;

import java.time.LocalDateTime;

import com.sgen.kafkastreams.app.pattern.PurchaseBuilder;

public class Purchase {

	private Integer id;
	private String itemName;
	private Integer quantity;
	private double amount;
	private LocalDateTime dateTime;
	private String location;

	public Purchase(PurchaseBuilder builder) {
		this.id = builder.getId();
		this.itemName = builder.getItemName();
		this.quantity = builder.getQuantity();
		this.amount = builder.getAmount();
		this.dateTime = builder.getDateTime();
		this.location = builder.getLocation();
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

	public LocalDateTime getDate() {
		return dateTime;
	}

	public String getLocation() {
		return location;
	}

	public static PurchaseBuilder builder() {

		return new PurchaseBuilder();
	}

	@Override
	public String toString() {
		return "Purchase [id=" + id + ", itemName=" + itemName + ", quantity=" + quantity + ", amount=" + amount
				+ ", dateTime=" + dateTime + "]";
	}

}
