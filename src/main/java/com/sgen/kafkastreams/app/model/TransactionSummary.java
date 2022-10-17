package com.sgen.kafkastreams.app.model;

public class TransactionSummary {

	private String customerId;
	private String stockTicker;
	private String industry;
	private long summaryCount;
	private String customerName;
	private String companyName;

	public TransactionSummary(String customerId, String stockTicker, String industry) {
		this.customerId = customerId;
		this.stockTicker = stockTicker;
		this.industry = industry;
	}

	public String getCustomerId() {
		return customerId;
	}

	public String getStockTicker() {
		return stockTicker;
	}

	public String getIndustry() {
		return industry;
	}

	public void setSummaryCount(long summaryCount) {
		this.summaryCount = summaryCount;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public long getSummaryCount() {
		return summaryCount;
	}

	public String getCustomerName() {
		return customerName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public static TransactionSummary from(StockTransaction transaction) {
		return new TransactionSummary(transaction.getCustomerId(), transaction.getSymbol(), transaction.getIndustry());
	}

	public TransactionSummary withCustomerName(String customerName) {
		this.customerName = customerName;
		return this;
	}

	public TransactionSummary withCompanyName(String companyName) {
		this.companyName = companyName;
		return this;
	}

	@Override
	public String toString() {
		return "TransactionSummary [customerId=" + customerId + ", stockTicker=" + stockTicker + ", industry="
				+ industry + ", summaryCount=" + summaryCount + ", customerName=" + customerName + ", companyName="
				+ companyName + "]";
	}

}
