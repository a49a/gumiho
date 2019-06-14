package org.gumiho.lib.spark

import org.apache.spark.{SparkConf, SparkContext}

object SparkEnv {
    private var sc: SparkContext = _
    private var appName: String = _

    def init(appName: String = "foo") = {
        this.appName = appName
        this.sc = contextFactory()
    }

    def getContext() = {
        sc
    }

    def loadData(path: String) = {
        sc.textFile(path)
    }

    private def contextFactory() = {
        val conf = new SparkConf()
            .setAppName(appName)
            .setIfMissing("spark.master","local[*]")
            .set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
        new SparkContext(conf)
    }
}
