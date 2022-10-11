package com.sgen.kafkastreams.app.streaming.joiner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.kafka.streams.kstream.ValueJoiner;

import com.sgen.kafkastreams.app.model.CorrelatedPurchase;
import com.sgen.kafkastreams.app.model.Purchase;
import com.sgen.kafkastreams.app.pattern.CorrelatedPurchaseBuilder;

public class PurchaseJoiner implements ValueJoiner<Purchase, Purchase, CorrelatedPurchase> {

	private String customerId;
	private List<String> itemsPurchased;
	private double totalAmount;
	private double firstPrice;
	private double secondPrice;
	private String firstItemPurchased;
	private String secondItemPurchased;
	private LocalDateTime firstPurchaseTime;
	private LocalDateTime secondPurchaseTime;

	// @formatter:off
	@Override
	public CorrelatedPurchase apply(Purchase firstPurchase, Purchase secondPurchase) {
		CorrelatedPurchaseBuilder builder = 
				new CorrelatedPurchaseBuilder();
		// the id of the first customer flowing into the topology
		this.customerId = firstPurchase.getCustomerId();
		
		this.firstPurchaseTime = firstPurchase != null ? firstPurchase.getDateTime(): null;
		this.firstPrice = firstPurchase != null ? firstPurchase.getAmount(): 0.0;
		this.firstItemPurchased = firstPurchase != null ? firstPurchase.getItemName(): null;
		
		
		this.secondPurchaseTime = secondPurchase != null? secondPurchase.getDateTime(): null;
		this.secondPrice = secondPurchase != null ? secondPurchase.getAmount(): 0.0;
		this.secondItemPurchased = secondPurchase != null ? secondPurchase.getItemName(): null;
		
		this.itemsPurchased = new ArrayList<>();
		
		if(firstItemPurchased != null) {
			itemsPurchased.add(this.getFirstItemPurchased()+ ":" +this.getFirstPrice());
		}
		
		if(secondItemPurchased != null) {
			itemsPurchased.add(this.getSecondItemPurchased()+ ":" +this.getSecondPrice());
		}
		this.totalAmount = firstPrice + secondPrice;
		
		CorrelatedPurchase correlatedPurchase = 
				builder.customerId(this.getCustomerId())
						.itemsPurchased(this.getItemsPurchased())
						.firstPurchaseTime(this.getFirstPurchaseTime())
						.secondPurchaseTime(this.getSecondPurchaseTime())
						.totalAmount(this.getTotalAmount())
						.build();
		
		return correlatedPurchase;
	}
	
	public List<String> getItemsPurchased() {
		return itemsPurchased;
	}

	public String getCustomerId() {
		return customerId;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public double getFirstPrice() {
		return firstPrice;
	}

	public double getSecondPrice() {
		return secondPrice;
	}

	public String getFirstItemPurchased() {
		return firstItemPurchased;
	}

	public String getSecondItemPurchased() {
		return secondItemPurchased;
	}

	public LocalDateTime getFirstPurchaseTime() {
		return firstPurchaseTime;
	}

	public LocalDateTime getSecondPurchaseTime() {
		return secondPurchaseTime;
	}
	
	

}
