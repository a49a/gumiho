package org.gumiho

import org.gumiho.lib.spark.SparkStreamingUtils

object StreamingDemo {
    // nc -lk 9999不行
    //nc -l -p 9999
    def main(args: Array[String]): Unit = {
        val ssc = SparkStreamingUtils.contextFactory()
        val lines = ssc.socketTextStream("localhost", 9999)
        lines.print()
        ssc.start()             // Start the computation
        ssc.awaitTermination()  // Wait for the computation to terminate
    }
}
