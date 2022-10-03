package com.sgen.kafkastreams.app.pattern;

import java.time.LocalDateTime;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.github.javafaker.Faker;
import com.sgen.kafkastreams.app.model.Purchase;
import com.sgen.kafkastreams.app.model.RewardAccumulator;

class RewardAccumulatorBuilderTest {

	private RewardAccumulator rewardAccumulator;

	@BeforeEach
	public void setUpClass() {
		Faker faker = Faker.instance();
		String creditcardNumber = faker.business().creditCardNumber();
		String department = "department";
		String customerId = "customer-id";
		Purchase purchase = Purchase.builder().id(1000).customerId(customerId).itemName("ItemName").amount(1200)
				.quantity(34).dateTime(LocalDateTime.now()).location("location").creditcardNumber(creditcardNumber)
				.department(department).build();

		this.rewardAccumulator = RewardAccumulator.builder(purchase)
				.build();

	}

	@Test
	void test() {
		Assertions.assertThat(this.rewardAccumulator).hasNoNullFieldsOrPropertiesExcept("daysFromLastPurchase");

	}

}
