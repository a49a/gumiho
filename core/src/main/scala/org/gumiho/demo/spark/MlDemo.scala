package org.gumiho.demo.spark

import org.apache.spark.ml.clustering.KMeans
import org.apache.spark.ml.feature.{Bucketizer, MaxAbsScaler, MinMaxScaler, StandardScaler}
import org.apache.spark.ml.linalg.Vectors
import org.gumiho.lib.spark.SparkSqlUtils

object MlDemo {

    def main(args: Array[String]): Unit = {
        kmeans()
    }

    def kmeans(): Unit = {
        val spark = SparkSqlUtils.sessionDevFactory()
        val data = Seq(
            Vectors.sparse(4, Seq((0, 1.0), (3, -2.0))),
            Vectors.dense(4.0, 5.0, 0.0, 3.0),
            Vectors.dense(6.0, 7.0, 0.0, 8.0),
            Vectors.sparse(4, Seq((0, 9.0), (3, 1.0)))
        )
        val array = Array(
            (1,(13.0, 1)),
            (2,(16.0, 2)),
            (3,(23.0, 3)),
            (4,(35.0, 4)),
            (5,(56.0, 5)),
            (6,(44.0, 6))
        )

        import spark.implicits._
        val df2 = data.map(Tuple1.apply).toDF("features")
        val df = spark.createDataFrame(array).toDF("id", "features")

        //标准化
        val scalermodel = new StandardScaler()
            .setInputCol("features")
            .setOutputCol("scaledFeatures")
            .setWithMean(true)
            .setWithStd(true)
            .fit(df2)
        scalermodel.transform(df2).show
        val maxabs = new MaxAbsScaler()
            .setInputCol("features")
            .setOutputCol("minmax_features").fit(df2).transform(df2).show()

        //        val kmeans = new KMeans().setK(2).setSeed(1L)
//        val model = kmeans.fit(df)
    }
}
