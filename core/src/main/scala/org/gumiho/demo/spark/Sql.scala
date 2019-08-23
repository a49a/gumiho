package org.gumiho.demo.spark

import org.apache.spark.sql.SaveMode
import org.gumiho.lib.spark.SparkSqlUtils

object Sql {
    def main(args: Array[String]): Unit = {
        val spark = SparkSqlUtils.sessionDevFactory()
        import spark.implicits._
        val df = spark.read.json("sql-demo.json")
            .toDF()
        df.write.mode(SaveMode.Append).saveAsTable("")
    }
}
