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
		this.id = purchase.getId();
		this.itemName = purchase.getItemName();
		this.amount = purchase.getAmount();
		this.dateTime = purchase.getDateTime();
		this.location = purchase.getLocation();
		this.quantity = purchase.getQuantity();
		System.out.println("\uD83D\uDE00");
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

}
