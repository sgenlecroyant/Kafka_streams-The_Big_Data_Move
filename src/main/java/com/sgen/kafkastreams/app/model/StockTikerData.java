package com.sgen.kafkastreams.app.model;

public class StockTikerData {

	private double price;
	private String symbol;

	public StockTikerData() {
		// TODO Auto-generated constructor stub
	}

	public StockTikerData(double price, String symbol) {
		this.price = price;
		this.symbol = symbol;
	}

	public double getPrice() {
		return price;
	}

	public String getSymbol() {
		return symbol;
	}

	@Override
	public String toString() {
		return "StockTikerData= { price=" + price + ", symbol=" + symbol + "}";
	}

}
