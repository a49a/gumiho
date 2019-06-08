#!/usr/bin/env bash
kafka_path=$1
brokers="localhost:9092"
topic="test"
${kafka_path}bin/kafka-console-produce.sh --broker-list ${brokers} --topic ${topic} << EOF
hello
hello
EOF
