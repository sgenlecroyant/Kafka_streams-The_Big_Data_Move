package com.sgen.kafkastreams.app.model;

import com.sgen.kafkastreams.app.pattern.ShareVolumeBuilder;

public class ShareVolume {

	private String symbol;
	private int shares;
	private String industry;

	public ShareVolume(ShareVolumeBuilder builder) {
		this.symbol = builder.getSymbol();
		this.shares = builder.getShares();
		this.industry = builder.getIndustry();
	}

	public String getSymbol() {
		return symbol;
	}

	public int getShares() {
		return shares;
	}

	public String getIndustry() {
		return industry;
	}
	
	public ShareVolumeBuilder builder(StockTransaction stockTransaction) {
		ShareVolumeBuilder builder = new ShareVolumeBuilder();
		builder.setIndustry(stockTransaction.getIndustry());
		builder.setShares(stockTransaction.getShares());
		builder.setSymbol(stockTransaction.getSymbol());
		return builder;
	}

	@Override
	public String toString() {
		return "ShareVolume [symbol=" + symbol + ", shares=" + shares + ", industry=" + industry + "]";
	}

}
