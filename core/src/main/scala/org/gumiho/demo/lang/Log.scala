package org.gumiho.demo.lang

import org.slf4j.{Logger, LoggerFactory}

object Log {
    def main(args: Array[String]) = {
        val logger: Logger = LoggerFactory.getLogger(Log.getClass)
        logger.info("test")
    }
}
