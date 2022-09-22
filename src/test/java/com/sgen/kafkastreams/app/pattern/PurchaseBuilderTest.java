package com.sgen.kafkastreams.app.pattern;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.sgen.kafkastreams.app.model.Purchase;

class PurchaseBuilderTest {

	@Test
	@DisplayName("TEST PURCHASE BUILDER PATTERN")
	void testBuild() {

		// Given
		// @formatter:off
		String itemName = "BOKA";
		String location = "NewYork City";
		int quantity = 23;
		int amount = 1000;
		Purchase purchase = 
				Purchase
				.builder()
				.id(1)
				.itemName(itemName)
				.amount(amount)
				.dateTime(LocalDateTime.now())
				.location(location)
				.quantity(quantity)
				.build();
		// When
//		assertThat(purchase.getLocation()).isEqualTo(location);
		assertThat(purchase).matches((pchase) -> pchase.getLocation().equals(location));
		assertThat(purchase).hasNoNullFieldsOrProperties();
	}

}
