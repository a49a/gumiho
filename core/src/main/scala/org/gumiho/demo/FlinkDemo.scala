package org.gumiho.demo

import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment

object FlinkDemo {
    def main(args: Array[String]): Unit = {
        val env = StreamExecutionEnvironment.getExecutionEnvironment
        val text = env.socketTextStream("localhost", 9999)
        text.print()
        env.execute("hh")
    }
}
