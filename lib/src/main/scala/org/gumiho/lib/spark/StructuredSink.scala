package org.gumiho.lib.spark

import org.apache.spark.sql.DataFrame

object StructuredSink {
    def kafkaSink(df: DataFrame) = {
        val bootstrap = "localhost:9092"
        val topics = "foo-topic"
        df.writeStream
            .format("kafka")
            .option("kafka.bootstrap.servers", bootstrap)
            .option("topic", topics)
            .start()
    }

    def consoleSink(df: DataFrame) = {
        val mode = "complete"
        df.writeStream
            .outputMode(mode)
            .format("console")
            .start()
    }
}
