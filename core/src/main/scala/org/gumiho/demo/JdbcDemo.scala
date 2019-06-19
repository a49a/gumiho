import java.sql.DriverManager

object JdbcDemo {

    def main(args: Array[String]): Unit = {
        classOf[com.mysql.jdbc.Driver]
        val url = "jdbc:mysql://localhost:3306/foo_db?user=foo&password=bar"
        val conn = DriverManager.getConnection(url)
    }
}
