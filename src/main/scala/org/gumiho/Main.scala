import org.slf4j.Logger
import org.slf4j.LoggerFactory

object Main {
    def main(args: Array[String]) = {
        val logger: Logger = LoggerFactory.getLogger(Main.getClass)
        logger.info("test")
    }
}