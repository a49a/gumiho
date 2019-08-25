package org.gumiho.demo.spark

import com.alibaba.fastjson.JSON
import org.apache.spark.sql.DataFrame
import org.gumiho.lib.spark.{SparkSqlUtils, StructuredSink, StructuredSource}

object StructuredStreaming {
    def main(args: Array[String]): Unit = {
//        memory()
        kafka()
    }

    def kafka() = {
        val spark = SparkSqlUtils.sessionDevFactory()
        import spark.implicits._
        val df = StructuredSource.kafkaStream(spark)
        val ds = df.selectExpr("CAST(key AS STRING)", "CAST(value AS STRING)")
            .as[(String, String)]
            .map(x => {
                val msg = JSON.parseObject(x._2)
                (msg.getInteger("index"), msg.getInteger("value"))
            })
        val query = StructuredSink.consoleSink(ds)
        query.awaitTermination()
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
