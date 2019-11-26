package org.gumiho.stream;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.util.Collector;

public class WindowDemo {
    public static void main(String[] args) throws Exception {
        final String host = "localhost";
        final int port = 9999;
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStream<String> text = env.socketTextStream(host, port, "\n");
        DataStream<WordWithCount> windowWordCounts = text.flatMap(new FlatMapFunction<String, WordWithCount>() {
            @Override
            public void flatMap(String value, Collector<WordWithCount> out) {
                for (String word : value.split("\\s")) {
                    out.collect(new WordWithCount(word, 1));
                }
            }
        })
        .keyBy("word")
        .timeWindow(Time.seconds(4))
        .reduce(new ReduceFunction<WordWithCount>() {
            @Override
            public WordWithCount reduce(WordWithCount wordWithCount, WordWithCount t1) throws Exception {
                return new WordWithCount(wordWithCount.word, wordWithCount.count + t1.count);
            }
        });
        windowWordCounts.print().setParallelism(1);
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
