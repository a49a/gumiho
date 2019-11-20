package org.gumiho.batch.spark.lib

import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD

trait DataAccessService extends Serializable {
    val len: Int
    val path: String
    val pk: Int


    def load():Unit

    def genKV():RDD[(String, Array[String])]

    def getRdd():RDD[Array[String]]

    protected def filterLen(a: Array[String]): Boolean = {
        a.length >= len
    }
}
