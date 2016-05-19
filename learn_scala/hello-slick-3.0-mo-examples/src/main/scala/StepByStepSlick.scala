import java.util

import slick.dbio.DBIO
import slick.jdbc.JdbcBackend
import slick.lifted.TableQuery
import slick.driver.H2Driver.api._

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global
/**
  * Created by mohanraj.nagasamy on 2/26/16.
  */
object StepByStepSlick {

  val db = Database.forConfig("h2mem1")

  def main(args: Array[String]) {
    println("StepByStepSlick$.main");

    // The query interface for the Suppliers table
    val suppliers: TableQuery[Suppliers] = TableQuery[Suppliers]

    // the query interface for the Coffees table
    val coffees: TableQuery[Coffees] = TableQuery[Coffees]

    val setupAction: DBIO[Unit] = DBIO.seq(
      // Create the schema by combining the DDLs for the Suppliers and Coffees
      // tables using the query interfaces
      (suppliers.schema ++ coffees.schema).create,

      // Insert some suppliers
      suppliers += (101, "Acme, Inc.", "99 Market Street", "Groundsville", "CA", "95199"),
      suppliers += ( 49, "Superior Coffee", "1 Party Place", "Mendocino", "CA", "95460"),
      suppliers += (150, "The High Ground", "100 Coffee Lane", "Meadows", "CA", "93966")
    )

    val setupFuture: Future[Unit] = db.run(setupAction)

    val f = setupFuture.flatMap { _ =>

      db.run(suppliers.result.map(allSuppliers => allSuppliers.foreach(x => println(s"Resule::: ${x}") )))

      // Insert some coffees (using JDBC's batch insert feature)
      val insertAction: DBIO[Option[Int]] = coffees ++= Seq (
        ("Colombian",         101, 7.99, 0, 0),
        ("French_Roast",       49, 8.99, 0, 0),
        ("Espresso",          150, 9.99, 0, 0),
        ("Colombian_Decaf",   101, 8.99, 0, 0),
        ("French_Roast_Decaf", 49, 9.99, 0, 0)
      )

      val insertAndPrintAction: DBIO[Unit] = insertAction.map { coffeesInsertResult =>
        // Print the number of rows inserted
        coffeesInsertResult foreach { numRows =>
          println(s"Inserted $numRows rows into the Coffees table")
        }
      }

      val allSuppliersAction: DBIO[Seq[(Int, String, String, String, String, String)]] =
        suppliers.result

      val combinedAction: DBIO[Seq[(Int, String, String, String, String, String)]] =
        insertAndPrintAction >> allSuppliersAction

      val combinedFuture: Future[Seq[(Int, String, String, String, String, String)]] =
        db.run(combinedAction)

      db.stream(coffees.map(_.name).result).foreach(println)
      db.stream(coffees.result).foreach(println)

      combinedFuture.map { allSuppliers => allSuppliers.foreach(println) }

    }
    Await.result(f, Duration.Inf)

    val salesUpdate: Query[Column[Int], Int, Seq] = coffees.filter(_.supID === 101).map(_.sales)
    db.run(salesUpdate.update(100))
    val session: JdbcBackend#SessionDef = db.createSession()
    db.run(coffees.result).foreach(println)


    println("coffees.map(_.name).result.statements.toList = " + coffees.map(_.name).result.statements.toList)
    println("coffees.filter(_.price > 9.0).result.statements.toList = " + coffees.filter(_.price > 9.0).map(_.name).result.statements.toList)
//    println("coffees.map(_.name).result = " + query)
  }

}
