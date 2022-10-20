package com.sgen.kafkastreams.app.pattern;

import java.util.Date;

import com.sgen.kafkastreams.app.model.StockTransaction;

public class StockTransactionBuilder {

	private String symbol;
	private String sector;
	private String industry;
	private int shares;
	private double sharePrice;
	private String customerId;
	private Date transactionTimestamp;
	private boolean purchase;

	public StockTransactionBuilder() {
		// TODO Auto-generated constructor stub
	}

	public StockTransaction build() {
		return new StockTransaction(this);
	}

	public String getCustomerId() {
		return customerId;
	}

	public String getIndustry() {
		return industry;
	}

	public String getSector() {
		return sector;
	}

	public double getSharePrice() {
		return sharePrice;
	}

	public int getShares() {
		return shares;
	}

	public String getSymbol() {
		return symbol;
	}

	public Date getTransactionTimestamp() {
		return transactionTimestamp;
	}

	// ===========REGULAR SETTERS AND GETTERS=====================

	public boolean isPurchase() {
		return purchase;
	}

	public StockTransactionBuilder withCustomerId(String customerId) {
		this.customerId = customerId;
		return this;
	}

	public StockTransactionBuilder withIndustry(String industry) {
		this.industry = industry;
		return this;
	}

	public StockTransactionBuilder withPurchase(boolean purchase) {
		this.purchase = purchase;
		return this;
	}

	public StockTransactionBuilder withSector(String sector) {
		this.sector = sector;
		return this;
	}

	public StockTransactionBuilder withSharePrice(double sharePrice) {
		this.sharePrice = sharePrice;
		return this;
	}

	public StockTransactionBuilder withShares(int shares) {
		this.shares = shares;
		return this;
	}

	public StockTransactionBuilder withSymbol(String symbol) {
		this.symbol = symbol;
		return this;
	}

	public StockTransactionBuilder withTransactionTimestamp(Date transactionTimestamp) {
		this.transactionTimestamp = transactionTimestamp;
		return this;
	}
}
