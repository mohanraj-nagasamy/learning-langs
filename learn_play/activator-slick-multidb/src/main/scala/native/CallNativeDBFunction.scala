import scala.slick.driver.H2Driver.simple._
import java.sql.Date

/** This example shows how to lift a native database function
  * to Slick's query language.
  */
object CallNativeDBFunction extends App {

  class SalesPerDay(tag: Tag) extends Table[(Date, Int)](tag, "SALES_PER_DAY") {
    def day = column[Date]("DAY", O.PrimaryKey)
    def count = column[Int]("COUNT")
    def * = (day, count)
  }
  val salesPerDay = TableQuery[SalesPerDay]

  val dayOfWeek = SimpleFunction.unary[Date, Int]("day_of_week")

  val dayOfWeek2 = SimpleFunction[Int]("day_of_week")

  def dayOfWeek3(c: Column[Date]) = dayOfWeek2(Seq(c))

  val db = Database.forURL("jdbc:h2:mem:test1", driver = "org.h2.Driver")
  db withSession { implicit session =>
    salesPerDay.ddl.create
    salesPerDay ++= Seq(
      (Date.valueOf("2011-04-01"), 3),
      (Date.valueOf("2011-04-02"), 8),
      (Date.valueOf("2011-04-03"), 0),
      (Date.valueOf("2011-04-04"), 2),
      (Date.valueOf("2011-04-05"), 1),
      (Date.valueOf("2011-04-06"), 1),
      (Date.valueOf("2011-04-07"), 2),
      (Date.valueOf("2011-04-08"), 5),
      (Date.valueOf("2011-04-09"), 4),
      (Date.valueOf("2011-04-10"), 0),
      (Date.valueOf("2011-04-11"), 2)
    )

    // Use the lifted function in a query to group by day of week
    val q1 = for {
      (dow, q) <- salesPerDay
                  .map(s => (dayOfWeek(s.day), s.count))
                  .groupBy(_._1)
    } yield (dow, q.map(_._2).sum)

    println("Day of week (1 = Sunday) -> Sales:")
    q1.foreach { case (dow, sum) => println("  " + dow + "\t->\t" + sum) }
  }
}
