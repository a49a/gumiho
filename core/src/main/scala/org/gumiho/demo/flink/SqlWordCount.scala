package org.gumiho.demo
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.table.api.EnvironmentSettings
import org.apache.flink.table.api.scala.StreamTableEnvironment


object SqlWordCount {
    def main(args: Array[String]): Unit = {
        val bsEnv = StreamExecutionEnvironment.getExecutionEnvironment
        val bsSettings = EnvironmentSettings.newInstance().useBlinkPlanner().inStreamingMode().build()
        val bsTableEnv = StreamTableEnvironment.create(bsEnv, bsSettings)
    }
}
