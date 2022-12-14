package com.sgen.kafkastreams.app.model;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sgen.kafkastreams.app.pattern.CorrelatedPurchaseBuilder;
import com.sgen.kafkastreams.app.util.CustomGenericLocalDateTimeDeserializer;
import com.sgen.kafkastreams.app.util.CustomGenericLocalDateTimeSerializer;

public class CorrelatedPurchase {

	private String customerId;
	private List<String> itemsPurchased;
	private double totalAmount;
	@JsonSerialize(using = CustomGenericLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomGenericLocalDateTimeDeserializer.class)
	private LocalDateTime firstPurchaseTime;
	
	@JsonSerialize(using = CustomGenericLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomGenericLocalDateTimeDeserializer.class)
	private LocalDateTime secondPurchaseTime;

	public CorrelatedPurchase(CorrelatedPurchaseBuilder builder) {
		this.customerId = builder.getCustomerId();
		this.itemsPurchased = builder.getItemsPurchased();
		this.totalAmount = builder.getTotalAmount();
		this.firstPurchaseTime = builder.getFirstPurchaseTime();
		this.secondPurchaseTime = builder.getSecondPurchaseTime();
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

	public static CorrelatedPurchaseBuilder newBuilder() {
		return new CorrelatedPurchaseBuilder();
	}

	@Override
	public String toString() {
		return "CorrelatedPurchase [customerId=" + customerId + ", itemsPurchased=" + itemsPurchased + ", totalAmount="
				+ totalAmount + ", firstPurchaseTime=" + firstPurchaseTime + ", secondPurchaseTime="
				+ secondPurchaseTime + "]";
	}

}
