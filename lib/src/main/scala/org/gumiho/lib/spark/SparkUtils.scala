package org.gumiho.lib.spark

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object SparkUtils {
    def contextFactory(appName: String = "foo") = {
        val conf = new SparkConf()
            .setAppName(appName)
            .setIfMissing("spark.master","local[*]")
            .set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
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
    //追加每天增量
    def appendIncr(full: RDD[String], incr: RDD[String])= {
        full.union(incr)
    }
    //根据主健合并每天增量
    def mergeIncr(full: RDD[String], incr: RDD[String], pkIndex: Int) = {

    }

    def main(args: Array[String]) = {
    }
}
