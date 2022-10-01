package com.sgen.kafkastreams.app.pattern;

import java.time.LocalDateTime;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.github.javafaker.Faker;
import com.sgen.kafkastreams.app.model.Purchase;
import com.sgen.kafkastreams.app.model.PurchasePattern;

class PurchasePatternBuilderTest {

	private Faker faker = Faker.instance();

	@Test
	@DisplayName("PURCHASE_PATTERN_BUILDER: SUCCESS")
	public void testPurchasePatternBuilder() {
		String itemName = "BOKA";
		String location = "NewYork City";
		int quantity = 23;
		int amount = 1000;
		String creditcardNumber = faker.business().creditCardNumber();

		Purchase purchase = Purchase.builder().itemName(itemName).amount(amount).dateTime(LocalDateTime.now())
				.location(location).quantity(quantity).creditcardNumber(creditcardNumber).build();

		PurchasePattern purchasePattern = PurchasePattern.builder(purchase).build();

		Assertions.assertThat(purchasePattern).hasNoNullFieldsOrProperties();
	}

}
