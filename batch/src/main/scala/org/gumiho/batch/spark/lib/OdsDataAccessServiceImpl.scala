package org.gumiho.batch.spark.lib

import org.apache.spark.rdd.RDD
import org.gumiho.batch.spark.lib.iter.{KeyIter, LoaderIter}

class OdsDataAccessServiceImpl(val path: String,
                               val len: Int,
                               val pk: Int = 0) extends DataAccessService {
    private var rdd: RDD[Array[String]] = _

    override def load() = {
        rdd = SparkEnv.loadData(path).mapPartitions(iter => {
            new LoaderIter(iter)
        })//.filter(filterLen)
    }

    def genKV() = {
        rdd.mapPartitions(iter => {
            new KeyIter(iter, pk)
        })
    }

    override def getRdd() = {
        rdd
    }
}
