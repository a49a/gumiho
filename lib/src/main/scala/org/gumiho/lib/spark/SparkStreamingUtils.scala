package org.gumiho.lib.spark

import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.SparkConf
import org.apache.spark.streaming.kafka010.KafkaUtils
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe

object SparkStreamingUtils {

    def context() = {
        val ssc = StreamingContext
            .getOrCreate("/tmp/spark/checkpoint",
            () => SparkStreamingUtils.contextFactory("foo", 4))
        ssc
    }

    def contextFactory(appName: String = "foo", seconds: Int = 1) = {
        val conf = new SparkConf()
            .setAppName(appName)
            .setIfMissing("spark.master","local[*]")
            .set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
        val ssc = new StreamingContext(conf, Seconds(seconds))
        ssc.checkpoint("checkpoint")
        ssc
    }

    def kafkaDirectStreamFactory(streamingContext: StreamingContext) = {
        val kafkaParams = Map[String, Object](
            "bootstrap.servers" -> "localhost:9092",
            "key.deserializer" -> classOf[StringDeserializer],
            "value.deserializer" -> classOf[StringDeserializer],
            "group.id" -> "foo-group",
            "auto.offset.reset" -> "earliest",
            "enable.auto.commit" -> (false: java.lang.Boolean)
        )

        val topics = Array("topicA", "topicB")

        KafkaUtils.createDirectStream[String, String](
            streamingContext,
            PreferConsistent,
            Subscribe[String, String](topics, kafkaParams)
        )
    }
}
