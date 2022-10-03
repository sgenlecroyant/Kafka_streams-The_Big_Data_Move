package com.sgen.kafkastreams.app.pattern;

import java.time.LocalDateTime;

import org.apache.logging.log4j.util.Strings;

import com.sgen.kafkastreams.app.model.Purchase;

public class PurchaseBuilder {

	private Integer id;
	private String itemName;
	private Integer quantity;
	private double amount;
	private LocalDateTime dateTime;
	private String location;
	private String creditcardNumber;
	private String department;

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
		this.creditcardNumber = purchase.getCreditcardNumber();
		this.department = purchase.getDepartment();
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

	public PurchaseBuilder creditcardNumber(String creditcardNummber) {
		this.creditcardNumber = creditcardNummber;
		return this;
	}

	public PurchaseBuilder department(String department) {
		this.department = department;
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

	public PurchaseBuilder maskCreditCard() {
		if (this.creditcardNumber != null) {
			this.creditcardNumber = this.creditcardNumber.replaceFirst("[0-9]", Strings.repeat("*", 12));
		}
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

	public String getCreditcardNumber() {
		return creditcardNumber;
	}

	public String getDepartment() {
		return department;
	}

	public Purchase build() {
		return new Purchase(this);
	}

}
