package tables

// AUTO-GENERATED Slick data model
/** Stand-alone Slick data model for immediate use */
object Tables extends {
  val profile = slick.driver.PostgresDriver
} with Tables

/** Slick data model trait for extension, choice of backend or usage in the cake pattern. (Make sure to initialize this late.) */
trait Tables {
  val profile: slick.driver.JdbcProfile

  import profile.api._
  import slick.model.ForeignKeyAction

  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  import slick.jdbc.{GetResult => GR}

  /** DDL for all tables. Call .create to execute. */
  lazy val schema = Array(Actor.schema,
    Address.schema,
    Category.schema,
    City.schema,
    Country.schema,
    Customer.schema,
    Film.schema, FilmActor.schema,
    FilmCategory.schema,
    Inventory.schema, Language.schema,
    Payment.schema, PaymentP200701.schema,
    PaymentP200702.schema, PaymentP200703.schema,
    PaymentP200704.schema, PaymentP200705.schema,
    PaymentP200706.schema, Rental.schema, Staff.schema, Store.schema).reduceLeft(_ ++ _)

  @deprecated("Use .schema instead of .ddl", "3.0")
  def ddl = schema

  /** Entity class storing rows of table Actor
    *
    * @param actorId    Database column actor_id SqlType(serial), AutoInc, PrimaryKey
    * @param firstName  Database column first_name SqlType(varchar), Length(45,true)
    * @param lastName   Database column last_name SqlType(varchar), Length(45,true)
    * @param lastUpdate Database column last_update SqlType(timestamp) */
  case class ActorRow(actorId: Int, firstName: String, lastName: String, lastUpdate: java.sql.Timestamp)

  /** GetResult implicit for fetching ActorRow objects using plain SQL queries */
  implicit def GetResultActorRow(implicit e0: GR[Int], e1: GR[String], e2: GR[java.sql.Timestamp]): GR[ActorRow] = GR {
    prs => import prs._
      ActorRow.tupled((<<[Int], <<[String], <<[String], <<[java.sql.Timestamp]))
  }

  /** Table description of table actor. Objects of this class serve as prototypes for rows in queries. */
  class Actor(_tableTag: Tag) extends Table[ActorRow](_tableTag, "actor") {
    def * = (actorId, firstName, lastName, lastUpdate) <>(ActorRow.tupled, ActorRow.unapply)

    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(actorId), Rep.Some(firstName), Rep.Some(lastName), Rep.Some(lastUpdate)).shaped.<>({ r => import r._; _1.map(_ => ActorRow.tupled((_1.get, _2.get, _3.get, _4.get))) }, (_: Any) => throw new Exception("Inserting into ? projection not supported."))

    /** Database column actor_id SqlType(serial), AutoInc, PrimaryKey */
    val actorId: Rep[Int] = column[Int]("actor_id", O.AutoInc, O.PrimaryKey)
    /** Database column first_name SqlType(varchar), Length(45,true) */
    val firstName: Rep[String] = column[String]("first_name", O.Length(45, varying = true))
    /** Database column last_name SqlType(varchar), Length(45,true) */
    val lastName: Rep[String] = column[String]("last_name", O.Length(45, varying = true))
    /** Database column last_update SqlType(timestamp) */
    val lastUpdate: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("last_update")

