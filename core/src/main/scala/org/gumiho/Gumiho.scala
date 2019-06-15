package org.gumiho

import org.slf4j.Logger
import org.slf4j.LoggerFactory

object Gumiho {
    def main(args: Array[String]) = {
        val logger: Logger = LoggerFactory.getLogger(Gumiho.getClass)
        logger.info("test")
    }
}