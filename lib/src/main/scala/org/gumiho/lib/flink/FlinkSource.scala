package org.gumiho.lib.flink

import java.util.Properties

import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer010

object FlinkSource {
    def kafkaSourceStreamFactory() = {
        val bootstrap = "localhost:9092"
        val group = "foo-group"
        val topic = "foo-topic"
        val env = StreamExecutionEnvironment.getExecutionEnvironment
        val properties = new Properties()
        properties.setProperty("bootstrap.servers", bootstrap)
        properties.setProperty("group.id", group)
        env.addSource(
                new FlinkKafkaConsumer010[String](
                    topic,
                    new SimpleStringSchema(),
                    properties
                )
            )
    }
}
