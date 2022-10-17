package com.sgen.kafkastreams.app.streaming.purchase;

import java.text.NumberFormat;
import java.time.Duration;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology.AutoOffsetReset;
import org.apache.kafka.streams.kstream.Branched;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.Grouped;
import org.apache.kafka.streams.kstream.JoinWindows;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Named;
import org.apache.kafka.streams.kstream.Predicate;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.kstream.StreamJoined;
import org.apache.kafka.streams.kstream.TimeWindows;
import org.apache.kafka.streams.kstream.ValueMapper;
import org.apache.kafka.streams.kstream.Windowed;
import org.apache.kafka.streams.kstream.WindowedSerdes;
import org.apache.kafka.streams.processor.TimestampExtractor;
import org.apache.kafka.streams.state.KeyValueBytesStoreSupplier;
import org.apache.kafka.streams.state.KeyValueStore;
import org.apache.kafka.streams.state.StoreBuilder;
import org.apache.kafka.streams.state.Stores;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.support.KafkaStreamBrancher;
import org.springframework.kafka.support.serializer.JsonSerde;

import com.fasterxml.jackson.core.type.TypeReference;
import com.sgen.kafkastreams.app.model.CorrelatedPurchase;
import com.sgen.kafkastreams.app.model.Purchase;
import com.sgen.kafkastreams.app.model.PurchasePattern;
import com.sgen.kafkastreams.app.model.RewardAccumulator;
import com.sgen.kafkastreams.app.model.ShareVolume;
import com.sgen.kafkastreams.app.model.StockTikerData;
import com.sgen.kafkastreams.app.model.StockTransaction;
import com.sgen.kafkastreams.app.model.TransactionSummary;
import com.sgen.kafkastreams.app.streaming.config.GlobalKafkaStreamsConfig;
import com.sgen.kafkastreams.app.streaming.helloworld.DataProducer;
import com.sgen.kafkastreams.app.streaming.joiner.PurchaseJoiner;
import com.sgen.kafkastreams.app.streaming.runner.DefaultStreamsRunner;
import com.sgen.kafkastreams.app.streaming.runner.StreamsRunner;
import com.sgen.kafkastreams.app.streaming.timestampextractor.PurchaseTimestampExtractor;
import com.sgen.kafkastreams.app.streaming.transformer.PurchaseTransformer;
import com.sgen.kafkastreams.app.streaming.util.StreamsUtil;
import com.sgen.kafkastreams.app.thread.PurchaseGeneratorThread;
import com.sgen.kafkastreams.app.util.FixedPriorityQueue;

