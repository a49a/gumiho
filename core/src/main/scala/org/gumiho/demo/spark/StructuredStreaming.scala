package org.gumiho.demo.spark

import java.sql.Timestamp
import java.time.LocalDateTime
import com.alibaba.fastjson.JSON
import org.apache.spark.sql.DataFrame
import org.gumiho.lib.spark.{SparkSqlUtils, StructuredSink, StructuredSource}

import org.apache.spark.sql.functions._
case class Msg(
                  word: String,
                  timestamp: Timestamp
)
object StructuredStreaming {
    def main(args: Array[String]): Unit = {
//        waterMark()
        //        memory()
        kafka()
    }

    def kafka() = {
        val spark = SparkSqlUtils.sessionDevFactory()
        import spark.implicits._
        val df = StructuredSource.kafkaStream(spark)
        df.printSchema()
        val ds = df.selectExpr("CAST(key AS STRING)", "CAST(value AS STRING)")
            .as[(String, String)]
            .map(x => {
                val msg = JSON.parseObject(x._2)
                (msg.getInteger("index"), msg.getInteger("value"))
            })
        val query = StructuredSink.consoleSink(ds)
        query.awaitTermination()
    }

    def waterMark() = {
        val spark = SparkSqlUtils.sessionDevFactory()
        import spark.implicits._
        val lines = StructuredSource.socketStream(spark)
        //as转化城DataSet
        val words = lines
            .as[String]
            .flatMap{ _.split(" ", -1) }
            .map(x => {
                Msg(x, Timestamp.valueOf(LocalDateTime.now()))
            })
        val seconds = "10 seconds"
        val minutes = "10 minutes"
        val wordCounts = words
            .withWatermark("timestamp", "10 seconds")
            .groupBy(
            window($"timestamp", minutes, minutes),
            $"word"
        ).count().sort(desc("count"))
        val query = StructuredSink.consoleSink(wordCounts, "complete")
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
