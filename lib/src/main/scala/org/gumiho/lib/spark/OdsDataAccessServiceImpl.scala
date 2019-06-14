package org.gumiho.lib.spark

import org.apache.spark.rdd.RDD
import org.gumiho.lib.spark.iter.{KeyIter, LoaderIter}

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
