package org.gumiho.demo

import java.util.Properties

import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer010
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.connectors.fs.SequenceFileWriter
import org.apache.flink.streaming.connectors.fs.bucketing.{BucketingSink, DateTimeBucketer}
import org.gumiho.lib.flink.FlinkSource
import org.gumiho.lib.{FlinkEnv, FlinkSink}

object WordCount {
    def main(args: Array[String]): Unit = {
        val env = StreamExecutionEnvironment.getExecutionEnvironment
        val text = env.socketTextStream("localhost", 9999, '\n')
        val windowCounts = text.flatMap(x => {
            x.split("\\s")
        }).map(x => {
            WordWithCount(x, 1)
        }).keyBy("word")
            .timeWindow(Time.seconds(5), Time.seconds(1))
            .sum("count")
        windowCounts.print().setParallelism(1)
        env.execute("Socker Word Count")
    }

    case class WordWithCount(word: String, count: Long)
}
