package org.gumiho.lib

import org.apache.flink.api.scala.ExecutionEnvironment
import org.apache.flink.streaming.api.{CheckpointingMode, TimeCharacteristic}
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment

object FlinkEnv {

    def getStreamEnv() = {
        val env = StreamExecutionEnvironment.getExecutionEnvironment
        import org.apache.flink.streaming.api.environment.CheckpointConfig.ExternalizedCheckpointCleanup

        val config = env.getCheckpointConfig
        config.enableExternalizedCheckpoints(ExternalizedCheckpointCleanup.RETAIN_ON_CANCELLATION)
        config.setCheckpointingMode(CheckpointingMode.EXACTLY_ONCE)
        config.setMinPauseBetweenCheckpoints(500)
        config.setCheckpointTimeout(60000)
        config.setMaxConcurrentCheckpoints(1)

        env.setStreamTimeCharacteristic(TimeCharacteristic.ProcessingTime)
        env.enableCheckpointing(4000)
        env
    }

    def genEnv() = {
        val env = ExecutionEnvironment.getExecutionEnvironment
        env
    }
}
