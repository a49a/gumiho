import org.gumiho.lib.spark.SparkStreamingUtils

object StreamingDemo {
    def main(args: Array[String]): Unit = {
        val ssc = SparkStreamingUtils.contextFactory()
        val lines = ssc.socketTextStream("localhost", 9999)
        lines.print()
        ssc.start()             // Start the computation
        ssc.awaitTermination()  // Wait for the computation to terminate
    }
}
