package org.gumiho.stream;

import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;


public class ProcessWindowFunctionImpl extends ProcessWindowFunction<Tuple2<String, Long>, String, String, TimeWindow> {

    @Override
    public void process(String key, Context context, Iterable<Tuple2<String, Long>> input, Collector<String> out) {
        long count = 0;
        for (Tuple2<String, Long> in : input) {
            count++;
        }
        out.collect("Window: " + context.window() + "count: " + count);
    }
}
