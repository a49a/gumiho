package org.gumiho.batch.spark.lib

import org.apache.spark.{SparkConf, SparkContext}

object SparkEnv {
    private var sc: SparkContext = _
    private var master: String = _
    private var appName: String = _

    def init(appName: String = "foo", master: String = "local[*]") = {
        this.appName = appName
        this.master = master
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
            .setIfMissing("spark.master", master)
            .set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
        new SparkContext(conf)
    }
}
