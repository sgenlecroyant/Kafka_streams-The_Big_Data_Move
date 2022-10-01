package com.sgen.kafkastreams.app.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sgen.kafkastreams.app.pattern.PurchasePatternBuilder;
import com.sgen.kafkastreams.app.util.CustomGenericLocalDateTimeDeserializer;
import com.sgen.kafkastreams.app.util.CustomGenericLocalDateTimeSerializer;

public class PurchasePattern {

	private String itemName;
	@JsonSerialize(using = CustomGenericLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomGenericLocalDateTimeDeserializer.class)
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
