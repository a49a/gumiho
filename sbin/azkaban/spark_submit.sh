#!/usr/bin/env bash

jar_path="hdfs://jars/foo.jar"
main_class="org.gumiho.spark.demo"
spark-submit \
--master yarn \
--deploy-mode cluster \
--num-executors 4 \
--executor-cores 2 \
--driver-memory 6g \
--executor-memory 4g \
--conf spark.default.parallelism=40 \
--class ${main_class} \
${jar_path}