@SpringBootApplication
// // @formatter:off
public class PurchaseStream {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PurchaseStream.class);

	

	private static Predicate<String, Purchase> isDepartmentCoffee = 
			(key, purchase) -> {
				if(purchase.getDepartment() != null) {
					return purchase.getDepartment().equalsIgnoreCase("coffee");
				}
				return false;
			};
	private static Predicate<String, Purchase> isDepartmentElectronics = 
					(key, purchase) -> {
						if(purchase.getDepartment() != null) {
							return purchase.getDepartment().equalsIgnoreCase("electronics");
						}
						return false;
					};

	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		SpringApplication.run(PurchaseStream.class, args);

		// creating the Global Configuration Settings instance by using the Singleton
		// Design Pattern
		GlobalKafkaStreamsConfig globalKafkaStreamsConfig = GlobalKafkaStreamsConfig.getInstance();
		StreamsConfig streamsConfig = globalKafkaStreamsConfig.applyDefaultConfigSettings();

		StreamsBuilder streamsBuilder = new StreamsBuilder();
		
		String storeName = "rewardPointsStore";
		KeyValueBytesStoreSupplier keyValueBytesStoreSupplier = 
				Stores.inMemoryKeyValueStore(storeName);
		
		StoreBuilder<KeyValueStore<String, Integer>> storeBuilder = 
				Stores.keyValueStoreBuilder(keyValueBytesStoreSupplier, Serdes.String(), Serdes.Integer());

		Map<String, String> loggingConfigs = 
				new HashMap<>();
		
		loggingConfigs.put("retention.ms", "172800000");
		loggingConfigs.put("cleanup.policy", "delete,compact");
		loggingConfigs.put("retention.bytes", "10000000000");
		
		storeBuilder.withLoggingEnabled(loggingConfigs);
		streamsBuilder.addStateStore(storeBuilder);

		// SERDES
		Serde<String> keySerde = Serdes.String();
		Serde<PurchasePattern> purchasePatternSerde = new JsonSerde<PurchasePattern>(PurchasePattern.class);

		// Let's use the JSON SERDE HERE
		Serde<Purchase> purchaseSerde = new JsonSerde<Purchase>(Purchase.class);
		Serde<CorrelatedPurchase> correlatedPurchaseSerde = new JsonSerde<>(CorrelatedPurchase.class);

		// Our PurchaseTimestampExtractor
		TimestampExtractor purchaseTimestampExtractor = new PurchaseTimestampExtractor();
		// the source processor which is reading from a Kafka Topic: hello-world
		
		KStream<String, Purchase> purchasesSourceStream = streamsBuilder
				.stream("purchases", Consumed.with(keySerde, purchaseSerde).withTimestampExtractor(purchaseTimestampExtractor))
				.mapValues((purchase) -> Purchase.newBuilder(purchase).maskCreditCard().build());
		// sending the result back to a specific topic since Kafka Streams is from Kafka
		// to Kafka
		Produced<String, Purchase> producedPurchase = Produced.with(keySerde, purchaseSerde);
		purchasesSourceStream.filter(StreamsUtil.isCheap())
								.to("inexpensive-purchases", producedPurchase);
		purchasesSourceStream.filterNot(StreamsUtil.isCheap())
								.to("expensive-purchases", producedPurchase);
		//branching into coffee and electronics
		KafkaStreamBrancher<String, Purchase> purchaseStreamBrancher = 
				new KafkaStreamBrancher<>();
		
		KStream<String, Purchase> coffeeSourceStream = 
				purchaseStreamBrancher
				.branch(isDepartmentCoffee, (coffeeStream) -> {
					coffeeStream.to("coffee", Produced.with(keySerde, purchaseSerde));
				}).onTopOf(purchasesSourceStream);
		
		
		KStream<String, Purchase> electronicSourceStream = 
				purchaseStreamBrancher
				.branch(isDepartmentElectronics, (electronicStream) -> electronicStream.to("electronics", Produced.with(keySerde, purchaseSerde)))
				.onTopOf(purchasesSourceStream);
		
		
		purchasesSourceStream.to("purchase-transactions", Produced.with(keySerde, purchaseSerde));
		// THE STORE NAME
		KStream<String, RewardAccumulator> rewardAccumulatorStream = purchasesSourceStream.transformValues(() -> new PurchaseTransformer(storeName), storeName);
		
		rewardAccumulatorStream.to("rewards", Produced.with(keySerde, new JsonSerde<>(RewardAccumulator.class)));
		
		KStream<String, PurchasePattern> purchasePatternStream = purchasesSourceStream.mapValues((purchase) -> PurchasePattern.builder(purchase).build());
		
		purchasePatternStream.to("patterns", Produced.with(keySerde, purchasePatternSerde));
		// building the KafkaStreams instance to be able to start our Streaming App later on
		String coffeeStream = "stream-branch-coffee";
		String electronicStream = "stream-branch-electronics";
		Map<String, KStream<String, Purchase>> coffeeAndElectronicStream = 
					purchasesSourceStream
					.split(Named.as("stream-branch-"))
					.branch(isDepartmentCoffee, Branched.withFunction((stream) -> stream, "coffee"))
					.branch(isDepartmentElectronics, Branched.withFunction((stream) -> stream, "electronics"))
					.noDefaultBranch();
		KStream<String, Purchase> coffeeStreamBranch = 
				coffeeAndElectronicStream.get(coffeeStream);
