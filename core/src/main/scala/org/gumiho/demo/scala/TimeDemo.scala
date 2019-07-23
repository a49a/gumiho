package org.gumiho.demo.scala

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object TimeDemo {
    def main(args: Array[String]): Unit = {
        val str = "2019-07-20 19:30:04"
        val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
        println(LocalDateTime.now().format(formatter))
        val fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val date = LocalDateTime.parse(str, fmt).toLocalDate
        println(date)
    }
}
