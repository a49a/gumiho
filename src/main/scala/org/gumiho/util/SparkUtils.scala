package org.gumiho.util

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object SparkUtils {
    def scFactory(appName: String = "foo", master: String = "local[*]") = {
        val conf = new SparkConf().setAppName(appName).setMaster(master)
        new SparkContext(conf)
    }

    def readLocalTextFile(sc: SparkContext, path: String) = {
        val file = sc.textFile(path)
        split(file, "\t")
    }

    def split(rdd: RDD[String], field: String = "\u0001") = {
        rdd.map(x => {
            x.split(field, -1)
        })
    }
    //取最新的一行数据
    def merge(rdd: RDD[Array[String]], pkIndex: Int) = {
        rdd.map(x => {
            (x(pkIndex), x)
        }).reduceByKey((x, y) => {
            y
        })
    }

    def main(args: Array[String]) = {
    }
}