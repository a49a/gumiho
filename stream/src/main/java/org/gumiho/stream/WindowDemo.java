package org.gumiho.stream;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.runtime.state.filesystem.FsStateBackend;
import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.WindowedStream;
import org.apache.flink.streaming.api.environment.CheckpointConfig;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.util.Collector;
import org.gumiho.stream.lib.Average;
import org.gumiho.stream.lib.BufferingSink;
import org.gumiho.stream.lib.EnvUtils;

public class WindowDemo {
    public static void main(String[] args) throws Exception {
        final String host = "localhost";
        final int port = 9999;
        final StreamExecutionEnvironment env = EnvUtils.genEnv();
        env.setStateBackend(new FsStateBackend("hdfs://flink/checkpoints"));

        DataStream<String> text = env.socketTextStream(host, port, "\n");
        text
                .map(new MapFunction<String, Tuple2<String, Integer>>() {
                    @Override
                    public Tuple2<String, Integer> map(String input) throws Exception {
                        return Tuple2.of(input, 1);
                    }
                })
                .keyBy(0)
                .map(new Average())
                .print()
                .setParallelism(1);
//        BufferingSink bufferSink = new BufferingSink(4);
//        text.map(new MapFunction<String, Tuple2<String, Integer>>() {
//            @Override
//            public Tuple2<String, Integer> map(String s) throws Exception {
//                return Tuple2.of(s, 1);
//            }
//        })
//        .addSink(bufferSink).setParallelism(1);

//        DataStream<WordWithCount> windowWordCounts = text.flatMap(new FlatMapFunction<String, WordWithCount>() {
//            @Override
//            public void flatMap(String value, Collector<WordWithCount> out) {
//                for (String word : value.split("\\s")) {
//                    out.collect(new WordWithCount(word, 1));
//                }
//            }
//        })
//        .keyBy("word")
//        .timeWindow(Time.seconds(4))
//        .reduce(new ReduceFunction<WordWithCount>() {
//            @Override
//            public WordWithCount reduce(WordWithCount wordWithCount, WordWithCount t1) throws Exception {
//                return new WordWithCount(wordWithCount.word, wordWithCount.count + t1.count);
//            }
//        });
//        windowWordCounts.print().setParallelism(1);
//
        env.execute("Java Socket Word Count");
    }

    public static class WordWithCount {
        public String word;
        public int count;

        public WordWithCount(String word, int count) {
            this.word = word;
            this.count = count;
        }
    }
}
