package com.sgen.kafkastreams.app.model;

import java.io.Serializable;
import java.util.Comparator;

import com.sgen.kafkastreams.app.pattern.ShareVolumeBuilder;

public class ShareVolume implements Serializable, Comparable<ShareVolume> {

	private static final long serialVersionUID = 1L;
	private String symbol;
	private int shares;
	private String industry;

	public ShareVolume() {
		// TODO Auto-generated constructor stub
	}

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

	public static ShareVolumeBuilder builder(StockTransaction stockTransaction) {
		ShareVolumeBuilder builder = new ShareVolumeBuilder();
		builder.setIndustry(stockTransaction.getIndustry());
		builder.setShares(stockTransaction.getShares());
		builder.setSymbol(stockTransaction.getSymbol());
		return builder;
	}

	public static ShareVolumeBuilder builder(ShareVolume shareVolume) {
		ShareVolumeBuilder builder = new ShareVolumeBuilder();
		builder.setIndustry(shareVolume.getIndustry());
		builder.setShares(shareVolume.getShares());
		builder.setSymbol(shareVolume.getSymbol());
		return builder;
	}

	public static ShareVolume sum(ShareVolume shareVolume1, ShareVolume shareVolume2) {
		ShareVolumeBuilder builder = builder(shareVolume1);
		builder.setShares(builder.getShares() + shareVolume2.getShares());
		return builder.build();
	}

	public static ShareVolumeBuilder builder() {
		return new ShareVolumeBuilder();
	}

	@Override
	public String toString() {
		return "ShareVolume [symbol=" + symbol + ", shares=" + shares + ", industry=" + industry + "]";
	}

	@Override
	public int compareTo(ShareVolume otherShareVolume) {
		return this.getShares() - otherShareVolume.getShares();
	}

}
