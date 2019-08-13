package org.gumiho.demo

import java.util.Properties

import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer010
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.connectors.fs.SequenceFileWriter
import org.apache.flink.streaming.connectors.fs.bucketing.{BucketingSink, DateTimeBucketer}
import org.gumiho.lib.flink.FlinkSource
import org.gumiho.lib.{FlinkEnv, FlinkSink}

object FlinkDemo {
    def main(args: Array[String]): Unit = {
        val env = FlinkEnv.getStreamEnv()
        val stream = FlinkSource.getSocketTextStream(env)
        stream.print()
        env.execute("hh")
    }
}
