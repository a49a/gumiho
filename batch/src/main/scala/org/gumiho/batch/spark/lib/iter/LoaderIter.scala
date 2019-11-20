package org.gumiho.batch.spark.lib.iter

class LoaderIter(iter: Iterator[String]) extends Iterator[Array[String]] {
    override def hasNext = iter.hasNext
    override def next = {
        iter.next().split("\t", -1)
    }
}
