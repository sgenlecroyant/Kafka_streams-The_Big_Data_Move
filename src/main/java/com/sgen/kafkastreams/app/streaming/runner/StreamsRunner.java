package com.sgen.kafkastreams.app.streaming.runner;

public interface StreamsRunner {
	/**
	 * @since version 1.0
	 * @author sgen
	 *         <h4>Hello World</h4>
	 * @param KafkaStreams, will be passed into the constructor of the class
	 *                      implementing this interface
	 * 
	 *                      This is subject to be implemented by any class which
	 *                      must have
	 * 
	 *                      the KafkaStreams instance as argument to call the start
	 *                      method
	 * 
	 */
	public void start();

}
