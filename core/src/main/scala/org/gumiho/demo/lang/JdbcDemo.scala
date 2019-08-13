package org.gumiho.demo.lang

import java.sql.DriverManager

import scala.io.Source

object JdbcDemo {
    def main(args: Array[String]): Unit = {
        val host = ""
        val db = ""
        val user = ""
        val password = ""
        classOf[com.mysql.jdbc.Driver]
        val url = s"jdbc:mysql://${host}:3306/${db}?user=${user}&password=${password}"
        val conn = DriverManager.getConnection(url)
        val r = Source.fromFile("" )
        for (l <- r.getLines()) {
            val tableName = l.trim()
            try {
                val statement = conn.createStatement()
                val sql = s""
                val resultSet = statement.executeUpdate(sql)
                println(resultSet)
            } catch {
                case e:Throwable => e.printStackTrace()
            }
        }
        conn.close()
    }
}