    /** Index over (lastName) (database name idx_actor_last_name) */
    val index1 = index("idx_actor_last_name", lastName)
  }

  /** Collection-like TableQuery object for table Actor */
  lazy val Actor = new TableQuery(tag => new Actor(tag))

  /** Entity class storing rows of table Address
    *
    * @param addressId  Database column address_id SqlType(serial), AutoInc, PrimaryKey
    * @param address    Database column address SqlType(varchar), Length(50,true)
    * @param address2   Database column address2 SqlType(varchar), Length(50,true), Default(None)
    * @param district   Database column district SqlType(varchar), Length(20,true)
    * @param cityId     Database column city_id SqlType(int2)
    * @param postalCode Database column postal_code SqlType(varchar), Length(10,true), Default(None)
    * @param phone      Database column phone SqlType(varchar), Length(20,true)
    * @param lastUpdate Database column last_update SqlType(timestamp) */
  case class AddressRow(addressId: Int, address: String, address2: Option[String] = None, district: String, cityId: Int, postalCode: Option[String] = None, phone: String, lastUpdate: java.sql.Timestamp)

  /** GetResult implicit for fetching AddressRow objects using plain SQL queries */
  implicit def GetResultAddressRow(e0: GR[Int], e1: GR[String], e2: GR[Option[String]], e3: GR[Int], e4: GR[java.sql.Timestamp]): GR[AddressRow] = GR {
    prs => import prs._
      AddressRow.tupled((<<[Int], <<[String], <<?[String], <<[String], <<[Int], <<?[String], <<[String], <<[java.sql.Timestamp]))
  }

  /** Table description of table address. Objects of this class serve as prototypes for rows in queries. */
  class Address(_tableTag: Tag) extends Table[AddressRow](_tableTag, "address") {
    def * = (addressId, address, address2, district, cityId, postalCode, phone, lastUpdate) <>(AddressRow.tupled, AddressRow.unapply)

    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(addressId), Rep.Some(address), address2, Rep.Some(district), Rep.Some(cityId), postalCode, Rep.Some(phone), Rep.Some(lastUpdate)).shaped.<>({ r => import r._; _1.map(_ => AddressRow.tupled((_1.get, _2.get, _3, _4.get, _5.get, _6, _7.get, _8.get))) }, (_: Any) => throw new Exception("Inserting into ? projection not supported."))

    /** Database column address_id SqlType(serial), AutoInc, PrimaryKey */
    val addressId: Rep[Int] = column[Int]("address_id", O.AutoInc, O.PrimaryKey)
    /** Database column address SqlType(varchar), Length(50,true) */
    val address: Rep[String] = column[String]("address", O.Length(50, varying = true))
    /** Database column address2 SqlType(varchar), Length(50,true), Default(None) */
    val address2: Rep[Option[String]] = column[Option[String]]("address2", O.Length(50, varying = true), O.Default(None))
    /** Database column district SqlType(varchar), Length(20,true) */
    val district: Rep[String] = column[String]("district", O.Length(20, varying = true))
    /** Database column city_id SqlType(int2) */
    val cityId: Rep[Int] = column[Int]("city_id")
    /** Database column postal_code SqlType(varchar), Length(10,true), Default(None) */
    val postalCode: Rep[Option[String]] = column[Option[String]]("postal_code", O.Length(10, varying = true), O.Default(None))
    /** Database column phone SqlType(varchar), Length(20,true) */
    val phone: Rep[String] = column[String]("phone", O.Length(20, varying = true))
    /** Database column last_update SqlType(timestamp) */
    val lastUpdate: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("last_update")

    /** Foreign key referencing City (database name address_city_id_fkey) */
    lazy val cityFk = foreignKey("address_city_id_fkey", cityId, City)(r => r.cityId, onUpdate = ForeignKeyAction.Cascade, onDelete = ForeignKeyAction.Restrict)
  }

  /** Collection-like TableQuery object for table Address */
  lazy val Address = new TableQuery(tag => new Address(tag))

  /** Entity class storing rows of table Category
    *
    * @param categoryId Database column category_id SqlType(serial), AutoInc, PrimaryKey
    * @param name       Database column name SqlType(varchar), Length(25,true)
    * @param lastUpdate Database column last_update SqlType(timestamp) */
  case class CategoryRow(categoryId: Int, name: String, lastUpdate: java.sql.Timestamp)

  /** GetResult implicit for fetching CategoryRow objects using plain SQL queries */
  implicit def GetResultCategoryRow(implicit e0: GR[Int], e1: GR[String], e2: GR[java.sql.Timestamp]): GR[CategoryRow] = GR {
    prs => import prs._
      CategoryRow.tupled((<<[Int], <<[String], <<[java.sql.Timestamp]))
  }

  /** Table description of table category. Objects of this class serve as prototypes for rows in queries. */
  class Category(_tableTag: Tag) extends Table[CategoryRow](_tableTag, "category") {
    def * = (categoryId, name, lastUpdate) <>(CategoryRow.tupled, CategoryRow.unapply)

    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(categoryId), Rep.Some(name), Rep.Some(lastUpdate)).shaped.<>({ r => import r._; _1.map(_ => CategoryRow.tupled((_1.get, _2.get, _3.get))) }, (_: Any) => throw new Exception("Inserting into ? projection not supported."))

    /** Database column category_id SqlType(serial), AutoInc, PrimaryKey */
    val categoryId: Rep[Int] = column[Int]("category_id", O.AutoInc, O.PrimaryKey)
    /** Database column name SqlType(varchar), Length(25,true) */
    val name: Rep[String] = column[String]("name", O.Length(25, varying = true))
    /** Database column last_update SqlType(timestamp) */
    val lastUpdate: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("last_update")
  }

  /** Collection-like TableQuery object for table Category */
  lazy val Category = new TableQuery(tag => new Category(tag))

  /** Entity class storing rows of table City
    *
    * @param cityId     Database column city_id SqlType(serial), AutoInc, PrimaryKey
    * @param city       Database column city SqlType(varchar), Length(50,true)
    * @param countryId  Database column country_id SqlType(int2)
    * @param lastUpdate Database column last_update SqlType(timestamp) */
  case class CityRow(cityId: Int, city: String, countryId: Int, lastUpdate: java.sql.Timestamp)

  /** GetResult implicit for fetching CityRow objects using plain SQL queries */
  implicit def GetResultCityRow(e0: GR[Int], e1: GR[String], e2: GR[Int], e3: GR[java.sql.Timestamp]): GR[CityRow] = GR {
    prs => import prs._
      CityRow.tupled((<<[Int], <<[String], <<[Int], <<[java.sql.Timestamp]))
  }

  /** Table description of table city. Objects of this class serve as prototypes for rows in queries. */
  class City(_tableTag: Tag) extends Table[CityRow](_tableTag, "city") {
    def * = (cityId, city, countryId, lastUpdate) <>(CityRow.tupled, CityRow.unapply)

    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(cityId), Rep.Some(city), Rep.Some(countryId), Rep.Some(lastUpdate)).shaped.<>({ r => import r._; _1.map(_ => CityRow.tupled((_1.get, _2.get, _3.get, _4.get))) }, (_: Any) => throw new Exception("Inserting into ? projection not supported."))

    /** Database column city_id SqlType(serial), AutoInc, PrimaryKey */
    val cityId: Rep[Int] = column[Int]("city_id", O.AutoInc, O.PrimaryKey)
    /** Database column city SqlType(varchar), Length(50,true) */
    val city: Rep[String] = column[String]("city", O.Length(50, varying = true))
    /** Database column country_id SqlType(int2) */
    val countryId: Rep[Int] = column[Int]("country_id")
    /** Database column last_update SqlType(timestamp) */
    val lastUpdate: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("last_update")

    /** Foreign key referencing Country (database name city_country_id_fkey) */
    lazy val countryFk = foreignKey("city_country_id_fkey", countryId, Country)(r => r.countryId, onUpdate = ForeignKeyAction.Cascade, onDelete = ForeignKeyAction.Restrict)
  }

  /** Collection-like TableQuery object for table City */
  lazy val City = new TableQuery(tag => new City(tag))

  /** Entity class storing rows of table Country
    *
    * @param countryId  Database column country_id SqlType(serial), AutoInc, PrimaryKey
    * @param country    Database column country SqlType(varchar), Length(50,true)
    * @param lastUpdate Database column last_update SqlType(timestamp) */
  case class CountryRow(countryId: Int, country: String, lastUpdate: java.sql.Timestamp)

  /** GetResult implicit for fetching CountryRow objects using plain SQL queries */
  implicit def GetResultCountryRow(implicit e0: GR[Int], e1: GR[String], e2: GR[java.sql.Timestamp]): GR[CountryRow] = GR {
    prs => import prs._
      CountryRow.tupled((<<[Int], <<[String], <<[java.sql.Timestamp]))
  }

  /** Table description of table country. Objects of this class serve as prototypes for rows in queries. */
  class Country(_tableTag: Tag) extends Table[CountryRow](_tableTag, "country") {
    def * = (countryId, country, lastUpdate) <>(CountryRow.tupled, CountryRow.unapply)

    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(countryId), Rep.Some(country), Rep.Some(lastUpdate)).shaped.<>({ r => import r._; _1.map(_ => CountryRow.tupled((_1.get, _2.get, _3.get))) }, (_: Any) => throw new Exception("Inserting into ? projection not supported."))

    /** Database column country_id SqlType(serial), AutoInc, PrimaryKey */
    val countryId: Rep[Int] = column[Int]("country_id", O.AutoInc, O.PrimaryKey)
    /** Database column country SqlType(varchar), Length(50,true) */
    val country: Rep[String] = column[String]("country", O.Length(50, varying = true))
    /** Database column last_update SqlType(timestamp) */
    val lastUpdate: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("last_update")
  }

  /** Collection-like TableQuery object for table Country */
  lazy val Country = new TableQuery(tag => new Country(tag))

  /** Entity class storing rows of table Customer
    *
    * @param customerId Database column customer_id SqlType(serial), AutoInc, PrimaryKey
    * @param storeId    Database column store_id SqlType(int2)
    * @param firstName  Database column first_name SqlType(varchar), Length(45,true)
    * @param lastName   Database column last_name SqlType(varchar), Length(45,true)
    * @param email      Database column email SqlType(varchar), Length(50,true), Default(None)
    * @param addressId  Database column address_id SqlType(int2)
    * @param activebool Database column activebool SqlType(bool), Default(true)
    * @param createDate Database column create_date SqlType(date)
    * @param lastUpdate Database column last_update SqlType(timestamp)
    * @param active     Database column active SqlType(int4), Default(None) */
  case class CustomerRow(customerId: Int, storeId: Int, firstName: String, lastName: String, email: Option[String] = None, addressId: Int, activebool: Boolean = true, createDate: java.sql.Date, lastUpdate: Option[java.sql.Timestamp], active: Option[Int] = None)

  /** GetResult implicit for fetching CustomerRow objects using plain SQL queries */
  implicit def GetResultCustomerRow(e0: GR[Int], e1: GR[Int], e2: GR[String], e3: GR[Option[String]], e4: GR[Boolean], e5: GR[java.sql.Date], e6: GR[Option[java.sql.Timestamp]], e7: GR[Option[Int]]): GR[CustomerRow] = GR {
    prs => import prs._
      CustomerRow.tupled((<<[Int], <<[Int], <<[String], <<[String], <<?[String], <<[Int], <<[Boolean], <<[java.sql.Date], <<?[java.sql.Timestamp], <<?[Int]))
  }

  /** Table description of table customer. Objects of this class serve as prototypes for rows in queries. */
  class Customer(_tableTag: Tag) extends Table[CustomerRow](_tableTag, "customer") {
    def * = (customerId, storeId, firstName, lastName, email, addressId, activebool, createDate, lastUpdate, active) <>(CustomerRow.tupled, CustomerRow.unapply)

    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(customerId), Rep.Some(storeId), Rep.Some(firstName), Rep.Some(lastName), email, Rep.Some(addressId), Rep.Some(activebool), Rep.Some(createDate), lastUpdate, active).shaped.<>({ r => import r._; _1.map(_ => CustomerRow.tupled((_1.get, _2.get, _3.get, _4.get, _5, _6.get, _7.get, _8.get, _9, _10))) }, (_: Any) => throw new Exception("Inserting into ? projection not supported."))

    /** Database column customer_id SqlType(serial), AutoInc, PrimaryKey */
    val customerId: Rep[Int] = column[Int]("customer_id", O.AutoInc, O.PrimaryKey)
    /** Database column store_id SqlType(int2) */
    val storeId: Rep[Int] = column[Int]("store_id")
    /** Database column first_name SqlType(varchar), Length(45,true) */
    val firstName: Rep[String] = column[String]("first_name", O.Length(45, varying = true))
    /** Database column last_name SqlType(varchar), Length(45,true) */
    val lastName: Rep[String] = column[String]("last_name", O.Length(45, varying = true))
    /** Database column email SqlType(varchar), Length(50,true), Default(None) */
    val email: Rep[Option[String]] = column[Option[String]]("email", O.Length(50, varying = true), O.Default(None))
    /** Database column address_id SqlType(int2) */
    val addressId: Rep[Int] = column[Int]("address_id")
    /** Database column activebool SqlType(bool), Default(true) */
    val activebool: Rep[Boolean] = column[Boolean]("activebool", O.Default(true))
    /** Database column create_date SqlType(date) */
    val createDate: Rep[java.sql.Date] = column[java.sql.Date]("create_date")
    /** Database column last_update SqlType(timestamp) */
    val lastUpdate: Rep[Option[java.sql.Timestamp]] = column[Option[java.sql.Timestamp]]("last_update")
    /** Database column active SqlType(int4), Default(None) */
    val active: Rep[Option[Int]] = column[Option[Int]]("active", O.Default(None))

    /** Foreign key referencing Address (database name customer_address_id_fkey) */
    lazy val addressFk = foreignKey("customer_address_id_fkey", addressId, Address)(r => r.addressId, onUpdate = ForeignKeyAction.Cascade, onDelete = ForeignKeyAction.Restrict)
    /** Foreign key referencing Store (database name customer_store_id_fkey) */
    lazy val storeFk = foreignKey("customer_store_id_fkey", storeId, Store)(r => r.storeId, onUpdate = ForeignKeyAction.Cascade, onDelete = ForeignKeyAction.Restrict)

    /** Index over (lastName) (database name idx_last_name) */
    val index1 = index("idx_last_name", lastName)
  }

  /** Collection-like TableQuery object for table Customer */
  lazy val Customer = new TableQuery(tag => new Customer(tag))

  /** Entity class storing rows of table Film
    *
    * @param filmId             Database column film_id SqlType(serial), AutoInc, PrimaryKey
    * @param title              Database column title SqlType(varchar), Length(255,true)
    * @param description        Database column description SqlType(text), Default(None)
    * @param releaseYear        Database column release_year SqlType(year), Default(None)
    * @param languageId         Database column language_id SqlType(int2)
    * @param originalLanguageId Database column original_language_id SqlType(int2), Default(None)
    * @param rentalDuration     Database column rental_duration SqlType(int2), Default(3)
    * @param rentalRate         Database column rental_rate SqlType(numeric), Default(4.99)
    * @param length             Database column length SqlType(int2), Default(None)
    * @param replacementCost    Database column replacement_cost SqlType(numeric), Default(19.99)
    * @param rating             Database column rating SqlType(mpaa_rating), Default(None)
    * @param lastUpdate         Database column last_update SqlType(timestamp)
    * @param specialFeatures    Database column special_features SqlType(_text), Length(2147483647,false), Default(None)
    * @param fulltext           Database column fulltext SqlType(tsvector), Length(2147483647,false) */
  case class FilmRow(filmId: Int,
                     title: String,
                     description: Option[String] = None,
                     releaseYear: Option[String] = None,
                     languageId: Int,
                     originalLanguageId: Option[Int] = None,
                     rentalDuration: Int = 3,
                     rentalRate: BigDecimal = BigDecimal("4.99"), length: Option[Int] = None,
                     replacementCost: BigDecimal = BigDecimal("19.99"),
                     rating: Option[String] = None, lastUpdate: java.sql.Timestamp, specialFeatures: Option[String] = None, fulltext: String)

  /** GetResult implicit for fetching FilmRow objects using plain SQL queries */
  implicit def GetResultFilmRow(e0: GR[Int], e1: GR[String], e2: GR[Option[String]], e3: GR[Option[String]], e4: GR[Int], e5: GR[Option[Int]], e6: GR[BigDecimal], e7: GR[java.sql.Timestamp]): GR[FilmRow] = GR {
    prs => import prs._
      FilmRow.tupled((<<[Int], <<[String], <<?[String], <<?[String], <<[Int], <<?[Int], <<[Int], <<[BigDecimal], <<?[Int], <<[BigDecimal], <<?[String], <<[java.sql.Timestamp], <<?[String], <<[String]))
  }

  /** Table description of table film. Objects of this class serve as prototypes for rows in queries. */
  class Film(_tableTag: Tag) extends Table[FilmRow](_tableTag, "film") {
    def * = (filmId, title, description, releaseYear, languageId, originalLanguageId, rentalDuration, rentalRate, length, replacementCost, rating, lastUpdate, specialFeatures, fulltext) <>(FilmRow.tupled, FilmRow.unapply)

    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(filmId), Rep.Some(title), description, releaseYear, Rep.Some(languageId), originalLanguageId, Rep.Some(rentalDuration), Rep.Some(rentalRate), length, Rep.Some(replacementCost), rating, Rep.Some(lastUpdate), specialFeatures, Rep.Some(fulltext)).shaped.<>({ r => import r._; _1.map(_ => FilmRow.tupled((_1.get, _2.get, _3, _4, _5.get, _6, _7.get, _8.get, _9, _10.get, _11, _12.get, _13, _14.get))) }, (_: Any) => throw new Exception("Inserting into ? projection not supported."))

    /** Database column film_id SqlType(serial), AutoInc, PrimaryKey */
    val filmId: Rep[Int] = column[Int]("film_id", O.AutoInc, O.PrimaryKey)
    /** Database column title SqlType(varchar), Length(255,true) */
    val title: Rep[String] = column[String]("title", O.Length(255, varying = true))
    /** Database column description SqlType(text), Default(None) */
    val description: Rep[Option[String]] = column[Option[String]]("description", O.Default(None))
    /** Database column release_year SqlType(year), Default(None) */
    val releaseYear: Rep[Option[String]] = column[Option[String]]("release_year", O.Default(None))
    /** Database column language_id SqlType(int2) */
    val languageId: Rep[Int] = column[Int]("language_id")
    /** Database column original_language_id SqlType(int2), Default(None) */
    val originalLanguageId: Rep[Option[Int]] = column[Option[Int]]("original_language_id", O.Default(None))
    /** Database column rental_duration SqlType(int2), Default(3) */
    val rentalDuration: Rep[Int] = column[Int]("rental_duration", O.Default(3))
    /** Database column rental_rate SqlType(numeric), Default(4.99) */
    val rentalRate: Rep[BigDecimal] = column[BigDecimal]("rental_rate", O.Default(BigDecimal("4.99")))
    /** Database column length SqlType(int2), Default(None) */
    val length: Rep[Option[Int]] = column[Option[Int]]("length", O.Default(None))
    /** Database column replacement_cost SqlType(numeric), Default(19.99) */
    val replacementCost: Rep[BigDecimal] = column[BigDecimal]("replacement_cost", O.Default(BigDecimal("19.99")))
    /** Database column rating SqlType(mpaa_rating), Default(None) */
    val rating: Rep[Option[String]] = column[Option[String]]("rating", O.Default(None))
    /** Database column last_update SqlType(timestamp) */
    val lastUpdate: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("last_update")
    /** Database column special_features SqlType(_text), Length(2147483647,false), Default(None) */
    val specialFeatures: Rep[Option[String]] = column[Option[String]]("special_features", O.Length(2147483647, varying = false), O.Default(None))
    /** Database column fulltext SqlType(tsvector), Length(2147483647,false) */
    val fulltext: Rep[String] = column[String]("fulltext", O.Length(2147483647, varying = false))

    /** Foreign key referencing Language (database name film_language_id_fkey) */
    lazy val languageFk1 = foreignKey("film_language_id_fkey", languageId, Language)(r => r.languageId, onUpdate = ForeignKeyAction.Cascade, onDelete = ForeignKeyAction.Restrict)
    /** Foreign key referencing Language (database name film_original_language_id_fkey) */
    lazy val languageFk2 = foreignKey("film_original_language_id_fkey", originalLanguageId, Language)(r => Rep.Some(r.languageId), onUpdate = ForeignKeyAction.Cascade, onDelete = ForeignKeyAction.Restrict)

    /** Index over (fulltext) (database name film_fulltext_idx) */
    val index1 = index("film_fulltext_idx", fulltext)
    /** Index over (title) (database name idx_title) */
    val index2 = index("idx_title", title)
  }

  /** Collection-like TableQuery object for table Film */
  lazy val Film = new TableQuery(tag => new Film(tag))

  /** Entity class storing rows of table FilmActor
    *
    * @param actorId    Database column actor_id SqlType(int2)
    * @param filmId     Database column film_id SqlType(int2)
    * @param lastUpdate Database column last_update SqlType(timestamp) */
  case class FilmActorRow(actorId: Int, filmId: Int, lastUpdate: java.sql.Timestamp)

  /** GetResult implicit for fetching FilmActorRow objects using plain SQL queries */
  implicit def GetResultFilmActorRow(e0: GR[Int], e1: GR[java.sql.Timestamp]): GR[FilmActorRow] = GR {
    prs => import prs._
      FilmActorRow.tupled((<<[Int], <<[Int], <<[java.sql.Timestamp]))
  }

  /** Table description of table film_actor. Objects of this class serve as prototypes for rows in queries. */
  class FilmActor(_tableTag: Tag) extends Table[FilmActorRow](_tableTag, "film_actor") {
    def * = (actorId, filmId, lastUpdate) <>(FilmActorRow.tupled, FilmActorRow.unapply)

    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(actorId), Rep.Some(filmId), Rep.Some(lastUpdate)).shaped.<>({ r => import r._; _1.map(_ => FilmActorRow.tupled((_1.get, _2.get, _3.get))) }, (_: Any) => throw new Exception("Inserting into ? projection not supported."))

    /** Database column actor_id SqlType(int2) */
    val actorId: Rep[Int] = column[Int]("actor_id")
    /** Database column film_id SqlType(int2) */
    val filmId: Rep[Int] = column[Int]("film_id")
    /** Database column last_update SqlType(timestamp) */
    val lastUpdate: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("last_update")

    /** Primary key of FilmActor (database name film_actor_pkey) */
    val pk = primaryKey("film_actor_pkey", (actorId, filmId))

    /** Foreign key referencing Actor (database name film_actor_actor_id_fkey) */
    lazy val actorFk = foreignKey("film_actor_actor_id_fkey", actorId, Actor)(r => r.actorId, onUpdate = ForeignKeyAction.Cascade, onDelete = ForeignKeyAction.Restrict)
    /** Foreign key referencing Film (database name film_actor_film_id_fkey) */
    lazy val filmFk = foreignKey("film_actor_film_id_fkey", filmId, Film)(r => r.filmId, onUpdate = ForeignKeyAction.Cascade, onDelete = ForeignKeyAction.Restrict)
  }

  /** Collection-like TableQuery object for table FilmActor */
  lazy val FilmActor = new TableQuery(tag => new FilmActor(tag))

  /** Entity class storing rows of table FilmCategory
    *
    * @param filmId     Database column film_id SqlType(int2)
    * @param categoryId Database column category_id SqlType(int2)
    * @param lastUpdate Database column last_update SqlType(timestamp) */
  case class FilmCategoryRow(filmId: Int, categoryId: Int, lastUpdate: java.sql.Timestamp)

  /** GetResult implicit for fetching FilmCategoryRow objects using plain SQL queries */
  implicit def GetResultFilmCategoryRow(e0: GR[Int], e1: GR[java.sql.Timestamp]): GR[FilmCategoryRow] = GR {
    prs => import prs._
      FilmCategoryRow.tupled((<<[Int], <<[Int], <<[java.sql.Timestamp]))
  }

  /** Table description of table film_category. Objects of this class serve as prototypes for rows in queries. */
  class FilmCategory(_tableTag: Tag) extends Table[FilmCategoryRow](_tableTag, "film_category") {
    def * = (filmId, categoryId, lastUpdate) <>(FilmCategoryRow.tupled, FilmCategoryRow.unapply)

    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(filmId), Rep.Some(categoryId), Rep.Some(lastUpdate)).shaped.<>({ r => import r._; _1.map(_ => FilmCategoryRow.tupled((_1.get, _2.get, _3.get))) }, (_: Any) => throw new Exception("Inserting into ? projection not supported."))

    /** Database column film_id SqlType(int2) */
    val filmId: Rep[Int] = column[Int]("film_id")
    /** Database column category_id SqlType(int2) */
    val categoryId: Rep[Int] = column[Int]("category_id")
    /** Database column last_update SqlType(timestamp) */
    val lastUpdate: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("last_update")

    /** Primary key of FilmCategory (database name film_category_pkey) */
    val pk = primaryKey("film_category_pkey", (filmId, categoryId))

    /** Foreign key referencing Category (database name film_category_category_id_fkey) */
    lazy val categoryFk = foreignKey("film_category_category_id_fkey", categoryId, Category)(r => r.categoryId, onUpdate = ForeignKeyAction.Cascade, onDelete = ForeignKeyAction.Restrict)
    /** Foreign key referencing Film (database name film_category_film_id_fkey) */
    lazy val filmFk = foreignKey("film_category_film_id_fkey", filmId, Film)(r => r.filmId, onUpdate = ForeignKeyAction.Cascade, onDelete = ForeignKeyAction.Restrict)
  }

  /** Collection-like TableQuery object for table FilmCategory */
  lazy val FilmCategory = new TableQuery(tag => new FilmCategory(tag))

  /** Entity class storing rows of table Inventory
    *
    * @param inventoryId Database column inventory_id SqlType(serial), AutoInc, PrimaryKey
    * @param filmId      Database column film_id SqlType(int2)
    * @param storeId     Database column store_id SqlType(int2)
    * @param lastUpdate  Database column last_update SqlType(timestamp) */
  case class InventoryRow(inventoryId: Int, filmId: Int, storeId: Int, lastUpdate: java.sql.Timestamp)

  /** GetResult implicit for fetching InventoryRow objects using plain SQL queries */
  implicit def GetResultInventoryRow(e0: GR[Int], e1: GR[Int], e2: GR[java.sql.Timestamp]): GR[InventoryRow] = GR {
    prs => import prs._
      InventoryRow.tupled((<<[Int], <<[Int], <<[Int], <<[java.sql.Timestamp]))
  }

  /** Table description of table inventory. Objects of this class serve as prototypes for rows in queries. */
  class Inventory(_tableTag: Tag) extends Table[InventoryRow](_tableTag, "inventory") {
    def * = (inventoryId, filmId, storeId, lastUpdate) <>(InventoryRow.tupled, InventoryRow.unapply)

    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(inventoryId), Rep.Some(filmId), Rep.Some(storeId), Rep.Some(lastUpdate)).shaped.<>({ r => import r._; _1.map(_ => InventoryRow.tupled((_1.get, _2.get, _3.get, _4.get))) }, (_: Any) => throw new Exception("Inserting into ? projection not supported."))

    /** Database column inventory_id SqlType(serial), AutoInc, PrimaryKey */
    val inventoryId: Rep[Int] = column[Int]("inventory_id", O.AutoInc, O.PrimaryKey)
    /** Database column film_id SqlType(int2) */
    val filmId: Rep[Int] = column[Int]("film_id")
    /** Database column store_id SqlType(int2) */
    val storeId: Rep[Int] = column[Int]("store_id")
    /** Database column last_update SqlType(timestamp) */
    val lastUpdate: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("last_update")

    /** Foreign key referencing Film (database name inventory_film_id_fkey) */
    lazy val filmFk = foreignKey("inventory_film_id_fkey", filmId, Film)(r => r.filmId, onUpdate = ForeignKeyAction.Cascade, onDelete = ForeignKeyAction.Restrict)
    /** Foreign key referencing Store (database name inventory_store_id_fkey) */
    lazy val storeFk = foreignKey("inventory_store_id_fkey", storeId, Store)(r => r.storeId, onUpdate = ForeignKeyAction.Cascade, onDelete = ForeignKeyAction.Restrict)

    /** Index over (storeId,filmId) (database name idx_store_id_film_id) */
    val index1 = index("idx_store_id_film_id", (storeId, filmId))
  }

  /** Collection-like TableQuery object for table Inventory */
  lazy val Inventory = new TableQuery(tag => new Inventory(tag))

  /** Entity class storing rows of table Language
    *
    * @param languageId Database column language_id SqlType(serial), AutoInc, PrimaryKey
    * @param name       Database column name SqlType(bpchar), Length(20,false)
    * @param lastUpdate Database column last_update SqlType(timestamp) */
  case class LanguageRow(languageId: Int, name: String, lastUpdate: java.sql.Timestamp)

  /** GetResult implicit for fetching LanguageRow objects using plain SQL queries */
  implicit def GetResultLanguageRow(e0: GR[Int], e1: GR[String], e2: GR[java.sql.Timestamp]): GR[LanguageRow] = GR {
    prs => import prs._
      LanguageRow.tupled((<<[Int], <<[String], <<[java.sql.Timestamp]))
  }

  /** Table description of table language. Objects of this class serve as prototypes for rows in queries. */
  class Language(_tableTag: Tag) extends Table[LanguageRow](_tableTag, "language") {
    def * = (languageId, name, lastUpdate) <>(LanguageRow.tupled, LanguageRow.unapply)

    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(languageId), Rep.Some(name), Rep.Some(lastUpdate)).shaped.<>({ r => import r._; _1.map(_ => LanguageRow.tupled((_1.get, _2.get, _3.get))) }, (_: Any) => throw new Exception("Inserting into ? projection not supported."))

    /** Database column language_id SqlType(serial), AutoInc, PrimaryKey */
    val languageId: Rep[Int] = column[Int]("language_id", O.AutoInc, O.PrimaryKey)
    /** Database column name SqlType(bpchar), Length(20,false) */
    val name: Rep[String] = column[String]("name", O.Length(20, varying = false))
    /** Database column last_update SqlType(timestamp) */
    val lastUpdate: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("last_update")
  }

  /** Collection-like TableQuery object for table Language */
  lazy val Language = new TableQuery(tag => new Language(tag))

  /** Entity class storing rows of table Payment
    *
    * @param paymentId   Database column payment_id SqlType(serial), AutoInc, PrimaryKey
    * @param customerId  Database column customer_id SqlType(int2)
    * @param staffId     Database column staff_id SqlType(int2)
    * @param rentalId    Database column rental_id SqlType(int4)
    * @param amount      Database column amount SqlType(numeric)
    * @param paymentDate Database column payment_date SqlType(timestamp) */
  case class PaymentRow(paymentId: Int, customerId: Int, staffId: Int, rentalId: Int, amount: BigDecimal, paymentDate: java.sql.Timestamp)

  /** GetResult implicit for fetching PaymentRow objects using plain SQL queries */
  implicit def GetResultPaymentRow(e0: GR[Int], e1: GR[Int], e2: GR[BigDecimal], e3: GR[java.sql.Timestamp]): GR[PaymentRow] = GR {
    prs => import prs._
      PaymentRow.tupled((<<[Int], <<[Int], <<[Int], <<[Int], <<[BigDecimal], <<[java.sql.Timestamp]))
  }

  /** Table description of table payment. Objects of this class serve as prototypes for rows in queries. */
  class Payment(_tableTag: Tag) extends Table[PaymentRow](_tableTag, "payment") {
    def * = (paymentId, customerId, staffId, rentalId, amount, paymentDate) <>(PaymentRow.tupled, PaymentRow.unapply)

    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(paymentId), Rep.Some(customerId), Rep.Some(staffId), Rep.Some(rentalId), Rep.Some(amount), Rep.Some(paymentDate)).shaped.<>({ r => import r._; _1.map(_ => PaymentRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get))) }, (_: Any) => throw new Exception("Inserting into ? projection not supported."))

    /** Database column payment_id SqlType(serial), AutoInc, PrimaryKey */
    val paymentId: Rep[Int] = column[Int]("payment_id", O.AutoInc, O.PrimaryKey)
    /** Database column customer_id SqlType(int2) */
    val customerId: Rep[Int] = column[Int]("customer_id")
    /** Database column staff_id SqlType(int2) */
    val staffId: Rep[Int] = column[Int]("staff_id")
    /** Database column rental_id SqlType(int4) */
    val rentalId: Rep[Int] = column[Int]("rental_id")
    /** Database column amount SqlType(numeric) */
    val amount: Rep[BigDecimal] = column[BigDecimal]("amount")
    /** Database column payment_date SqlType(timestamp) */
    val paymentDate: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("payment_date")

    /** Foreign key referencing Customer (database name payment_customer_id_fkey) */
    lazy val customerFk = foreignKey("payment_customer_id_fkey", customerId, Customer)(r => r.customerId, onUpdate = ForeignKeyAction.Cascade, onDelete = ForeignKeyAction.Restrict)
    /** Foreign key referencing Rental (database name payment_rental_id_fkey) */
    lazy val rentalFk = foreignKey("payment_rental_id_fkey", rentalId, Rental)(r => r.rentalId, onUpdate = ForeignKeyAction.Cascade, onDelete = ForeignKeyAction.SetNull)
    /** Foreign key referencing Staff (database name payment_staff_id_fkey) */
    lazy val staffFk = foreignKey("payment_staff_id_fkey", staffId, Staff)(r => r.staffId, onUpdate = ForeignKeyAction.Cascade, onDelete = ForeignKeyAction.Restrict)
  }

  /** Collection-like TableQuery object for table Payment */
  lazy val Payment = new TableQuery(tag => new Payment(tag))

  /** Entity class storing rows of table PaymentP200701
    *
    * @param paymentId   Database column payment_id SqlType(serial), AutoInc
    * @param customerId  Database column customer_id SqlType(int2)
    * @param staffId     Database column staff_id SqlType(int2)
    * @param rentalId    Database column rental_id SqlType(int4)
    * @param amount      Database column amount SqlType(numeric)
    * @param paymentDate Database column payment_date SqlType(timestamp) */
  case class PaymentP200701Row(paymentId: Int, customerId: Int, staffId: Int, rentalId: Int, amount: BigDecimal, paymentDate: java.sql.Timestamp)

  /** GetResult implicit for fetching PaymentP200701Row objects using plain SQL queries */
  implicit def GetResultPaymentP200701Row(e0: GR[Int], e1: GR[Int], e2: GR[BigDecimal], e3: GR[java.sql.Timestamp]): GR[PaymentP200701Row] = GR {
    prs => import prs._
      PaymentP200701Row.tupled((<<[Int], <<[Int], <<[Int], <<[Int], <<[BigDecimal], <<[java.sql.Timestamp]))
  }

  /** Table description of table payment_p2007_01. Objects of this class serve as prototypes for rows in queries. */
  class PaymentP200701(_tableTag: Tag) extends Table[PaymentP200701Row](_tableTag, "payment_p2007_01") {
    def * = (paymentId, customerId, staffId, rentalId, amount, paymentDate) <>(PaymentP200701Row.tupled, PaymentP200701Row.unapply)

    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(paymentId), Rep.Some(customerId), Rep.Some(staffId), Rep.Some(rentalId), Rep.Some(amount), Rep.Some(paymentDate)).shaped.<>({ r => import r._; _1.map(_ => PaymentP200701Row.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get))) }, (_: Any) => throw new Exception("Inserting into ? projection not supported."))

    /** Database column payment_id SqlType(serial), AutoInc */
    val paymentId: Rep[Int] = column[Int]("payment_id", O.AutoInc)
    /** Database column customer_id SqlType(int2) */
    val customerId: Rep[Int] = column[Int]("customer_id")
    /** Database column staff_id SqlType(int2) */
    val staffId: Rep[Int] = column[Int]("staff_id")
    /** Database column rental_id SqlType(int4) */
    val rentalId: Rep[Int] = column[Int]("rental_id")
    /** Database column amount SqlType(numeric) */
    val amount: Rep[BigDecimal] = column[BigDecimal]("amount")
    /** Database column payment_date SqlType(timestamp) */
    val paymentDate: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("payment_date")

    /** Foreign key referencing Customer (database name payment_p2007_01_customer_id_fkey) */
    lazy val customerFk = foreignKey("payment_p2007_01_customer_id_fkey", customerId, Customer)(r => r.customerId, onUpdate = ForeignKeyAction.NoAction, onDelete = ForeignKeyAction.NoAction)
    /** Foreign key referencing Rental (database name payment_p2007_01_rental_id_fkey) */
    lazy val rentalFk = foreignKey("payment_p2007_01_rental_id_fkey", rentalId, Rental)(r => r.rentalId, onUpdate = ForeignKeyAction.NoAction, onDelete = ForeignKeyAction.NoAction)
    /** Foreign key referencing Staff (database name payment_p2007_01_staff_id_fkey) */
    lazy val staffFk = foreignKey("payment_p2007_01_staff_id_fkey", staffId, Staff)(r => r.staffId, onUpdate = ForeignKeyAction.NoAction, onDelete = ForeignKeyAction.NoAction)
  }

  /** Collection-like TableQuery object for table PaymentP200701 */
  lazy val PaymentP200701 = new TableQuery(tag => new PaymentP200701(tag))

  /** Entity class storing rows of table PaymentP200702
    *
    * @param paymentId   Database column payment_id SqlType(serial), AutoInc
    * @param customerId  Database column customer_id SqlType(int2)
    * @param staffId     Database column staff_id SqlType(int2)
    * @param rentalId    Database column rental_id SqlType(int4)
    * @param amount      Database column amount SqlType(numeric)
    * @param paymentDate Database column payment_date SqlType(timestamp) */
  case class PaymentP200702Row(paymentId: Int, customerId: Int, staffId: Int, rentalId: Int, amount: BigDecimal, paymentDate: java.sql.Timestamp)

  /** GetResult implicit for fetching PaymentP200702Row objects using plain SQL queries */
  implicit def GetResultPaymentP200702Row(e0: GR[Int], e1: GR[Int], e2: GR[BigDecimal], e3: GR[java.sql.Timestamp]): GR[PaymentP200702Row] = GR {
    prs => import prs._
      PaymentP200702Row.tupled((<<[Int], <<[Int], <<[Int], <<[Int], <<[BigDecimal], <<[java.sql.Timestamp]))
  }

  /** Table description of table payment_p2007_02. Objects of this class serve as prototypes for rows in queries. */
  class PaymentP200702(_tableTag: Tag) extends Table[PaymentP200702Row](_tableTag, "payment_p2007_02") {
    def * = (paymentId, customerId, staffId, rentalId, amount, paymentDate) <>(PaymentP200702Row.tupled, PaymentP200702Row.unapply)

    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(paymentId), Rep.Some(customerId), Rep.Some(staffId), Rep.Some(rentalId), Rep.Some(amount), Rep.Some(paymentDate)).shaped.<>({ r => import r._; _1.map(_ => PaymentP200702Row.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get))) }, (_: Any) => throw new Exception("Inserting into ? projection not supported."))

    /** Database column payment_id SqlType(serial), AutoInc */
    val paymentId: Rep[Int] = column[Int]("payment_id", O.AutoInc)
    /** Database column customer_id SqlType(int2) */
    val customerId: Rep[Int] = column[Int]("customer_id")
    /** Database column staff_id SqlType(int2) */
    val staffId: Rep[Int] = column[Int]("staff_id")
    /** Database column rental_id SqlType(int4) */
    val rentalId: Rep[Int] = column[Int]("rental_id")
    /** Database column amount SqlType(numeric) */
    val amount: Rep[BigDecimal] = column[BigDecimal]("amount")
    /** Database column payment_date SqlType(timestamp) */
    val paymentDate: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("payment_date")

    /** Foreign key referencing Customer (database name payment_p2007_02_customer_id_fkey) */
    lazy val customerFk = foreignKey("payment_p2007_02_customer_id_fkey", customerId, Customer)(r => r.customerId, onUpdate = ForeignKeyAction.NoAction, onDelete = ForeignKeyAction.NoAction)
    /** Foreign key referencing Rental (database name payment_p2007_02_rental_id_fkey) */
    lazy val rentalFk = foreignKey("payment_p2007_02_rental_id_fkey", rentalId, Rental)(r => r.rentalId, onUpdate = ForeignKeyAction.NoAction, onDelete = ForeignKeyAction.NoAction)
    /** Foreign key referencing Staff (database name payment_p2007_02_staff_id_fkey) */
    lazy val staffFk = foreignKey("payment_p2007_02_staff_id_fkey", staffId, Staff)(r => r.staffId, onUpdate = ForeignKeyAction.NoAction, onDelete = ForeignKeyAction.NoAction)
  }

  /** Collection-like TableQuery object for table PaymentP200702 */
  lazy val PaymentP200702 = new TableQuery(tag => new PaymentP200702(tag))

  /** Entity class storing rows of table PaymentP200703
    *
    * @param paymentId   Database column payment_id SqlType(serial), AutoInc
    * @param customerId  Database column customer_id SqlType(int2)
    * @param staffId     Database column staff_id SqlType(int2)
    * @param rentalId    Database column rental_id SqlType(int4)
    * @param amount      Database column amount SqlType(numeric)
    * @param paymentDate Database column payment_date SqlType(timestamp) */
  case class PaymentP200703Row(paymentId: Int, customerId: Int, staffId: Int, rentalId: Int, amount: BigDecimal, paymentDate: java.sql.Timestamp)

  /** GetResult implicit for fetching PaymentP200703Row objects using plain SQL queries */
  implicit def GetResultPaymentP200703Row(e0: GR[Int], e1: GR[Int], e2: GR[BigDecimal], e3: GR[java.sql.Timestamp]): GR[PaymentP200703Row] = GR {
    prs => import prs._
      PaymentP200703Row.tupled((<<[Int], <<[Int], <<[Int], <<[Int], <<[BigDecimal], <<[java.sql.Timestamp]))
  }

  /** Table description of table payment_p2007_03. Objects of this class serve as prototypes for rows in queries. */
  class PaymentP200703(_tableTag: Tag) extends Table[PaymentP200703Row](_tableTag, "payment_p2007_03") {
    def * = (paymentId, customerId, staffId, rentalId, amount, paymentDate) <>(PaymentP200703Row.tupled, PaymentP200703Row.unapply)

    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(paymentId), Rep.Some(customerId), Rep.Some(staffId), Rep.Some(rentalId), Rep.Some(amount), Rep.Some(paymentDate)).shaped.<>({ r => import r._; _1.map(_ => PaymentP200703Row.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get))) }, (_: Any) => throw new Exception("Inserting into ? projection not supported."))

    /** Database column payment_id SqlType(serial), AutoInc */
    val paymentId: Rep[Int] = column[Int]("payment_id", O.AutoInc)
    /** Database column customer_id SqlType(int2) */
    val customerId: Rep[Int] = column[Int]("customer_id")
    /** Database column staff_id SqlType(int2) */
    val staffId: Rep[Int] = column[Int]("staff_id")
    /** Database column rental_id SqlType(int4) */
    val rentalId: Rep[Int] = column[Int]("rental_id")
    /** Database column amount SqlType(numeric) */
    val amount: Rep[BigDecimal] = column[BigDecimal]("amount")
    /** Database column payment_date SqlType(timestamp) */
    val paymentDate: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("payment_date")

    /** Foreign key referencing Customer (database name payment_p2007_03_customer_id_fkey) */
    lazy val customerFk = foreignKey("payment_p2007_03_customer_id_fkey", customerId, Customer)(r => r.customerId, onUpdate = ForeignKeyAction.NoAction, onDelete = ForeignKeyAction.NoAction)
    /** Foreign key referencing Rental (database name payment_p2007_03_rental_id_fkey) */
    lazy val rentalFk = foreignKey("payment_p2007_03_rental_id_fkey", rentalId, Rental)(r => r.rentalId, onUpdate = ForeignKeyAction.NoAction, onDelete = ForeignKeyAction.NoAction)
    /** Foreign key referencing Staff (database name payment_p2007_03_staff_id_fkey) */
    lazy val staffFk = foreignKey("payment_p2007_03_staff_id_fkey", staffId, Staff)(r => r.staffId, onUpdate = ForeignKeyAction.NoAction, onDelete = ForeignKeyAction.NoAction)
  }

  /** Collection-like TableQuery object for table PaymentP200703 */
  lazy val PaymentP200703 = new TableQuery(tag => new PaymentP200703(tag))

  /** Entity class storing rows of table PaymentP200704
    *
    * @param paymentId   Database column payment_id SqlType(serial), AutoInc
    * @param customerId  Database column customer_id SqlType(int2)
    * @param staffId     Database column staff_id SqlType(int2)
    * @param rentalId    Database column rental_id SqlType(int4)
    * @param amount      Database column amount SqlType(numeric)
    * @param paymentDate Database column payment_date SqlType(timestamp) */
  case class PaymentP200704Row(paymentId: Int, customerId: Int, staffId: Int, rentalId: Int, amount: BigDecimal, paymentDate: java.sql.Timestamp)

  /** GetResult implicit for fetching PaymentP200704Row objects using plain SQL queries */
  implicit def GetResultPaymentP200704Row(e0: GR[Int], e1: GR[Int], e2: GR[BigDecimal], e3: GR[java.sql.Timestamp]): GR[PaymentP200704Row] = GR {
    prs => import prs._
      PaymentP200704Row.tupled((<<[Int], <<[Int], <<[Int], <<[Int], <<[BigDecimal], <<[java.sql.Timestamp]))
  }

  /** Table description of table payment_p2007_04. Objects of this class serve as prototypes for rows in queries. */
  class PaymentP200704(_tableTag: Tag) extends Table[PaymentP200704Row](_tableTag, "payment_p2007_04") {
    def * = (paymentId, customerId, staffId, rentalId, amount, paymentDate) <>(PaymentP200704Row.tupled, PaymentP200704Row.unapply)

    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(paymentId), Rep.Some(customerId), Rep.Some(staffId), Rep.Some(rentalId), Rep.Some(amount), Rep.Some(paymentDate)).shaped.<>({ r => import r._; _1.map(_ => PaymentP200704Row.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get))) }, (_: Any) => throw new Exception("Inserting into ? projection not supported."))

    /** Database column payment_id SqlType(serial), AutoInc */
    val paymentId: Rep[Int] = column[Int]("payment_id", O.AutoInc)
    /** Database column customer_id SqlType(int2) */
    val customerId: Rep[Int] = column[Int]("customer_id")
    /** Database column staff_id SqlType(int2) */
    val staffId: Rep[Int] = column[Int]("staff_id")
    /** Database column rental_id SqlType(int4) */
    val rentalId: Rep[Int] = column[Int]("rental_id")
    /** Database column amount SqlType(numeric) */
    val amount: Rep[BigDecimal] = column[BigDecimal]("amount")
    /** Database column payment_date SqlType(timestamp) */
    val paymentDate: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("payment_date")

    /** Foreign key referencing Customer (database name payment_p2007_04_customer_id_fkey) */
    lazy val customerFk = foreignKey("payment_p2007_04_customer_id_fkey", customerId, Customer)(r => r.customerId, onUpdate = ForeignKeyAction.NoAction, onDelete = ForeignKeyAction.NoAction)
    /** Foreign key referencing Rental (database name payment_p2007_04_rental_id_fkey) */
    lazy val rentalFk = foreignKey("payment_p2007_04_rental_id_fkey", rentalId, Rental)(r => r.rentalId, onUpdate = ForeignKeyAction.NoAction, onDelete = ForeignKeyAction.NoAction)
    /** Foreign key referencing Staff (database name payment_p2007_04_staff_id_fkey) */
    lazy val staffFk = foreignKey("payment_p2007_04_staff_id_fkey", staffId, Staff)(r => r.staffId, onUpdate = ForeignKeyAction.NoAction, onDelete = ForeignKeyAction.NoAction)
  }

  /** Collection-like TableQuery object for table PaymentP200704 */
  lazy val PaymentP200704 = new TableQuery(tag => new PaymentP200704(tag))

  /** Entity class storing rows of table PaymentP200705
    *
    * @param paymentId   Database column payment_id SqlType(serial), AutoInc
    * @param customerId  Database column customer_id SqlType(int2)
    * @param staffId     Database column staff_id SqlType(int2)
    * @param rentalId    Database column rental_id SqlType(int4)
    * @param amount      Database column amount SqlType(numeric)
    * @param paymentDate Database column payment_date SqlType(timestamp) */
  case class PaymentP200705Row(paymentId: Int, customerId: Int, staffId: Int, rentalId: Int, amount: BigDecimal, paymentDate: java.sql.Timestamp)

  /** GetResult implicit for fetching PaymentP200705Row objects using plain SQL queries */
  implicit def GetResultPaymentP200705Row(e0: GR[Int], e1: GR[Int], e2: GR[BigDecimal], e3: GR[java.sql.Timestamp]): GR[PaymentP200705Row] = GR {
    prs => import prs._
      PaymentP200705Row.tupled((<<[Int], <<[Int], <<[Int], <<[Int], <<[BigDecimal], <<[java.sql.Timestamp]))
  }

  /** Table description of table payment_p2007_05. Objects of this class serve as prototypes for rows in queries. */
  class PaymentP200705(_tableTag: Tag) extends Table[PaymentP200705Row](_tableTag, "payment_p2007_05") {
    def * = (paymentId, customerId, staffId, rentalId, amount, paymentDate) <>(PaymentP200705Row.tupled, PaymentP200705Row.unapply)

    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(paymentId), Rep.Some(customerId), Rep.Some(staffId), Rep.Some(rentalId), Rep.Some(amount), Rep.Some(paymentDate)).shaped.<>({ r => import r._; _1.map(_ => PaymentP200705Row.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get))) }, (_: Any) => throw new Exception("Inserting into ? projection not supported."))

    /** Database column payment_id SqlType(serial), AutoInc */
    val paymentId: Rep[Int] = column[Int]("payment_id", O.AutoInc)
    /** Database column customer_id SqlType(int2) */
    val customerId: Rep[Int] = column[Int]("customer_id")
    /** Database column staff_id SqlType(int2) */
    val staffId: Rep[Int] = column[Int]("staff_id")
    /** Database column rental_id SqlType(int4) */
    val rentalId: Rep[Int] = column[Int]("rental_id")
    /** Database column amount SqlType(numeric) */
    val amount: Rep[BigDecimal] = column[BigDecimal]("amount")
    /** Database column payment_date SqlType(timestamp) */
    val paymentDate: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("payment_date")

    /** Foreign key referencing Customer (database name payment_p2007_05_customer_id_fkey) */
    lazy val customerFk = foreignKey("payment_p2007_05_customer_id_fkey", customerId, Customer)(r => r.customerId, onUpdate = ForeignKeyAction.NoAction, onDelete = ForeignKeyAction.NoAction)
    /** Foreign key referencing Rental (database name payment_p2007_05_rental_id_fkey) */
    lazy val rentalFk = foreignKey("payment_p2007_05_rental_id_fkey", rentalId, Rental)(r => r.rentalId, onUpdate = ForeignKeyAction.NoAction, onDelete = ForeignKeyAction.NoAction)
    /** Foreign key referencing Staff (database name payment_p2007_05_staff_id_fkey) */
    lazy val staffFk = foreignKey("payment_p2007_05_staff_id_fkey", staffId, Staff)(r => r.staffId, onUpdate = ForeignKeyAction.NoAction, onDelete = ForeignKeyAction.NoAction)
  }

  /** Collection-like TableQuery object for table PaymentP200705 */
  lazy val PaymentP200705 = new TableQuery(tag => new PaymentP200705(tag))

  /** Entity class storing rows of table PaymentP200706
    *
    * @param paymentId   Database column payment_id SqlType(serial), AutoInc
    * @param customerId  Database column customer_id SqlType(int2)
    * @param staffId     Database column staff_id SqlType(int2)
    * @param rentalId    Database column rental_id SqlType(int4)
    * @param amount      Database column amount SqlType(numeric)
    * @param paymentDate Database column payment_date SqlType(timestamp) */
  case class PaymentP200706Row(paymentId: Int, customerId: Int, staffId: Int, rentalId: Int, amount: BigDecimal, paymentDate: java.sql.Timestamp)

  /** GetResult implicit for fetching PaymentP200706Row objects using plain SQL queries */
  implicit def GetResultPaymentP200706Row(e0: GR[Int], e1: GR[Int], e2: GR[BigDecimal], e3: GR[java.sql.Timestamp]): GR[PaymentP200706Row] = GR {
    prs => import prs._
      PaymentP200706Row.tupled((<<[Int], <<[Int], <<[Int], <<[Int], <<[BigDecimal], <<[java.sql.Timestamp]))
  }

  /** Table description of table payment_p2007_06. Objects of this class serve as prototypes for rows in queries. */
  class PaymentP200706(_tableTag: Tag) extends Table[PaymentP200706Row](_tableTag, "payment_p2007_06") {
    def * = (paymentId, customerId, staffId, rentalId, amount, paymentDate) <>(PaymentP200706Row.tupled, PaymentP200706Row.unapply)

    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(paymentId), Rep.Some(customerId), Rep.Some(staffId), Rep.Some(rentalId), Rep.Some(amount), Rep.Some(paymentDate)).shaped.<>({ r => import r._; _1.map(_ => PaymentP200706Row.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get))) }, (_: Any) => throw new Exception("Inserting into ? projection not supported."))

    /** Database column payment_id SqlType(serial), AutoInc */
    val paymentId: Rep[Int] = column[Int]("payment_id", O.AutoInc)
    /** Database column customer_id SqlType(int2) */
    val customerId: Rep[Int] = column[Int]("customer_id")
    /** Database column staff_id SqlType(int2) */
    val staffId: Rep[Int] = column[Int]("staff_id")
    /** Database column rental_id SqlType(int4) */
    val rentalId: Rep[Int] = column[Int]("rental_id")
    /** Database column amount SqlType(numeric) */
    val amount: Rep[BigDecimal] = column[BigDecimal]("amount")
    /** Database column payment_date SqlType(timestamp) */
    val paymentDate: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("payment_date")

    /** Foreign key referencing Customer (database name payment_p2007_06_customer_id_fkey) */
    lazy val customerFk = foreignKey("payment_p2007_06_customer_id_fkey", customerId, Customer)(r => r.customerId, onUpdate = ForeignKeyAction.NoAction, onDelete = ForeignKeyAction.NoAction)
    /** Foreign key referencing Rental (database name payment_p2007_06_rental_id_fkey) */
    lazy val rentalFk = foreignKey("payment_p2007_06_rental_id_fkey", rentalId, Rental)(r => r.rentalId, onUpdate = ForeignKeyAction.NoAction, onDelete = ForeignKeyAction.NoAction)
    /** Foreign key referencing Staff (database name payment_p2007_06_staff_id_fkey) */
    lazy val staffFk = foreignKey("payment_p2007_06_staff_id_fkey", staffId, Staff)(r => r.staffId, onUpdate = ForeignKeyAction.NoAction, onDelete = ForeignKeyAction.NoAction)
  }

  /** Collection-like TableQuery object for table PaymentP200706 */
  lazy val PaymentP200706 = new TableQuery(tag => new PaymentP200706(tag))

  /** Entity class storing rows of table Rental
    *
    * @param rentalId    Database column rental_id SqlType(serial), AutoInc, PrimaryKey
    * @param rentalDate  Database column rental_date SqlType(timestamp)
    * @param inventoryId Database column inventory_id SqlType(int4)
    * @param customerId  Database column customer_id SqlType(int2)
    * @param returnDate  Database column return_date SqlType(timestamp), Default(None)
    * @param staffId     Database column staff_id SqlType(int2)
    * @param lastUpdate  Database column last_update SqlType(timestamp) */
  case class RentalRow(rentalId: Int, rentalDate: java.sql.Timestamp, inventoryId: Int, customerId: Int, returnDate: Option[java.sql.Timestamp] = None, staffId: Int, lastUpdate: java.sql.Timestamp)

  /** GetResult implicit for fetching RentalRow objects using plain SQL queries */
  implicit def GetResultRentalRow(e0: GR[Int], e1: GR[java.sql.Timestamp], e2: GR[Int], e3: GR[Option[java.sql.Timestamp]]): GR[RentalRow] = GR {
    prs => import prs._
      RentalRow.tupled((<<[Int], <<[java.sql.Timestamp], <<[Int], <<[Int], <<?[java.sql.Timestamp], <<[Int], <<[java.sql.Timestamp]))
  }

  /** Table description of table rental. Objects of this class serve as prototypes for rows in queries. */
  class Rental(_tableTag: Tag) extends Table[RentalRow](_tableTag, "rental") {
    def * = (rentalId, rentalDate, inventoryId, customerId, returnDate, staffId, lastUpdate) <>(RentalRow.tupled, RentalRow.unapply)

    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(rentalId), Rep.Some(rentalDate), Rep.Some(inventoryId), Rep.Some(customerId), returnDate, Rep.Some(staffId), Rep.Some(lastUpdate)).shaped.<>({ r => import r._; _1.map(_ => RentalRow.tupled((_1.get, _2.get, _3.get, _4.get, _5, _6.get, _7.get))) }, (_: Any) => throw new Exception("Inserting into ? projection not supported."))

    /** Database column rental_id SqlType(serial), AutoInc, PrimaryKey */
    val rentalId: Rep[Int] = column[Int]("rental_id", O.AutoInc, O.PrimaryKey)
    /** Database column rental_date SqlType(timestamp) */
    val rentalDate: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("rental_date")
    /** Database column inventory_id SqlType(int4) */
    val inventoryId: Rep[Int] = column[Int]("inventory_id")
    /** Database column customer_id SqlType(int2) */
    val customerId: Rep[Int] = column[Int]("customer_id")
    /** Database column return_date SqlType(timestamp), Default(None) */
    val returnDate: Rep[Option[java.sql.Timestamp]] = column[Option[java.sql.Timestamp]]("return_date", O.Default(None))
    /** Database column staff_id SqlType(int2) */
    val staffId: Rep[Int] = column[Int]("staff_id")
    /** Database column last_update SqlType(timestamp) */
    val lastUpdate: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("last_update")

    /** Foreign key referencing Customer (database name rental_customer_id_fkey) */
    lazy val customerFk = foreignKey("rental_customer_id_fkey", customerId, Customer)(r => r.customerId, onUpdate = ForeignKeyAction.Cascade, onDelete = ForeignKeyAction.Restrict)
    /** Foreign key referencing Inventory (database name rental_inventory_id_fkey) */
    lazy val inventoryFk = foreignKey("rental_inventory_id_fkey", inventoryId, Inventory)(r => r.inventoryId, onUpdate = ForeignKeyAction.Cascade, onDelete = ForeignKeyAction.Restrict)
    /** Foreign key referencing Staff (database name rental_staff_id_fkey) */
    lazy val staffFk = foreignKey("rental_staff_id_fkey", staffId, Staff)(r => r.staffId, onUpdate = ForeignKeyAction.Cascade, onDelete = ForeignKeyAction.Restrict)

    /** Uniqueness Index over (rentalDate,inventoryId,customerId) (database name idx_unq_rental_rental_date_inventory_id_customer_id) */
    val index1 = index("idx_unq_rental_rental_date_inventory_id_customer_id", (rentalDate, inventoryId, customerId), unique = true)
  }

  /** Collection-like TableQuery object for table Rental */
  lazy val Rental = new TableQuery(tag => new Rental(tag))

  /** Entity class storing rows of table Staff
    *
    * @param staffId    Database column staff_id SqlType(serial), AutoInc, PrimaryKey
    * @param firstName  Database column first_name SqlType(varchar), Length(45,true)
    * @param lastName   Database column last_name SqlType(varchar), Length(45,true)
    * @param addressId  Database column address_id SqlType(int2)
    * @param email      Database column email SqlType(varchar), Length(50,true), Default(None)
    * @param storeId    Database column store_id SqlType(int2)
    * @param active     Database column active SqlType(bool), Default(true)
    * @param username   Database column username SqlType(varchar), Length(16,true)
    * @param password   Database column password SqlType(varchar), Length(40,true), Default(None)
    * @param lastUpdate Database column last_update SqlType(timestamp)
    * @param picture    Database column picture SqlType(bytea), Default(None) */
  case class StaffRow(staffId: Int, firstName: String, lastName: String, addressId: Int, email: Option[String] = None,
                      storeId: Int, active: Boolean = true, username: String, password: Option[String] = None,
                      lastUpdate: java.sql.Timestamp, picture: Option[String] = None)

  /** GetResult implicit for fetching StaffRow objects using plain SQL queries */
  implicit def GetResultStaffRow(e0: GR[Int], e1: GR[String], e2: GR[Int], e3: GR[Option[String]], e4: GR[Boolean], e5: GR[java.sql.Timestamp], e6: GR[Option[String]]): GR[StaffRow] = GR {
    prs => import prs._
      StaffRow.tupled((<<[Int], <<[String], <<[String], <<[Int], <<?[String], <<[Int], <<[Boolean], <<[String], <<?[String], <<[java.sql.Timestamp], <<?[String]))
  }

  /** Table description of table staff. Objects of this class serve as prototypes for rows in queries. */
  class Staff(_tableTag: Tag) extends Table[StaffRow](_tableTag, "staff") {
    def * = (staffId, firstName, lastName, addressId, email, storeId, active, username, password, lastUpdate, picture) <>(StaffRow.tupled, StaffRow.unapply)

    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(staffId), Rep.Some(firstName), Rep.Some(lastName), Rep.Some(addressId), email, Rep.Some(storeId), Rep.Some(active), Rep.Some(username), password, Rep.Some(lastUpdate), picture).shaped.<>({ r => import r._; _1.map(_ => StaffRow.tupled((_1.get, _2.get, _3.get, _4.get, _5, _6.get, _7.get, _8.get, _9, _10.get, _11))) }, (_: Any) => throw new Exception("Inserting into ? projection not supported."))

    /** Database column staff_id SqlType(serial), AutoInc, PrimaryKey */
    val staffId: Rep[Int] = column[Int]("staff_id", O.AutoInc, O.PrimaryKey)
    /** Database column first_name SqlType(varchar), Length(45,true) */
    val firstName: Rep[String] = column[String]("first_name", O.Length(45, varying = true))
    /** Database column last_name SqlType(varchar), Length(45,true) */
    val lastName: Rep[String] = column[String]("last_name", O.Length(45, varying = true))
    /** Database column address_id SqlType(int2) */
    val addressId: Rep[Int] = column[Int]("address_id")
    /** Database column email SqlType(varchar), Length(50,true), Default(None) */
    val email: Rep[Option[String]] = column[Option[String]]("email", O.Length(50, varying = true), O.Default(None))
    /** Database column store_id SqlType(int2) */
    val storeId: Rep[Int] = column[Int]("store_id")
    /** Database column active SqlType(bool), Default(true) */
    val active: Rep[Boolean] = column[Boolean]("active", O.Default(true))
    /** Database column username SqlType(varchar), Length(16,true) */
    val username: Rep[String] = column[String]("username", O.Length(16, varying = true))
    /** Database column password SqlType(varchar), Length(40,true), Default(None) */
    val password: Rep[Option[String]] = column[Option[String]]("password", O.Length(40, varying = true), O.Default(None))
    /** Database column last_update SqlType(timestamp) */
    val lastUpdate: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("last_update")
    /** Database column picture SqlType(bytea), Default(None) */
    val picture: Rep[Option[String]] = column[Option[String]]("picture", O.Default(None))

    /** Foreign key referencing Address (database name staff_address_id_fkey) */
    lazy val addressFk = foreignKey("staff_address_id_fkey", addressId, Address)(r => r.addressId, onUpdate = ForeignKeyAction.Cascade, onDelete = ForeignKeyAction.Restrict)
    /** Foreign key referencing Store (database name staff_store_id_fkey) */
    lazy val storeFk = foreignKey("staff_store_id_fkey", storeId, Store)(r => r.storeId, onUpdate = ForeignKeyAction.NoAction, onDelete = ForeignKeyAction.NoAction)
  }

  /** Collection-like TableQuery object for table Staff */
  lazy val Staff = new TableQuery(tag => new Staff(tag))

  /** Entity class storing rows of table Store
    *
    * @param storeId        Database column store_id SqlType(serial), AutoInc, PrimaryKey
    * @param managerStaffId Database column manager_staff_id SqlType(int2)
    * @param addressId      Database column address_id SqlType(int2)
    * @param lastUpdate     Database column last_update SqlType(timestamp) */
  case class StoreRow(storeId: Int, managerStaffId: Int, addressId: Int, lastUpdate: java.sql.Timestamp)

  /** GetResult implicit for fetching StoreRow objects using plain SQL queries */
  implicit def GetResultStoreRow(e0: GR[Int], e1: GR[Int], e2: GR[java.sql.Timestamp]): GR[StoreRow] = GR {
    prs => import prs._
      StoreRow.tupled((<<[Int], <<[Int], <<[Int], <<[java.sql.Timestamp]))
  }

  /** Table description of table store. Objects of this class serve as prototypes for rows in queries. */
  class Store(_tableTag: Tag) extends Table[StoreRow](_tableTag, "store") {
    def * = (storeId, managerStaffId, addressId, lastUpdate) <>(StoreRow.tupled, StoreRow.unapply)

    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(storeId), Rep.Some(managerStaffId), Rep.Some(addressId), Rep.Some(lastUpdate)).shaped.<>({ r => import r._; _1.map(_ => StoreRow.tupled((_1.get, _2.get, _3.get, _4.get))) }, (_: Any) => throw new Exception("Inserting into ? projection not supported."))

    /** Database column store_id SqlType(serial), AutoInc, PrimaryKey */
    val storeId: Rep[Int] = column[Int]("store_id", O.AutoInc, O.PrimaryKey)
    /** Database column manager_staff_id SqlType(int2) */
    val managerStaffId: Rep[Int] = column[Int]("manager_staff_id")
    /** Database column address_id SqlType(int2) */
    val addressId: Rep[Int] = column[Int]("address_id")
    /** Database column last_update SqlType(timestamp) */
    val lastUpdate: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("last_update")

    /** Foreign key referencing Address (database name store_address_id_fkey) */
    lazy val addressFk = foreignKey("store_address_id_fkey", addressId, Address)(r => r.addressId, onUpdate = ForeignKeyAction.Cascade, onDelete = ForeignKeyAction.Restrict)
    /** Foreign key referencing Staff (database name store_manager_staff_id_fkey) */
    lazy val staffFk = foreignKey("store_manager_staff_id_fkey", managerStaffId, Staff)(r => r.staffId, onUpdate = ForeignKeyAction.Cascade, onDelete = ForeignKeyAction.Restrict)

    /** Uniqueness Index over (managerStaffId) (database name idx_unq_manager_staff_id) */
    val index1 = index("idx_unq_manager_staff_id", managerStaffId, unique = true)
  }

  /** Collection-like TableQuery object for table Store */
  lazy val Store = new TableQuery(tag => new Store(tag))
}
