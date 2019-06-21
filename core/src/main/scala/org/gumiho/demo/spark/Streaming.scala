package org.gumiho.demo.spark

import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.gumiho.lib.spark.SparkStreamingUtils

object Streaming {
    // nc -lk 9999不行
    //nc -l -p 9999
    def main(args: Array[String]): Unit = {
        val ssc = StreamingContext.getOrCreate("/tmp/spark/checkpoint",
            () => SparkStreamingUtils.contextFactory("foo", 4))
        val lines = ssc.socketTextStream("localhost", 9999)
        val window = lines.window(Seconds(8))
        window.print()
        ssc.start()             // Start the computation
        ssc.awaitTermination()  // Wait for the computation to terminate
    }
}
