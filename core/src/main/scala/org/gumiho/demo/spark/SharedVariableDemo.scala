package org.gumiho.demo.spark

import java.time.LocalDateTime
import java.util.Date

import org.gumiho.lib.spark.SparkEnv

object SharedVariableDemo {
    def main(args: Array[String]): Unit = {
        SparkEnv.init()
        val sc = SparkEnv.getContext()
        val broadcastVar = sc.broadcast(Array(1, 2, 3))
        //print(broadcastVar.value)

        val accum = sc.longAccumulator("My Accumulator")
        val array = new Array[Int](1000)
        for (i <- 0 until 1000) {
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
        import java.time.Duration
        val duration = Duration.between(startTime, endTime)
        println(duration)
        println(accum.value)
    }
}
