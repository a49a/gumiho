package org.gumiho.demo.spark

import org.gumiho.lib.spark.SparkEnv

object ClassicCase {

    def main(args: Array[String]): Unit = {
        topN()
    }

    def mean() = {
        SparkEnv.init()
        val sc = SparkEnv.getContext()
        val rdd = sc.parallelize(
            Array(
                ("a", 1),
                ("a", 2),
                ("a", 3)
            )
        )
        rdd.groupByKey()
            .map(x => {

        }).collect().foreach(println)
    }

    def topN() = {
        val N = 5
        SparkEnv.init()
        val sc = SparkEnv.getContext()
        val rdd = sc.parallelize(
            Array(
                ("a", 1),
                ("a", 2),
                ("a", 3)
            )
        )

        rdd.groupByKey()
            .map(x => {
                x._2.toArray.sortWith(_ > _).take(N)
            })
            .foreach(println)

        rdd.aggregateByKey(Array(0))(
            (x, y) => {
                x(0) = y
                x
            },
            (x, y) => {
                x(0) = y(0)
                x
            }
        ).collect().foreach(x => {
            println(x._2(0))
        })
    }
}
