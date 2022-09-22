package com.sgen.kafkastreams.app.pattern;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.sgen.kafkastreams.app.broker.message.PurchaseMessage;
import com.sgen.kafkastreams.app.model.Purchase;

class PurchaseMessageBuilderTest {

	@Test
	@DisplayName("SUCCESS_PURCHASE_MESSAGE_BUILDER")

	// @formatter:off
	void testSuccessPurchaseMessageBuilder() {
		String itemName = "HUAWEI";
		Integer id = 1000;
		double amount = 2500;
		LocalDateTime dateTime = LocalDateTime.now();
		String location = "TEXAS";
		Integer quantity = 23;
		
		Purchase purchase = 
				Purchase.builder()
				.id(id)
				.itemName(itemName)
				.amount(amount)
				.dateTime(dateTime)
				.location(location)
				.quantity(quantity)
				.build();
		
		PurchaseMessage purchaseMessage = 
				PurchaseMessage.builder(purchase)
				.build();
		
		assertThat(purchaseMessage.getLocation()).isEqualTo(purchase.getLocation());
	}

}
