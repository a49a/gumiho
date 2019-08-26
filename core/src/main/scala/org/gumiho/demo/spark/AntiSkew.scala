package org.gumiho.demo.spark


import org.gumiho.lib.spark.SparkEnv
import scala.collection.mutable
import scala.util.Random

object AntiSkew {
    def main(args: Array[String]): Unit = {

    }
    
    //只适用于多个key shuffle到一个partition的情况，如果单个key数量过多，不适用。
    def makeMorePart() = {
        SparkEnv.init()
        val sc = SparkEnv.getContext()
        val rdd = sc.parallelize(
            Array(
                ("1", "1"),
                ("2", "2")
            )
        )
        val N = 1000
        rdd.repartition(N)
            .reduceByKey( _ + _ )
            .reduceByKey( _ + _ )

    }

    //双聚合
    def doubleAggregate() = {
        SparkEnv.init()
        val sc = SparkEnv.getContext()
        val rdd = sc.parallelize(
            Array(
                ("1", "1"),
                ("2", "2")
            )
        )
        rdd.map(randomPrefix)
            .reduceByKey( _ + _ )
            .map(removePrefix)
            .reduceByKey( _ + _ )

        def randomPrefix[T](pair: Tuple2[String, T]) = {
            val random = new Random()
            val N = 10
            val prefix = random.nextInt(N)
            (prefix + "_" + pair._1, pair._2)
        }

        def removePrefix[T](pair: Tuple2[String, T]) = {
            (pair._1.split("_", -1)(1), pair._2)
        }
    }

    //利用广播变量实现mapJoin代替普通reduceJoin
    def mapJoin() = {
        SparkEnv.init()
        val sc = SparkEnv.getContext()
        val rdd = sc.parallelize(
            Array(
                ("1", "1"),
                ("2", "2")
            )
        )
        val broadcastRdd = sc.broadcast(rdd.collect())
        rdd.map(joinRdd)

        def joinRdd(pair: Tuple2[String, String]) = {
            val list = broadcastRdd.value
            val map = mutable.Map[String, String]()
            for (item <- list) {
                map += (item._1 -> item._2)
            }
            val rightValue = map.get(pair._1)
            (pair._1, (pair._2, rightValue))
        }
    }


}
