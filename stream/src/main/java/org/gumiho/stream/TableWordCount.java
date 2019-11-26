package org.gumiho.stream;

import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.java.StreamTableEnvironment;
import org.apache.flink.table.descriptors.FileSystem;
import org.apache.flink.table.descriptors.OldCsv;
import org.apache.flink.table.descriptors.Schema;
import org.apache.flink.types.Row;
import org.gumiho.stream.udf.HashCode;

public class TableWordCount {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        StreamTableEnvironment tEnv = StreamTableEnvironment.create(env);

        String path = TableWordCount.class.getClassLoader().getResource("words.txt").getPath();
        tEnv.connect(new FileSystem().path(path))
                .withFormat(new OldCsv().field("word", Types.STRING).lineDelimiter("\n"))
                .withSchema(new Schema().field("word", Types.STRING))
                .inAppendMode()
                .registerTableSource("fileSource");


        Table result = tEnv.scan("fileSource")
                .groupBy("word")
                .select("word, count(1) as count");
        tEnv.registerFunction("hash", new HashCode(122));
        Table r = result.select("word, word.hash(), hash(word)");

        tEnv.toRetractStream(r, Row.class).print();

        env.execute("table word count");
    }
}