//		coffeeStreamBranch.print(Printed.<String, Purchase>toSysOut().withLabel("COFFEE_ONLY_STREAMS_SPLIT => "));
		KStream<String, Purchase> electronicStreamBranch = 
				coffeeAndElectronicStream.get(electronicStream);
//		electronicStreamBranch.print(Printed.<String, Purchase>toSysOut().withLabel("ELECTRONICS_ONLY_STREAMS_SPLIT"));
		
		
		JoinWindows oneMinuteJoinWindows = JoinWindows.ofTimeDifferenceWithNoGrace(Duration.ofMinutes(1));
		
		KStream<String, CorrelatedPurchase> correlatedPurchaseStream = coffeeStreamBranch.join(electronicStreamBranch, new PurchaseJoiner(), oneMinuteJoinWindows, StreamJoined.with(keySerde, purchaseSerde, purchaseSerde));
		correlatedPurchaseStream.to("purchase-streams", Produced.<String, CorrelatedPurchase>with(keySerde, correlatedPurchaseSerde));
		
		KTable<String, StockTikerData> stockTickerTable = streamsBuilder.table("stockticker-table", Consumed.with(keySerde, new JsonSerde<>(StockTikerData.class)));
		
		KStream<String, StockTikerData> stockTickerStream = streamsBuilder.stream("stockticker-stream", Consumed.with(keySerde, new JsonSerde<>(StockTikerData.class)));
		
		
//		stockTickerTable.toStream().print(Printed.<String, StockTikerData>toSysOut().withLabel("Stocks-KTable"));
//		stockTickerStream.print(Printed.<String, StockTikerData>toSysOut().withLabel("Stocks-KStream"));
		
		KTable<String, ShareVolume> reducedShareVolumes = streamsBuilder.stream("stock-transactions", Consumed.with(keySerde, new JsonSerde<>(StockTransaction.class))
				.withOffsetResetPolicy(AutoOffsetReset.LATEST))
				.mapValues((stockTransaction) -> ShareVolume.builder(stockTransaction).build())
				.groupBy((key, transaction) -> transaction.getSymbol(), Grouped.with(keySerde, new JsonSerde<>(ShareVolume.class)))
				
				.reduce(ShareVolume::sum);
		
//		reducedShareVolumes.toStream().print(Printed.<String, ShareVolume>toSysOut().withLabel("REDUCED_SHARE_VOLUMES"));
		reducedShareVolumes.toStream().to("reduced-share-volumes", Produced.with(keySerde, new JsonSerde<>(ShareVolume.class)));
		
		Comparator<ShareVolume> shareVolumeComparator = 
				(shareVolume1, shareVolume2) -> shareVolume1.getShares() - shareVolume2.getShares();
		Serde<ShareVolume> shareVolumeSerde = new JsonSerde<>(ShareVolume.class);
			
		FixedPriorityQueue<ShareVolume> fixedPriorityQueue = 
				new FixedPriorityQueue<>(shareVolumeComparator, 5);
		
		
