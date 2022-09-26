#!/bin/bash

topics="hello-world transactions patterns rewards purchases stocks"

for topic in ${topics}; do
     echo "attempting to create topic ${topic}"
     ${KAFKA_HOME}/bin/kafka-topics.sh --bootstrap-server ${DEV_HOST}:9092 --topic ${topic} --partitions 3 --replication-factor 1 --create
done