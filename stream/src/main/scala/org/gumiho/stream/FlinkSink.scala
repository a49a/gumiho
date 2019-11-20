package org.gumiho.stream

import java.util.Properties

import javax.annotation.Nullable
import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.streaming.api.scala.DataStream
import org.apache.flink.streaming.connectors.fs.StringWriter
import org.apache.flink.streaming.connectors.fs.bucketing.{BucketingSink, DateTimeBucketer}
import org.apache.flink.streaming.connectors.kafka.{FlinkKafkaProducer, KafkaSerializationSchema}
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer.Semantic
import org.apache.kafka.clients.producer.ProducerRecord

object FlinkSink {
    def getHdfsSink() = {
        val sink = new BucketingSink[String]("~/tmp/flink/")
        sink.setBucketer(new DateTimeBucketer("yyyy-MM-dd--HHmm"))
        sink.setWriter(new StringWriter[String])
        sink.setBatchSize(1024 * 1024 * 400) // this is 400 MB,
        sink.setBatchRolloverInterval(20 * 60 * 1000) // this is 20 mins
        sink
    }

    def getKafkaSink() = {
        val prop = new Properties()
        val schema = new KafkaSerializationSchema[String] {
            override def serialize(t: String, aLong: java.lang.Long): ProducerRecord[Nothing, String] = {
                new ProducerRecord("foo-dwd", t)
            }
        }
        val producer = new FlinkKafkaProducer[String](
            "foo-dwd",
            schema,
            prop,
            Semantic.EXACTLY_ONCE
        )
        producer.setWriteTimestampToKafka(true)
        producer
    }

}
