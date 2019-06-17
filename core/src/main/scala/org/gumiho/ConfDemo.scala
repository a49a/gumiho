package org.gumiho

import scala.collection.JavaConverters._

import org.gumiho.lib.ConfUtils

object ConfDemo {
    def main(args: Array[String]): Unit = {
        val p = ConfUtils.readProperties("foo.properties")
        val iters = p.entrySet().iterator().asScala
        for (i <- iters) {
            println(i.getKey, i.getValue)
        }
    }
}
