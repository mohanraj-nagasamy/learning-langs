name := """hello-slick-3.0-mo-examples"""

mainClass in Compile := Some("HelloSlick")

scalaVersion := "2.11.6"

libraryDependencies ++= List(
  "com.typesafe.slick" %% "slick" % "3.0.0",
//  "org.slf4j" % "slf4j-nop" % "1.6.4",
  "com.h2database" % "h2" % "1.3.175",
  "org.scalatest" %% "scalatest" % "2.2.4" % "test"
)
libraryDependencies += "com.typesafe.slick" %% "slick-codegen" % "3.0.0"
libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.1.3"
//libraryDependencies += "ch.qos.logback" % "logback-core" % "1.1.3"
libraryDependencies += "org.postgresql" % "postgresql" % "9.4.1207"

fork in run := true