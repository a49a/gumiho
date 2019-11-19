package org.gumiho.stream

import java.util.Properties

import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer
import org.apache.flink.streaming.util.serialization.SimpleStringSchema

object FlinkSource {
    //先启动 nc -lk 9999 在启动Flink可消费测试
    def getSocketTextStream(env: StreamExecutionEnvironment) = {
        val host = "localhost"
        val port = 9999
        env.socketTextStream(host, port)
    }

    def getKafkaSourceStream(env: StreamExecutionEnvironment) = {
        val bootstrap = "localhost:9092"
        val group = "foo-group"
        val topic = "foo-topic"
        val properties = new Properties()
        properties.setProperty("bootstrap.servers", bootstrap)
        properties.setProperty("group.id", group)
//        env.addSource(
//                new FlinkKafkaConsumer[String](
//                    topic,
//                    new SimpleStringSchema(),
//                    properties
//                )
//            )
    }
}
