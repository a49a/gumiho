package org.gumiho.lib.spark

import java.util.Properties

import org.apache.spark.sql.{DataFrame, SaveMode, SparkSession}

object SparkSqlUtils {
    def sessionDevFactory(appName: String = "foo", master: String = "local[*]") = {
        SparkSession
            .builder()
            .appName(appName)
            .master(master)
            .getOrCreate()
    }

    def sessionProdFactory(appName: String) = {
        SparkSession
            .builder()
            .appName(appName)
            .getOrCreate()
    }

    def hiveSparkSession(appName: String, dir: String) = {
        SparkSession
            .builder()
            .appName(appName)
            .config("spark.sql.warehouse.dir", dir)
            .enableHiveSupport()
            .getOrCreate()
    }


    def genMysqlProp() = {
        val prop: Properties = new Properties()
        prop.put("user", "foo")
        prop.put("password", "foo")
        prop.put("numPartitions", "8")
        prop.put("driver", "com.mysql.cj.jdbc.Driver")
        prop
    }

    def genMysqlJdbcUrl(host: String, db: String) = {
        s"jdbc:mysql://${host}/${db}?rewriteBatchedStatements=true"
    }

    def saveMysql(df: DataFrame, url: String, table: String, prop: Properties) = {

        //?rewriteBatchedStatements=true
        //JDBCOptions.JDBC_BATCH_INSERT_SIZE, 1000 // 设置批次大小
        df.write
            .mode(SaveMode.Overwrite)
            .jdbc(url, table, prop)
    }

}
