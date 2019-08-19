package org.gumiho.demo.spark

import org.gumiho.lib.spark.SparkSqlUtils

object StructuredStreaming {
    def main(args: Array[String]): Unit = {
        val spark = SparkSqlUtils.sessionDevFactory()
        import spark.implicits._

    }
}
