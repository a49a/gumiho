package org.gumiho.demo.spark

import org.apache.spark.SparkContext
import org.gumiho.lib.spark.SparkEnv

class ThreadDemo(private val sc: SparkContext) extends Thread {
    override def run(): Unit = {
        val data = Array(1, 2, 3 ,4)
        val rdd = sc.parallelize(data)
        rdd.foreach(println)
    }
}

object MultithreadingDemo {
    def main(args: Array[String]): Unit = {
        SparkEnv.init()
        val sc = SparkEnv.getContext()
        val a = new ThreadDemo(sc)
        val b = new ThreadDemo(sc)
        a.start()
        b.start()
    }
}
