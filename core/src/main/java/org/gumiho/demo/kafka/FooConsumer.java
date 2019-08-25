package org.gumiho.demo.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.Properties;

public class FooConsumer {
    public static void main(String[] args) {
        Logger logger = Logger.getLogger(FooConsumer.class);
        String topicName = "foo-topic";
        String clientId = "foo-clientid";
        String groupId = "foo-group";

        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("client.id", clientId);
        props.put("group.id", groupId);
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("auto.offset.reset", "earliest");
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<String, String>(props);
        kafkaConsumer.subscribe(Arrays.asList(topicName));

//        try {
            while (true) {
                ConsumerRecords<String, String> records = kafkaConsumer.poll(1000);
                for (ConsumerRecord<String, String> record : records) {
//                try {
//                    insertBehaviorPhoneLog(tablesName, record.offset(), record.value());
//                } catch (IOException e) {
//                    logger.error(record.offset() + "_" + record.value());
//                    e.printStackTrace();
//                }
                    String str = String.format("topic:%s partition:%s offset:%s key:%s value:%s",
                            record.topic(),
                            record.partition(),
                            record.offset(),
                            record.key(),
                            record.value()
                    );
                    System.out.println(str);
                    logger.info(str);
                }
            }
//        } finally {
//            kafkaConsumer.close();
//        }

    }
}
