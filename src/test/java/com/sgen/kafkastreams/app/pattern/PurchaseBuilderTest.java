package com.sgen.kafkastreams.app.pattern;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.github.javafaker.Faker;
import com.sgen.kafkastreams.app.model.Purchase;

class PurchaseBuilderTest {

	private Faker faker = new Faker();

	@Test
	@DisplayName("SUCCESS TEST: PURCHASE BUILDER PATTERN")
	void testSuccessBuilderPattern() {

		// Given
		// @formatter:off
		String itemName = "BOKA";
		String location = "NewYork City";
		int quantity = 23;
		int amount = 1000;
		String creditcardNumber = faker.business().creditCardNumber();
		Purchase purchase = 
				Purchase
				.builder()
				.id(1)
				.itemName(itemName)
				.amount(amount)
				.dateTime(LocalDateTime.now())
				.location(location)
				.quantity(quantity)
				.creditcardNumber(creditcardNumber)
				.build();
		// When
//		assertThat(purchase.getLocation()).isEqualTo(location);
		assertThat(purchase).matches((pchase) -> pchase.getLocation().equals(location));
		assertThat(purchase).hasNoNullFieldsOrProperties();
	}
	
	@Test
	@DisplayName("FAILURE TEST: PURCHASE BUILDER PATTERN")
	void testFailBuilderPattern() {

		// Given
		// @formatter:off
		String itemName = "BOKA";
		String location = "NewYork City";
		int quantity = 23;
		int amount = 1000;
		String creditcardNumber = faker.business().creditCardNumber();
		Purchase purchase = 
				Purchase
				.builder()
				.itemName(itemName)
				.amount(amount)
				.dateTime(LocalDateTime.now())
				.location(location)
				.quantity(quantity)
				.creditcardNumber(creditcardNumber)
				.build();
		// When
//		assertThat(purchase.getLocation()).isEqualTo(location);
		assertThat(purchase).matches((pchase) -> pchase.getLocation().equals(location));
		assertThat(purchase).hasNoNullFieldsOrPropertiesExcept("id");
	}
	
	@Test
	@DisplayName("NEW BUILDER: WITH EXTERNAL SOURCE")
	public void testNewBuilderMethod() {
		String creditcardNumber = faker.business().creditCardNumber();
		Purchase purchase = 
				Purchase.builder()
				.id(1000)
				.itemName("ItemName")
				.amount(1200)
				.quantity(34)
				.dateTime(LocalDateTime.now())
				.location("location")
				.creditcardNumber(creditcardNumber)
				.build();
		Purchase newPurchase = 
				Purchase.newBuilder(purchase)
				.build();
		
		assertThat(newPurchase.getId()).isEqualTo(purchase.getId());
		
		assertThat(newPurchase.getItemName()).isEqualTo(purchase.getItemName());
		assertThat(newPurchase.getAmount()).isEqualTo(purchase.getAmount());
		assertThat(newPurchase.getLocation()).isEqualTo(purchase.getLocation());
		assertThat(newPurchase.getDateTime()).isEqualTo(purchase.getDateTime());
		
		assertThat(newPurchase).hasNoNullFieldsOrProperties();
	}


}
