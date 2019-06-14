package org.gumiho.lib.spark

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object SparkUtils {
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
    def mergeIncr(full: RDD[(String, Array[String])], incr: RDD[(String, Array[String])]) = {
        val joined = full.fullOuterJoin(incr)
        joined.map(x => {
            x._2._2 match {
                case None => x._2._1.get
                case Some(value) => value
            }
        })
    }

    def main(args: Array[String]) = {
    }
}
