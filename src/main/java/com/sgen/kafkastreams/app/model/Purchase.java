package com.sgen.kafkastreams.app.model;

import java.time.LocalDateTime;

public class Purchase {

	private Integer id;
	private String itemName;
	private Integer quantity;
	private double amount;
	private LocalDateTime date;

	private Purchase() {
		throw new RuntimeException("Instanciation Not Allowed! Class: " + this.getClass().getSimpleName());
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
		return date;
	}

	@Override
	public String toString() {
		return "Purchase [id=" + id + ", itemName=" + itemName + ", quantity=" + quantity + ", amount=" + amount
				+ ", date=" + date + "]";
	}

}
