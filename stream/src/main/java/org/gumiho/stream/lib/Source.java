package org.gumiho.stream.lib;

import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.gumiho.stream.ConsumerStringDeserializationSchema;

import java.util.Properties;

public class Source {
    public static FlinkKafkaConsumer<String> getKafka(String topic) {
        Properties properties = new Properties();
        ConsumerStringDeserializationSchema schema = new ConsumerStringDeserializationSchema();
        FlinkKafkaConsumer consumer = new FlinkKafkaConsumer<String>(
                topic,
                schema,
                properties
        );
        return consumer;
    }
}
