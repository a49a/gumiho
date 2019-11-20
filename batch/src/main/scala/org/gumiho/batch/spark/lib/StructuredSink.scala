package org.gumiho.batch.spark.lib

import org.apache.spark.sql.streaming.DataStreamWriter
import org.apache.spark.sql.{DataFrame, Dataset, Row}

object StructuredSink {
    def kafkaSink(df: DataFrame) = {
        val bootstrap = "localhost:9092"
        val topics = "foo-topic"
        df.selectExpr("CAST(key AS STRING)", "CAST(value AS STRING)")
            .writeStream
            .format("kafka")
            .option("kafka.bootstrap.servers", bootstrap)
            .option("topic", topics)
            .start()
    }

    def consoleSink[T](ds: Dataset[T], mode: String = "append") = {
        ds.writeStream
            .outputMode(mode)
            .format("console")
            .start()
    }

    def memorySink[T](ds: Dataset[T], mode: String = "append") = {
        ds.writeStream
            .queryName("aggregates")    // queryName的值就是Table的名称
            .outputMode(mode)
            .format("memory")
            .start()
    }

    def foreachSink[T](ds: Dataset[T], mode: String = "append") = {
        //TODO
        ds.writeStream
                .outputMode(mode)
            .start()
    }

    def save(df: DataFrame) = {
        val bootstrap = "localhost:9092"
        val topics = "foo-topic"
        df.selectExpr("CAST(key AS STRING)", "CAST(value AS STRING)")
            .write
            .format("kafka")
            .option("kafka.bootstrap.servers", bootstrap)
            .option("topic", topics)
            .save()
    }
}