//		reducedShareVolumes
//		.groupBy((key, value) -> KeyValue.pair(value.getSymbol(), value), Grouped.with(keySerde, new JsonSerde<>(ShareVolume.class)))
//		.aggregate(() -> fixedPriorityQueue,
//				(key, shareVolume, aggregator) -> aggregator.add(shareVolume), 
//				(key, shareVolume, aggregator) -> aggregator.remove(shareVolume),
//				Materialized.with(keySerde, new JsonSerde<>(new TypeReference<FixedPriorityQueue<ShareVolume>>() {
//				})))
//				.mapValues(priorityQueueMapper)
//				.toStream()
//				.peek((key, shareVolume) -> {
//					LOGGER.info("Stock Volume by industry {} {}", key, shareVolume);
//				})
//				.to("stock-volume-by-company", Produced.with(keySerde, keySerde));
		
		
		        KStream<String, StockTransaction> stockTransactionStream = streamsBuilder
				.stream("stock-transactions", Consumed.with(keySerde, new JsonSerde<>(StockTransaction.class))
				.withOffsetResetPolicy(AutoOffsetReset.LATEST));
		        
				stockTransactionStream.mapValues((key, value) -> ShareVolume.builder(value).build())
							.groupBy((key, value) -> value.getIndustry(), Grouped.with(keySerde, shareVolumeSerde))
							.reduce((v1, v2) -> ShareVolume.sum(v1, v2))
							.groupBy((key, value) -> KeyValue.pair(value.getIndustry(), value), Grouped.with(keySerde, shareVolumeSerde))
							.aggregate(() -> fixedPriorityQueue,
									(key, value, aggregator) -> aggregator.add(value),
									(key, value, aggregator) -> aggregator.remove(value),
									Materialized.with(keySerde, new JsonSerde<>(new TypeReference<FixedPriorityQueue<ShareVolume>>() {
									})))
									.mapValues(priorityQueueMapper)
									.toStream()
									.peek((key, value) -> LOGGER.info("Stock Volume by industry {} {}", key, value))
									.to("stock-volume-by-company", Produced.with(keySerde, keySerde));
		Serde<TransactionSummary> transactionSummarySerde = new JsonSerde<>(TransactionSummary.class);
		Serde<StockTransaction> stockTransactionSerde = new JsonSerde<>(StockTransaction.class);
		Duration windowLength = Duration.ofMinutes(2);
		
		Serde<Windowed<String>> windowSerde = WindowedSerdes.timeWindowedSerdeFrom(String.class, windowLength.toMillis());

				JsonSerde<KeyValue<String, StockTransaction>> jsonSerde = new JsonSerde<>(new TypeReference<KeyValue<String, StockTransaction>>() {});
		
		stockTransactionStream
				.groupByKey()
				.windowedBy(TimeWindows.ofSizeWithNoGrace(windowLength))
				.reduce(StockTransaction::reduce)
				.toStream()
				.to("windowed-transactions", Produced.with(windowSerde, stockTransactionSerde));

		KafkaStreams kafkaStreams = globalKafkaStreamsConfig.getKafkaStreamsInstance(streamsBuilder, streamsConfig);
		StreamsRunner streamsRunner = new DefaultStreamsRunner(kafkaStreams);
		streamsRunner.start();
		
		
		
//		 Logger LOGGER = LoggerFactory.getLogger(PurchaseStream.class);
//
//		DataProducer randomPurchaseProducer = new DataProducer();
//
//		AtomicInteger countPurchase = new AtomicInteger(0);

		
//		while (true) {
//			countPurchase.getAndIncrement();
//			countPurchase.incrementAndGet();
//			randomPurchaseProducer.sendRandomPurchase();
//			LOGGER.info("count: " + countPurchase + ", THREAD: " + Thread.currentThread().getName());
//		}

		int dummyThreads = 5;
		ExecutorService executorService = Executors.newFixedThreadPool(dummyThreads);

		for (int threadCount = 0; threadCount <= dummyThreads; threadCount++) {

			Runnable purchaseRunnable = new PurchaseGeneratorThread();
			Thread purchaseThread = new Thread(purchaseRunnable);
			executorService.execute(purchaseThread);
		}
		
		
		DataProducer dataProducer = new DataProducer();
//		dataProducer.generateStockTickerDataAndSend();
		
		dataProducer.generateRandomStockTransactions();
	}
	private static NumberFormat numberFormat = NumberFormat.getInstance();
	public static ValueMapper<FixedPriorityQueue<ShareVolume>, String> priorityQueueMapper = (fixedPriorityQueue) -> {
		StringBuilder topShareVolumesAsString = new StringBuilder();
		Iterator<ShareVolume> iterator = fixedPriorityQueue.iterate();
		int count = 1;
		while(iterator.hasNext()) {
			ShareVolume shareVolume = iterator.next();
			if(shareVolume != null) {
				System.out.println("SIZE NOW: " +fixedPriorityQueue.getInner().size());
				topShareVolumesAsString.append(count++).append(")").append(shareVolume.getSymbol())
				.append(":").append(numberFormat.format(shareVolume.getShares())).append(", Industry: " +shareVolume.getIndustry()).append(" ");
			}
		}
		return topShareVolumesAsString.toString();
	};
}
