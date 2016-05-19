import java.sql.DriverManager

object TestAnorm {
  def getConnection() = {
    DriverManager.getConnection("jdbc:oracle:thin:@//ntpdv1.octanner.com:1521/ntpdv1", "p42", "project42")
  }

  def main(args: Array[String]) {
    Class.forName("oracle.jdbc.driver.OracleDriver").newInstance()
    implicit val connection = getConnection()
    println("connection = " + connection)
  }
}
