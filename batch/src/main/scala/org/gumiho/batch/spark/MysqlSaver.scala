package org.gumiho.batch.spark

import org.gumiho.batch.spark.lib.SparkSqlUtils

object MysqlSaver {
    case class Schema(
                         col1: Int,
                         col2: Int,
                         col3: Int,
                         col4: Int
                     )

    def main(args: Array[String]) = {
        val spark = SparkSqlUtils.sessionDevFactory("foo")
        import spark.implicits._
        val sc = spark.sparkContext
        val len = 100000
        val v = for (i <- 0 to len) yield Schema(i, i, i, 4)
        val rdd = sc.parallelize(v)
        val df = rdd.toDF()
        val prop = SparkSqlUtils.genMysqlProp()
        val host = "foo"
        val url = SparkSqlUtils.genMysqlJdbcUrl(host, "foo")
        val table = "spark_foo"
        SparkSqlUtils.saveMysql(df, url, table, prop)
    }
}
