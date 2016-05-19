//import play.PlayScala
import com.typesafe.sbt.SbtScalariform.ScalariformKeys
import com.typesafe.sbt.SbtScalariform._
import scalariform.formatter.preferences.{ AlignParameters, AlignSingleLineCaseStatements, CompactControlReadability, DoubleIndentClassDeclaration }

name := "perf-give-api"

version := "1.0"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

// Sometimes an error occurs when the published checksum, such as a sha1 or md5 hash, differs from the checksum computed for a downloaded artifact
checksums in update := Nil

resolvers += Resolver.file("ivy-local", file(Path.userHome.absolutePath + "/.ivy2/local"))

resolvers ++= Seq(
  "Build Maven Repository" at "file:///opt/m2repo",
  "Local Maven Repository" at "file://"+Path.userHome.absolutePath+"/.m2/repository",
  "OCTanner releases" at "http://artifactory.octanner.com/releases/",
  "OCTanner snapshots" at "http://artifactory.octanner.com/snapshots/",
  "OCTanner plugins releases" at "http://artifactory.octanner.com/plugins-releases/",
  "OCTanner plugins snapshots" at "http://artifactory.octanner.com/plugins-snapshots/",
  "Cloudbees private release repository" at "https://repository-oct.forge.cloudbees.com/release",
  "JBoss Public Maven Repository Group" at "https://repository.jboss.org/nexus/content/groups/public-jboss/"
)

credentials += Credentials("oct repository", "repository-oct.forge.cloudbees.com", "oct", "project42")

val playVersion = "2.4.3"
javaOptions ++= Seq("-Xmx512M", "-Xmx2048M", "-XX:MaxPermSize=2048M")

javaOptions in Test += "-Dconfig.file=conf/test.conf"

// show feature warnings
scalacOptions += "-feature"

// show Java unchecked conversion warnings
//javacOptions += "-Xlint:unchecked"

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  "com.zaxxer" % "HikariCP" % "2.3.9",
  jdbc,
  cache,
  javaJpa,
  ws,
  javaWs,
  specs2 % Test,
  "com.typesafe.play" %% "anorm" % "2.4.0",
  "org.scalatest" %% "scalatest" % "2.2.4" % "test",
  "org.scalatestplus" %% "play" % "1.2.0",
  "com.octanner" % "auth-oc_tanner" % "1.2.0",
  "com.octanner" % "play-api-base" % "1.6.7",
  "com.octanner" % "perfservices" % "2.0.67",
  "com.octanner" % "email-services" % "1.0",
  "com.octanner" % "perf-email-publisher" % "1.2",
  "com.google.inject" % "guice" % "3.0",
  "net.codingwell" % "scala-guice_2.10" % "4.0.0-beta",
  "commons-collections" % "commons-collections" % "3.2.1",
  "org.hibernate" % "hibernate-entitymanager" % "4.2.7.Final",
  "com.oracle" % "ojdbc7" % "12.1.0.1",
  "org.mockito" % "mockito-all" % "1.9.5",
  "com.google.code.gson" % "gson" % "2.0",
  "javax.mail" % "mail" % "1.4.5",
  "commons-codec" % "commons-codec" % "1.9",
  "com.amazonaws" % "aws-java-sdk" % "1.9.0",
  "com.wordnik" %% "swagger-play2" % "1.3.11",
  "com.rabbitmq" % "amqp-client" % "3.3.4",
  "joda-time" % "joda-time" % "2.8.1",
  "com.newrelic.agent.java" % "newrelic-agent" % "3.13.0"
)

scalariformSettings

  ScalariformKeys.preferences := ScalariformKeys.preferences.value
  .setPreference(AlignParameters, true)
  .setPreference(AlignSingleLineCaseStatements, true)
  .setPreference(AlignSingleLineCaseStatements.MaxArrowIndent, 100)
  .setPreference(DoubleIndentClassDeclaration, true)
  .setPreference(CompactControlReadability, true)


//lazy val root = (project in file(".")).enablePlugins(PlayScala, BuildInfoPlugin)
//  .settings(buildInfoKeys := Seq[BuildInfoKey](name, version, scalaVersion, sbtVersion), buildInfoPackage := "build")

ScoverageSbtPlugin.ScoverageKeys.coverageExcludedPackages := "<empty>;Reverse.*;views.*"

ScoverageSbtPlugin.ScoverageKeys.coverageMinimum := 50

ScoverageSbtPlugin.ScoverageKeys.coverageFailOnMinimum := false

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator
