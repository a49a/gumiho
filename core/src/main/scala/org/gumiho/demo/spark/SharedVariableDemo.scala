package org.gumiho.demo.spark

import org.gumiho.lib.spark.SparkEnv

object SharedVariableDemo {
    def main(args: Array[String]): Unit = {
        SparkEnv.init()
        val sc = SparkEnv.getContext()
        val broadcastVar = sc.broadcast(Array(1, 2, 3))
        print(broadcastVar.value)

        val accum = sc.longAccumulator("My Accumulator")
        sc.parallelize(Array(1, 2, 3, 4)).foreach(x => {
            accum.add(x)
        })
        print(accum.value)
    }
}
