package org.gumiho.demo.lang

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object TimeDemo {
    def main(args: Array[String]): Unit = {
        val str = "2019-07-20 19:30:04"
        val fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val date = LocalDateTime.parse(str, fmt)
            .plusDays(1)
            .toLocalDate
            .toString
        println(date)
    }
}
