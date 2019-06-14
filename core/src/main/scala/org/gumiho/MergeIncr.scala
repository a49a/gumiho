package org.gumiho

import org.gumiho.lib.spark.{OdsDataAccessServiceImpl, SparkEnv, SparkUtils}

object MergeIncr {
    def main(args: Array[String]): Unit = {
        SparkEnv.init()
        val leftDas = new OdsDataAccessServiceImpl("/home/cat/testdata/origin.txt", 2)
        val rightDas = new OdsDataAccessServiceImpl("/home/cat/testdata/update.txt", 2)
        leftDas.load()
        val leftRdd = leftDas.genKV()
        rightDas.load()
        val rightRdd = rightDas.genKV()
        val r = leftRdd.fullOuterJoin(rightRdd)
        r.foreach(println)
    }
}
