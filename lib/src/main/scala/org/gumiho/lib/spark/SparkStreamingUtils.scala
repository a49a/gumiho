package org.gumiho.lib.spark

import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.SparkConf

object SparkStreamingUtils {
    def contextFactory(appName: String = "foo", seconds: Int = 1) = {
        val conf = new SparkConf()
            .setAppName(appName)
            .setIfMissing("spark.master","local[*]")
            .set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
        new StreamingContext(conf, Seconds(seconds))
    }
}
