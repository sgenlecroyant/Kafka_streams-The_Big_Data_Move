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
	
	public StockTransaction() {
		// TODO Auto-generated constructor stub
	}

	public StockTransaction(StockTransactionBuilder builder) {
		this.symbol = builder.getSymbol();
		this.sector = builder.getSector();
		this.industry = builder.getIndustry();
		this.shares = builder.getShares();
		this.sharePrice = builder.getSharePrice();
		this.customerId = builder.getCustomerId();
		this.transactionTimestamp = builder.getTransactionTimestamp();
		this.purchase = builder.isPurchase();
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

	public static StockTransaction reduce(StockTransaction transaction1, StockTransaction transaction2) {
		StockTransactionBuilder builder = StockTransaction.builder(transaction1);
		builder.withShares(transaction1.getShares() + transaction2.getShares());
		return builder.build();
	}

	public static StockTransactionBuilder builder() {
		return new StockTransactionBuilder();
	}

	public static StockTransactionBuilder builder(StockTransaction stockTransactionCopy) {
		StockTransactionBuilder builder = new StockTransactionBuilder();
		builder.withCustomerId(stockTransactionCopy.customerId);
		builder.withIndustry(stockTransactionCopy.getIndustry());
		builder.withPurchase(stockTransactionCopy.isPurchase());
		builder.withSector(stockTransactionCopy.getSector());
		builder.withSharePrice(stockTransactionCopy.getSharePrice());
		builder.withShares(stockTransactionCopy.getShares());
		builder.withSymbol(stockTransactionCopy.getSymbol());
		builder.withTransactionTimestamp(stockTransactionCopy.getTransactionTimestamp());
		return builder;
	}

	@Override
	public String toString() {
		return "StockTransaction [symbol=" + symbol + ", sector=" + sector + ", industry=" + industry + ", shares="
				+ shares + ", sharePrice=" + sharePrice + ", customerId=" + customerId + ", transactionTimestamp="
				+ transactionTimestamp + ", purchase=" + purchase + "]";
	}
}
