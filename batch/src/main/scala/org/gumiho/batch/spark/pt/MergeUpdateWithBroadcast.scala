package org.gumiho.batch.spark.pt

import org.gumiho.lib.spark.{OdsDataAccessServiceImpl, SparkEnv}

object MergeUpdateWithBroadcast {
    def main(args: Array[String]): Unit = {
        SparkEnv.init()
        val sc = SparkEnv.getContext()
        val leftDas = new OdsDataAccessServiceImpl("/home/cat/testdata/origin.txt", 2)
        val rightDas = new OdsDataAccessServiceImpl("/home/cat/testdata/update.txt", 2)
        leftDas.load()
        val leftRdd = leftDas.genKV()
        rightDas.load()
        val rightRdd = rightDas.getRdd()
        val set = rightRdd.map(x => {
            x(1)
        }).collect().toSet
        val bset = sc.broadcast(set)
        val cleaned = leftRdd.filter(x => {
            bset.value.contains(x._1)
        })
        val r = cleaned.values.union(rightRdd)
        r.foreach(println)
    }
}
