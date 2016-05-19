package tables

import actions.Rating.Good
import tables.Rating.Awesome

import scala.concurrent._
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

import slick.driver.H2Driver.api._

object Main {

  // Tables -------------------------------------
  case class Me(name: String){
    def test = {
      name.toUpperCase()
    }
  }
  case class Album(
    artist : String,
    title  : String,
    rating: Rating,
    id     : Long = 0L)

  class AlbumTable(tag: Tag) extends Table[Album](tag, "albums") {
    def artist = column[String]("artist")
    def title  = column[String]("title")
    def rating = column[Rating]("rating")
    def id     = column[Long]("id", O.PrimaryKey, O.AutoInc)

    def * = (artist, title, rating, id) <> (Album.tupled, Album.unapply)
  }

  lazy val AlbumTable = TableQuery[AlbumTable]



  // Actions ------------------------------------

  val createTableAction =
    AlbumTable.schema.create

  val insertAlbumsAction =
    AlbumTable ++= Seq(
      Album( "Keyboard Cat"  , "Keyboard Cat's Greatest Hits", Awesome  ), // released in 2009
      Album( "Spice Girls"   , "Spice"                       , Rating.Good  ), // released in 1996
      Album( "Rick Astley"   , "Whenever You Need Somebody"  , Rating.NotBad  ), // released in 1987
      Album( "Manowar"       , "The Triumph of Steel"        , Rating.Meh  ), // released in 1992
      Album( "Justin Bieber" , "Believe"                     , Awesome  )) // released in 2013

  val selectAlbumsAction =
    AlbumTable.result



  // Database -----------------------------------

  val db = Database.forConfig("scalaxdb")



  // Let's go! ----------------------------------

  def exec[T](action: DBIO[T]): T =
    Await.result(db.run(action), 2 seconds)

  def main(args: Array[String]): Unit = {
    exec(createTableAction)
    exec(insertAlbumsAction)
    exec(selectAlbumsAction).foreach(println)
  }

}