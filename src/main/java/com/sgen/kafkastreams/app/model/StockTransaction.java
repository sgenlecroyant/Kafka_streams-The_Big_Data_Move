package com.sgen.kafkastreams.app.model;

import java.util.Date;

import com.sgen.kafkastreams.app.pattern.StockTransactionBuilder;

public class StockTransaction {

	private String symbol;
	private String sector;
	private String industry;
	private int shares;
	private double sharePrice;
	private String customerId;
	private Date transactionTimestamp;
	private boolean purchase;

	public StockTransaction(StockTransactionBuilder builder) {
		
	}

	public String getSymbol() {
		return symbol;
	}

	public String getSector() {
		return sector;
	}

	public String getIndustry() {
		return industry;
	}

	public int getShares() {
		return shares;
	}

	public double getSharePrice() {
		return sharePrice;
	}

	public String getCustomerId() {
		return customerId;
	}

	public Date getTransactionTimestamp() {
		return transactionTimestamp;
	}

	public boolean isPurchase() {
		return purchase;
	}

	@Override
	public String toString() {
		return "StockTransaction [symbol=" + symbol + ", sector=" + sector + ", industry=" + industry + ", shares="
				+ shares + ", sharePrice=" + sharePrice + ", customerId=" + customerId + ", transactionTimestamp="
				+ transactionTimestamp + ", purchase=" + purchase + "]";
	}
}
