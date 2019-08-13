package org.gumiho.demo.spark

import org.apache.spark.ml.clustering.KMeans
import org.apache.spark.ml.linalg.Vectors
import org.gumiho.lib.spark.SparkSqlUtils

object MlDemo {
    def main(args: Array[String]): Unit = {
        val spark = SparkSqlUtils.sessionDevFactory()
        val data = Seq(
            Vectors.sparse(4, Seq((0, 1.0), (3, -2.0))),
            Vectors.dense(4.0, 5.0, 0.0, 3.0),
            Vectors.dense(6.0, 7.0, 0.0, 8.0),
            Vectors.sparse(4, Seq((0, 9.0), (3, 1.0)))
        )
        import spark.implicits._
        val df = data.map(Tuple1.apply).toDF()

        val kmeans = new KMeans().setK(2).setSeed(1L)
        val model = kmeans.fit(df)
    }
}
