package org.gumiho.demo.spark

import org.apache.spark.sql.DataFrame
import org.gumiho.lib.spark.{SparkSqlUtils, StructuredSink, StructuredSource}

object StructuredStreaming {
    def main(args: Array[String]): Unit = {
        memory()
    }

    def kafka() = {
        val spark = SparkSqlUtils.sessionDevFactory()
        import spark.implicits._
        val df = StructuredSource.kafkaStream(spark)
        df.selectExpr("CAST(key AS STRING)", "CAST(value AS STRING)")
            .as[(String, String)]
    }

    def console() = {
        val spark = SparkSqlUtils.sessionDevFactory()
        import spark.implicits._
        val lines = StructuredSource.socketStream(spark)
        //as转化城DataSet
        val words = lines.as[String].flatMap{ _.split(" ", -1) }
        val wordCounts = words.groupBy("value").count()
        val query = StructuredSink.consoleSink(words, "update")
        query.awaitTermination()
    }

    def memory() = {
        val spark = SparkSqlUtils.sessionDevFactory()
        import spark.implicits._
        val lines = StructuredSource.socketStream(spark)
        //as转化城DataSet
        val words = lines.as[String].flatMap{ _.split(" ", -1) }
        val query = StructuredSink.memorySink(words, "append")
        spark.sql("SELECT count(*) FROM aggregates").show()
        query.awaitTermination()
    }
}
