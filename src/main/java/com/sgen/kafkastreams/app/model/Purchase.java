package com.sgen.kafkastreams.app.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sgen.kafkastreams.app.pattern.PurchaseBuilder;
import com.sgen.kafkastreams.app.util.CustomGenericLocalDateTimeDeserializer;
import com.sgen.kafkastreams.app.util.CustomGenericLocalDateTimeSerializer;

public class Purchase {

	private Integer id;
	private String itemName;
	private Integer quantity;
	private double amount;
	@JsonSerialize(using = CustomGenericLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomGenericLocalDateTimeDeserializer.class)
	private LocalDateTime dateTime;
	private String location;
	
	public Purchase() {
		// TODO Auto-generated constructor stub
	}

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

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public String getLocation() {
		return location;
	}

	public static PurchaseBuilder builder() {

		return new PurchaseBuilder();
	}

	// with data coming from an external source: VERY IMPORTANT
	public static PurchaseBuilder newBuilder(Purchase purchase) {
		return new PurchaseBuilder(purchase);
	}

	@Override
	public String toString() {
		return "Purchase [id=" + id + ", itemName=" + itemName + ", quantity=" + quantity + ", amount=" + amount
				+ ", dateTime=" + dateTime + "]";
	}

}
