name := "essential-slick-chapter-05"

version := "3.1"

scalaVersion := "2.11.7"

scalacOptions ++= Seq(
  "-deprecation",
  "-encoding", "UTF-8",
  "-unchecked",
  "-feature",
  "-language:implicitConversions",
  "-language:postfixOps",
  "-Ywarn-dead-code",
  "-Xlint",
  "-Xfatal-warnings"
  )


libraryDependencies ++= Seq(
  "com.typesafe.slick" %% "slick"           % "3.1.0",
  "com.h2database"      % "h2"              % "1.4.185",
  "ch.qos.logback"      % "logback-classic" % "1.1.2",
  "joda-time"           % "joda-time"       % "2.6",
  "org.joda"            % "joda-convert"    % "1.2")

initialCommands in console := """
  |import org.joda.time._
  |import scala.concurrent.ExecutionContext.Implicits.global
  |import scala.concurrent.Await
  |import scala.concurrent.duration._
  |import ChatSchema._
  |val schema = new Schema(slick.driver.H2Driver)
  |import schema._
  |import profile.api._
  |import PKs._
  |def exec[T](action: DBIO[T]): T = Await.result(db.run(action), 2 seconds)
  |val db = Database.forConfig("chapter05")
  |exec(populate)
""".trim.stripMargin


