package org.gumiho.demo.spark

import java.time.LocalDateTime
import java.time.Duration

import org.gumiho.lib.spark.SparkEnv

object PerformanceTuning {
    def main(args: Array[String]): Unit = {
        mapPartItor()
    }

    def cache() = {
        SparkEnv.init(master = "local[1]")
        val sc = SparkEnv.getContext()
        val accum = sc.longAccumulator("My Accumulator")
        val length = 1000
        val array = new Array[Int](length)
        for (i <- 0 until length) {
            array(i) = i
        }
        val a = sc.parallelize(array)
        val startTime = LocalDateTime.now()
        val b = a.map(x => {
            var a = x
            for (i <- 0 until 40000000) {
                a += 1
                a -= 1
            }
            a
        }).cache()

        val c = b.map{ _ + 2}
        val d = b.map{ _ + 2}

        val r = d.union(c)
        r.foreach(x => {
            accum.add(x)
        })
        val endTime = LocalDateTime.now()
        val duration = Duration.between(startTime, endTime)
        println(duration)
        println(accum.value)
    }

    def mapPartItor() = {
        SparkEnv.init(master = "local[4]")
        val sc = SparkEnv.getContext()

        val long = 620000000
        val list = new Array[Int](long)
        for (i <- 0 until long) {
            list(i) = i
        }
        val rdd = sc.parallelize(list, 8)

        val startTime = LocalDateTime.now()

        //val r = rdd.map{ _ + 1 }

//        val r = rdd.mapPartitions(iter =>{
//            new Iterator[Int] {
//                override def hasNext: Boolean = iter.hasNext
//                override def next(): Int = {
//                    iter.next + 1
//                }
//            }
//        })

        val r = rdd mapPartitions (iter => {
            iter.map( _ + 1)
        })
        println(r.reduce( _ + _ ))

        val endTime = LocalDateTime.now()
        val duration = Duration.between(startTime, endTime)
        println(duration)
    }
}
