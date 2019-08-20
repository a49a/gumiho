package org.gumiho.demo.spark

import org.apache.spark.sql.DataFrame
import org.gumiho.lib.spark.{SparkSqlUtils, StructuredSink, StructuredSource}

object StructuredStreaming {
    def main(args: Array[String]): Unit = {
        val spark = SparkSqlUtils.sessionDevFactory()
        import spark.implicits._
        val lines = StructuredSource.socketStream(spark)
        val words = lines.as[String].flatMap{ _.split(" ", -1) }
        val wordCounts = words.groupBy("value").count()
        val query = StructuredSink.consoleSink(wordCounts)
        query.awaitTermination()
    }
}
