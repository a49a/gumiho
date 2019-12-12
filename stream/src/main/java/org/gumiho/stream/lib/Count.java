package org.gumiho.stream.lib;

import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.api.common.state.StateTtlConfig;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.api.common.time.Time;
import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.configuration.Configuration;

public class Count extends RichMapFunction<Integer, Integer> {

    private transient ValueState<Tuple2<Integer, Integer>> sum;

    @Override
    public Integer map(Integer input) throws Exception {
        Tuple2<Integer, Integer> currentSum = sum.value();
        currentSum.f0 += input;
        currentSum.f1 += 1;
        sum.update(currentSum);
        return input;
    }

    @Override
    public void open(Configuration conf) throws Exception {
        ValueStateDescriptor<Tuple2<Integer, Integer>> descriptor =
                new ValueStateDescriptor<Tuple2<Integer, Integer>>(
                        "sum",
                        TypeInformation.of(new TypeHint<Tuple2<Integer, Integer>>() {})
                );
        StateTtlConfig ttlConfig = StateTtlConfig
                .newBuilder(Time.seconds(1))
                .setUpdateType(StateTtlConfig.UpdateType.OnCreateAndWrite)
                .setStateVisibility(StateTtlConfig.StateVisibility.NeverReturnExpired)
                .build();

        sum = getRuntimeContext().getState(descriptor);
    }
}
