name := """play-scala-mo-playground"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

resolvers += Resolver.file("ivy-local", file(Path.userHome.absolutePath + "/.ivy2/local"))

resolvers ++= Seq(
  "Local Maven Repository" at "file://" + Path.userHome.absolutePath + "/.m2/repository",
  "Build Maven Repository" at "file:///opt/m2repo",
  "OCTanner releases" at "http://artifactory.octanner.com/releases/",
  "OCTanner snapshots" at "http://artifactory.octanner.com/snapshots/",
  "OCTanner plugins releases" at "http://artifactory.octanner.com/plugins-releases/",
  "OCTanner plugins snapshots" at "http://artifactory.octanner.com/plugins-snapshots/",
  "Cloudbees private release repository" at "https://repository-oct.forge.cloudbees.com/release",
  "Typesafe Repo" at "http://repo.typesafe.com/typesafe/releases",
  "IronIO Repo" at "http://iron-io.github.com/maven/repository",
  "JBoss Public Maven Repository Group" at "https://repository.jboss.org/nexus/content/groups/public-jboss/"
)


scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  "com.typesafe.play" %% "anorm" % "2.5.0",
  "com.oracle" % "ojdbc7" % "12.1.0.1",
  jdbc,
  cache,
  ws,
  specs2 % Test
)
libraryDependencies += "com.h2database" % "h2" % "1.4.191"
libraryDependencies += "com.rabbitmq" % "amqp-client" % "3.3.4"
libraryDependencies += "io.iron.ironmq" % "ironmq" % "3.0.2"


resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator
