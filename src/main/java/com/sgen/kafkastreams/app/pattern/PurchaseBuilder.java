package com.sgen.kafkastreams.app.pattern;

import java.time.LocalDateTime;

import com.sgen.kafkastreams.app.model.Purchase;

public class PurchaseBuilder {

	private Integer id;
	private String itemName;
	private Integer quantity;
	private double amount;
	private LocalDateTime dateTime;
	private String location;

	private Purchase newPurchase;

	public PurchaseBuilder() {
		// TODO Auto-generated constructor stub
	}

	public PurchaseBuilder(Purchase purchase) {

		this.newPurchase = 
				Purchase.builder()
				.id(purchase.getId())
				.itemName(purchase.getItemName())
				.amount(purchase.getAmount())
				.quantity(purchase.getQuantity())
				.dateTime(purchase.getDateTime())
				.location(purchase.getLocation())
				.build();
	}

	public PurchaseBuilder id(Integer id) {
		this.id = id;
		return this;
	}

	public PurchaseBuilder itemName(String itemName) {
		this.itemName = itemName;
		return this;
	}

	public PurchaseBuilder quantity(Integer quantity) {
		this.quantity = quantity;
		return this;
	}

	public PurchaseBuilder amount(double amount) {
		this.amount = amount;
		return this;
	}

	public PurchaseBuilder dateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
		return this;
	}

	public PurchaseBuilder location(String location) {
		this.location = location;
		return this;
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

	public Purchase build() {
		return new Purchase(this);
	}

	public Purchase buildNew() {
		return this.newPurchase;
	}

}
