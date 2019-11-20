package org.gumiho.stream;

import org.apache.flink.streaming.connectors.kafka.KafkaSerializationSchema;
import org.apache.kafka.clients.producer.ProducerRecord;

public class ProducerStringSerializationSchema implements KafkaSerializationSchema<String> {
    private String topic;

    public ProducerStringSerializationSchema(String topic) {
        super();
        this.topic = topic;
    }

    @Override
    public ProducerRecord<byte[], byte[]> serialize(String e, Long timestamp)  {
        return new ProducerRecord<byte[], byte[]>(topic, e.getBytes());
    }
}