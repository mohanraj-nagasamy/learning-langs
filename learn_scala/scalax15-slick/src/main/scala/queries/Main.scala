package queries

import slick.dbio.Effect.{Write, Read}
import slick.driver.H2Driver
import slick.profile.{FixedSqlAction, FixedSqlStreamingAction}

import scala.concurrent._
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

import slick.driver.H2Driver.api._

object Main {

  // Tables -------------------------------------

  case class Album(
                    artist: String,
                    title: String,
                    year: Int,
                    rating: Rating,
                    id: Long = 0L)

  class AlbumTable(tag: Tag) extends Table[Album](tag, "albums") {
    def artist = column[String]("artist")

    def title = column[String]("title")

    def year = column[Int]("year")

    def rating = column[Rating]("rating")

    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

    def * = (artist, title, year, rating, id) <>(Album.tupled, Album.unapply)
  }

  lazy val AlbumTable = TableQuery[AlbumTable]


  // Example queries ----------------------------

  val createTableAction =
    AlbumTable.schema.create

  val insertAlbumsAction =
    AlbumTable ++= Seq(
      Album("Keyboard Cat", "Keyboard Cat's Greatest Hits", 2009, Rating.Awesome),
      Album("Spice Girls", "Spice", 1996, Rating.Good),
      Album("Rick Astley", "Whenever You Need Somebody", 1987, Rating.NotBad),
      Album("Manowar", "The Triumph of Steel", 1992, Rating.Meh),
      Album("Justin Bieber", "Believe", 2013, Rating.Aaargh))

  val selectAllQuery =
    AlbumTable

  val selectWhereQuery =
    AlbumTable
      .filter(_.rating === (Rating.Awesome: Rating))

  val selectSortedQuery1 =
    AlbumTable
      .sortBy(_.year.asc)

  val selectSortedQuery2 =
    AlbumTable
      .sortBy(a => (a.year.asc, a.rating.asc))

  val selectPagedQuery =
    AlbumTable
      .drop(2).take(1)

  val selectColumnsQuery1 =
    AlbumTable
      .map(_.title)

  val selectColumnsQuery2 =
    AlbumTable
      .map(a => (a.artist, a.title))

  val selectCombinedQuery =
    AlbumTable
      .filter(_.artist === "Keyboard Cat")
      .map(_.title)


  // Returning single/multiple results ----------

  val selectPagedAction1 =
    selectPagedQuery
      .result

  val selectPagedAction2 =
    selectPagedQuery
      .result
      .headOption

  val selectPagedAction3 =
    selectPagedQuery
      .result
      .head


  val insert = AlbumTable ++= Seq(
    Album("Moh", "tittt", 2323, Rating.Aaargh),
    Album("Moh", "tittt", 9999, Rating.Aaargh))
  val select = AlbumTable.result

  val update = AlbumTable.filter(_.artist === "Moh").map((table: AlbumTable) => (table.rating, table.title)).update((Rating.Awesome, "asdfnew"))
  val delete = AlbumTable.filter(_.title === "asdfnew").delete
  // Database -----------------------------------

  val db = Database.forConfig("scalaxdb")

  def selectArtist(artist: String): Query[AlbumTable, Album, Seq] =
    AlbumTable.filter(_.artist === artist)

  def insertArtist(album: Album): FixedSqlAction[Int, NoStream, Write] =
    AlbumTable += album

  //  def updateArtist(album: Album): FixedSqlAction[Int, NoStream, Write] =
  //    AlbumTable.map().update

  def createArtist(artist: String, title: String, year: Int) = {
//    for {
//      existing <- selectArtist(artist).result
//      t = println("existing " + existing)
//      rating = if (existing.length > 0) Rating.Meh else Rating.Awesome
//      _ <- insertArtist(Album(artist, title, year, rating))
//    } yield ()
    selectArtist(artist).result.map((albums: Seq[Album]) => {
      println("existing " + albums)
      val rating = if (albums.length > 0) Rating.Meh else Rating.Awesome
      rating
    }).flatMap((rating: Rating) => {
      insertArtist(Album(artist, title, year, rating))
    })
  }

  // Let's go! ----------------------------------

  def exec[T](action: DBIO[T]): T =
    Await.result(db.run(action), 2 seconds)

  def createTestAlbums() = {
    exec(createTableAction)
    exec(insertAlbumsAction)
  }

  def main(args: Array[String]): Unit = {
    createTestAlbums()
    exec(insert)
    exec(update)
    exec(select).foreach(println)
//    println("*" * 100)
//    exec(delete)
    exec(createArtist("Moh", "chainnin", 99999))
    exec(select).foreach(println)
    //    exec(selectCombinedQuery.result).foreach(println)
  }

}
