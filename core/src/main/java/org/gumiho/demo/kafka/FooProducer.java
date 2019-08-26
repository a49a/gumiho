package org.gumiho.demo.kafka;

import com.alibaba.fastjson.JSON;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class FooProducer {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        Producer<String, String> producer = new KafkaProducer(props);
        String topic = "foo-topic";
        for(int i = 0; i < 10; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("index", i);
            map.put("value", 1);
            String msg = JSON.toJSONString(map);
            ProducerRecord<String, String> record = new ProducerRecord(topic, msg);
            try {
                producer.send(record).get();
            } catch (Exception e) {
                System.out.println(e);
            }
        }

    }
}
