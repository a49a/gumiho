package org.gumiho.demo.spark

import org.apache.log4j.{Level, Logger}
import org.apache.spark.streaming.kafka010.{CanCommitOffsets, HasOffsetRanges, OffsetRange}
import org.apache.spark.streaming.Seconds
import org.gumiho.lib.spark.SparkStreamingUtils

object Streaming {

    def main(args: Array[String]): Unit = {
        Logger.getLogger("org.apache.spark").setLevel(Level.WARN)

        //kafkaStarter()
        ordinaryStarter()
    }

    def kafkaStarter(): Unit = {
        val ssc = SparkStreamingUtils.context()
        val stream = SparkStreamingUtils.kafkaDirectStreamFactory(ssc)
        val window = stream.window(Seconds(8))
        var offsetRanges = Array.empty[OffsetRange]
        stream.transform(rdd => {
            offsetRanges = rdd.asInstanceOf[HasOffsetRanges].offsetRanges
            rdd
        }).foreachRDD( rdd => {
            stream.asInstanceOf[CanCommitOffsets].commitAsync(offsetRanges)
        })
        window.print()
        ssc.start()             // Start the computation
        ssc.awaitTermination()  // Wait for the computation to terminate
    }

    // nc -lk 9999不行
    //nc -l -p 9999
    def ordinaryStarter(): Unit = {
        val ssc = SparkStreamingUtils.context()
        val lines = ssc.socketTextStream("localhost", 9999)
        val window = lines.window(Seconds(8))
        window.print()

        val pairStream = window.map(x => (x, 1))
        val countStream = pairStream.updateStateByKey(updateRunningSum)
        countStream.print()
        ssc.start()             // Start the computation
        ssc.awaitTermination()  // Wait for the computation to terminate
    }

    def updateRunningSum(values: Seq[Int], state: Option[Int]) = {
        Some(state.getOrElse(0) + values.size)
    }
}
