package org.gumiho.batch.spark.lib

import org.apache.spark.sql.SparkSession

object StructuredSource {
    def socketStream(spark: SparkSession) = {
        val host = "localhost"
        val port = 9999
        spark.readStream
            .format("socket")
            .option("host", host)
            .option("port", port)
            .load()
    }

    def kafkaStream(spark: SparkSession) = {
        val topics = "foo-topic"
        val bootstrap = "localhost:9092"
        spark.readStream
            .format("kafka")
            .option("kafka.bootstrap.servers", bootstrap)
//            .option("maxOffsetsPerTrigger", 1000000)
            .option("subscribe", topics)
//            .option("startingOffsets", "earliest")
            .load()
    }
}
