package com.sgen.kafkastreams.app.pattern;

import com.sgen.kafkastreams.app.model.ShareVolume;

public class ShareVolumeBuilder {

	private String symbol;
	private int shares;
	private String industry;

	public ShareVolumeBuilder() {
		// TODO Auto-generated constructor stub
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public void setShares(int shares) {
		this.shares = shares;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
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

	public ShareVolume build() {
		return new ShareVolume(this);
	}

}
