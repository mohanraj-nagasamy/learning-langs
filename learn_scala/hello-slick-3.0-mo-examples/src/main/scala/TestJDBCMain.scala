import java.sql.{Connection, DriverManager}
import java.util.Properties

import slick.dbio.DBIO
import slick.jdbc.GetResult
import slick.lifted
import tables.Tables
import scala.collection.mutable
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}
import scala.slick.driver.PostgresDriver.api._

/**
  * Created by mohanraj.nagasamy on 2/26/16.
  */
object TestJDBCMain {
//  val url = "jdbc:postgresql://localhost:5432/pagila";
  val url = "jdbc:postgresql://localhost:5432/perf-mobile-notifications";

  def main(args: Array[String]) {

    val props = Map[String, String]("user" -> "mohanraj.nagasamy", "password" -> "")
    //    props("user") = "mohanraj.nagasamy"
    //    props("password") = ""
    //    props.setProperty("ssl", "true");
    //    implicit val conn = DriverManager.getConnection(url, props);

    val db = Database.forURL(url, props)

    println("db = " + db)
    //    println("conn = " + conn)
    //    val url = "jdbc:postgresql://localhost/test?user=fred&password=secret&ssl=true";
    //    val conn = DriverManager.getConnection(url);
        codeGen()


    //    val customer: lifted.TableQuery[Tables.Customer] = tables.Tables.Customer
    //    val f: Future[Int] = db.run(customer.length.result)
    //    println("customer.length = " + f)
    //    Await.result(f, Duration.Inf)

    //    val customerRow: GetResult[Tables.CustomerRow] = tables.Tables.GetResultCustomerRow

    //    println("customerRow = " + customerRow)
  }

  def codeGen(): Unit = {
    slick.codegen.SourceCodeGenerator.main(
      Array("slick.driver.PostgresDriver", "org.postgresql.Driver", url,
        "/Users/mohanraj.nagasamy/src/projects/learn_scala/hello-slick-3.0-mo-examples/target/models",
        "notifications", "mohanraj.nagasamy", ""
      )
    )
  }
}
