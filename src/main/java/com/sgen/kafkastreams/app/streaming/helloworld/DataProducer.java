package com.sgen.kafkastreams.app.streaming.helloworld;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.javafaker.Faker;
import com.sgen.kafkastreams.app.config.PurchaseProducer;
import com.sgen.kafkastreams.app.config.StringProducer;
import com.sgen.kafkastreams.app.model.Purchase;
import com.sgen.kafkastreams.app.model.StockTikerData;
import com.sgen.kafkastreams.app.model.StockTransaction;
import com.sgen.kafkastreams.app.util.StockTickerDataSerializer;
import com.sgen.kafkastreams.app.util.StockTransactionSerializer;

public class DataProducer {

	private static Map<String, Object> stockTickerProducerConfigs = null;
	private KafkaProducer<String, StockTikerData> stockTickerProducer = null;
	private KafkaProducer<String, StockTransaction> stockTransactionProducer;
	private static Map<String, Object> stockTransactionsConfigs;

	static {
		stockTickerProducerConfigs = new HashMap<>();
		stockTickerProducerConfigs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		stockTickerProducerConfigs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		stockTickerProducerConfigs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StockTickerDataSerializer.class);

		// ============STOCK TRANSACTIONS===========

		stockTransactionsConfigs = new HashMap<>();
		stockTransactionsConfigs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		stockTransactionsConfigs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		stockTransactionsConfigs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StockTransactionSerializer.class);

	}

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private KafkaProducer<String, Purchase> purchaseKafkaProducer;
	private KafkaProducer<String, String> stringKafkaProducerProducer;

	public DataProducer() {
		this.purchaseKafkaProducer = new PurchaseProducer().initPurchaseProducer();
		this.stringKafkaProducerProducer = new StringProducer().initStringLiteralKafkaProducer();
	}

	public void sendRandomGreetings() {

		String randomFirstName = this.getFakerApi().name().firstName();
		String greetingMessage = String.format("Hello %s !", randomFirstName);
		ProducerRecord<String, String> producerRecord = new ProducerRecord<String, String>("hello-world",
				greetingMessage);

		Future<RecordMetadata> asynchSendResult = this.stringKafkaProducerProducer.send(producerRecord, new Callback() {

			@Override
			public void onCompletion(RecordMetadata metadata, Exception exception) {
				if (exception != null) {
					logger.error("Something went wrong while sending data !!!");
				}
			}
		});

	}

	public void sendRandomPurchase() {
//		this.objectMapper = JsonConfig.getDefaultConfiguredJsonInstance();
		Purchase purchase = this.generatePurchase();

		try {
			ProducerRecord<String, Purchase> purchaseProducerRecord = new ProducerRecord<String, Purchase>("purchases",
					purchase.getCustomerId(), purchase);
			this.purchaseKafkaProducer.send(purchaseProducerRecord);
//			this.logger.info("published: {}", purchase);
		} catch (Exception e) {
			this.logger.error("failed to send purchase record due to: {}", e.getMessage());
		}
	}

	public Faker getFakerApi() {
		return new Faker(Locale.US);
	}

	public String generateRandomDepartment() {
		String[] departments = { "coffee", "electronics" };
		Random random = new Random();
		int randomPosition = random.nextInt(0, 2);
		return departments[randomPosition];

	}

	public Purchase generatePurchase() {
		Faker purchaseApi = this.getFakerApi();
		String id = purchaseApi.idNumber().valid();
		String itemName = purchaseApi.food().dish();
		int quantity = ThreadLocalRandom.current().nextInt(5, 30);
		double amount = ThreadLocalRandom.current().nextDouble(300, 800);
//		String creditcardNumber = purchaseApi.business().creditCardNumber();
		String creditcardNumber = this.generateCreditCardNumber();
		LocalDateTime dateTime = LocalDateTime.now();
		String location = purchaseApi.country().name();
		String department = this.generateRandomDepartment();

		return Purchase.builder().id(ThreadLocalRandom.current().nextInt(1, 1000)).itemName(itemName).quantity(quantity)
				.amount(amount).dateTime(dateTime).location(location).creditcardNumber(creditcardNumber)
				.department(department).customerId(creditcardNumber).build();
	}

	private String generateCreditCardNumber() {
		String[] creditcardNumbers = { "card-12-23-34", "card-56-67-78" };
		Random random = new Random();
		int creditcardNumberPosition = random.nextInt(0, 2);
		return creditcardNumbers[creditcardNumberPosition];
	}

	public void generateStockTickerDataAndSend() {
		this.stockTickerProducer = new KafkaProducer<>(stockTickerProducerConfigs);
		logger.info("KStream vs KTable started");
		for (int i = 1; i <= 3; i++) {
			if (i == 2) {
				logger.info("StockData updates sent");
			}
			this.sendToStockTickerStream(stockTickerProducer);
			this.sendToStockTickerTable(stockTickerProducer);
		}
		logger.info("DOne sending Stock updates");
	}

	public void sendToStockTickerStream(KafkaProducer<String, StockTikerData> stockTickerProducer) {
		String stockTickerStream = "stockticker-stream";

		StockTikerData stockTikerData1 = new StockTikerData(new Random().nextInt(10, 30), "WORLD");
		StockTikerData stockTikerData2 = new StockTikerData(new Random().nextInt(10, 30), "YORLD");
		StockTikerData stockTikerData3 = new StockTikerData(new Random().nextInt(10, 30), "PORLD");

		ProducerRecord<String, StockTikerData> stockTickerRecord1 = new ProducerRecord<String, StockTikerData>(
				stockTickerStream, stockTikerData1.getSymbol(), stockTikerData1);
		ProducerRecord<String, StockTikerData> stockTickerRecord2 = new ProducerRecord<String, StockTikerData>(
				stockTickerStream, stockTikerData2.getSymbol(), stockTikerData2);
		ProducerRecord<String, StockTikerData> stockTickerRecord3 = new ProducerRecord<String, StockTikerData>(
				stockTickerStream, stockTikerData3.getSymbol(), stockTikerData3);
		List<ProducerRecord<String, StockTikerData>> stockTickers = List
				.<ProducerRecord<String, StockTikerData>>of(stockTickerRecord1, stockTickerRecord2, stockTickerRecord3);

		stockTickerProducer.send(stockTickerRecord1);
		stockTickerProducer.send(stockTickerRecord2);
		stockTickerProducer.send(stockTickerRecord3);

	}

	public void sendToStockTickerTable(KafkaProducer<String, StockTikerData> stockTickerProducer) {
		String stockTickerTable = "stockticker-table";

		StockTikerData stockTikerData1 = new StockTikerData(new Random().nextInt(10, 30), "WORLD");
		StockTikerData stockTikerData2 = new StockTikerData(new Random().nextInt(10, 30), "YORLD");
		StockTikerData stockTikerData3 = new StockTikerData(new Random().nextInt(10, 30), "PORLD");

		ProducerRecord<String, StockTikerData> stockTickerRecord1 = new ProducerRecord<String, StockTikerData>(
				stockTickerTable, stockTikerData1.getSymbol(), stockTikerData1);
		ProducerRecord<String, StockTikerData> stockTickerRecord2 = new ProducerRecord<String, StockTikerData>(
				stockTickerTable, stockTikerData2.getSymbol(), stockTikerData2);
		ProducerRecord<String, StockTikerData> stockTickerRecord3 = new ProducerRecord<String, StockTikerData>(
				stockTickerTable, stockTikerData3.getSymbol(), stockTikerData3);

		stockTickerProducer.send(stockTickerRecord1);
		stockTickerProducer.send(stockTickerRecord2);
		stockTickerProducer.send(stockTickerRecord3);

	}

	public void generateRandomStockTransactions() {
		stockTransactionProducer = new KafkaProducer<>(stockTransactionsConfigs);

		while (true) {
			generateStockTransaction();
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void generateStockTransaction() {
		Faker faker = Faker.instance();
		StockTransaction stockTransaction = StockTransaction.builder().withCustomerId(this.generateCreditCardNumber())
				.withIndustry(faker.company().name()).withPurchase(new Random().nextBoolean())
				.withSector(faker.commerce().department()).withSharePrice(new Random().nextDouble(1000, 3400))
				.withShares(new Random().nextInt(23, 56)).withTransactionTimestamp(new Date())
				.withSymbol(this.generateSymbol()).build();
		String topic = "stock-transactions";
		String key = stockTransaction.getCustomerId();
		ProducerRecord<String, StockTransaction> stockTransactionRecord = new ProducerRecord<String, StockTransaction>(
				topic, key, stockTransaction);
		this.stockTransactionProducer.send(stockTransactionRecord);
	}

	private String generateSymbol() {
		String[] symbols = { "WORLD", "YORLD", "JORLD" };
		return symbols[new Random().nextInt(0, 3)];
	}

}
