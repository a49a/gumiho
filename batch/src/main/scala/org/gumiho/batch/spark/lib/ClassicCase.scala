package org.gumiho.batch.spark.lib

import org.apache.spark.rdd.RDD

object ClassicCase {

    def main(args: Array[String]): Unit = {
        SparkEnv.init()
        val sc = SparkEnv.getContext()
        val rdd = sc.parallelize(
            Array(
                ("a", 1),
                ("a", 2),
                ("a", 3)
            )
        )
        topN(rdd, 5)
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

    def topN(rdd: RDD[(String, Int)], n: Int) = {
        val N = n
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
        ).collect()
    }
}
