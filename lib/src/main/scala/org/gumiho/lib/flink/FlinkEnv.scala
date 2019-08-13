package org.gumiho.lib

import org.apache.flink.api.scala.ExecutionEnvironment
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment

object FlinkEnv {
    def getStreamEnv() = {
        val env = StreamExecutionEnvironment.getExecutionEnvironment
        env.enableCheckpointing(4000)
        env
    }

    def genEnv() = {
        val env = ExecutionEnvironment.getExecutionEnvironment
        env
    }
}
