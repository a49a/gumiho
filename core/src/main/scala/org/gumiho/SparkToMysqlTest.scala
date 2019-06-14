package org.gumiho.util
import org.gumiho.lib.spark.SparkSQLUtils
case class Schema(
                     col1: Int,
                     col2: Int,
                     col3: Int,
                     col4: Int
                 )

object SparkToMysqlTest {
    def main(args: Array[String]) = {
        val spark = SparkSQLUtils.sessionDevFactory("foo")
        import spark.implicits._
        val sc = spark.sparkContext
        val len = 100000
        val v = for (i <- 0 to len) yield Schema(i, i, i, 4)
        val rdd = sc.parallelize(v)
        val df = rdd.toDF()
        val prop = SparkSQLUtils.genMysqlProp()
        val host = "foo"
        val url = SparkSQLUtils.genMysqlJdbcUrl(host, "foo")
        val table = "spark_foo"
        SparkSQLUtils.saveMysql(df, url, table, prop)
    }
}