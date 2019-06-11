package org.gumiho.util

import org.slf4j.Logger
import org.slf4j.LoggerFactory

case class Schema(
                 col1: Int,
                 col2: Int,
                 col3: Int,
                 col4: Int
                 )

object Gumiho {
    def main(args: Array[String]) = {
        val logger: Logger = LoggerFactory.getLogger(Gumiho.getClass)
        logger.info("test")
    }
}