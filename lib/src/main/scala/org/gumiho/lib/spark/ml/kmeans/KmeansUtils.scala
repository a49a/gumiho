package org.gumiho.lib.spark.ml.kmeans

object KmeansUtils {
    //求欧式距离
    def distance(a: Array[Int], b: Array[Int]) = {
        math.sqrt(
            a.zip(b)
                .map(x => x._1 - x._2)
                .map(x => x * x)
                .sum
        )
    }
}
