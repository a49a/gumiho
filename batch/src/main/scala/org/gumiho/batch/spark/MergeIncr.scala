package org.gumiho.batch.spark

import org.gumiho.batch.spark.lib.{OdsDataAccessServiceImpl, SparkEnv, SparkUtils}

object MergeIncr {
    def main(args: Array[String]): Unit = {
        SparkEnv.init()
        val leftDas = new OdsDataAccessServiceImpl("/home/cat/testdata/origin.txt", 2)
        val rightDas = new OdsDataAccessServiceImpl("/home/cat/testdata/update.txt", 2)
        leftDas.load()
        val leftRdd = leftDas.genKV()
        rightDas.load()
        val rightRdd = rightDas.genKV()
        val r = SparkUtils.mergeIncr(leftRdd, rightRdd)
        r.foreach(x=> {
            println(x.mkString("\01"))
        })
    }
}
