package org.gumiho.stream

import org.apache.flink.streaming.connectors.fs.StringWriter
import org.apache.flink.streaming.connectors.fs.bucketing.{BucketingSink, DateTimeBucketer}

object FlinkSink {
    def getHdfsSink() = {
        val sink = new BucketingSink[String]("~/tmp/flink/")
        sink.setBucketer(new DateTimeBucketer("yyyy-MM-dd--HHmm"))
        sink.setWriter(new StringWriter[String])
        sink.setBatchSize(1024 * 1024 * 400) // this is 400 MB,
        sink.setBatchRolloverInterval(20 * 60 * 1000) // this is 20 mins
        sink
    }

}
