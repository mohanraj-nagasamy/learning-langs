name := """learn-scala-oscon-tutorial"""

version := "1.0"

scalaVersion := "2.11.7"

// Change this to another test framework if you prefer
libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.4" % "test"
libraryDependencies += "com.netflix.hystrix" % "hystrix-core" % "1.4.18"
libraryDependencies += "com.netflix.hystrix" % "hystrix-examples" % "1.4.18"
libraryDependencies += "junit" % "junit" % "4.10"
libraryDependencies += "org.mockito" % "mockito-all" % "1.10.19" % "test"
libraryDependencies += "org.scalamock" %% "scalamock-scalatest-support" % "3.2" % "test"
// Uncomment to use Akka
libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.3.11"
libraryDependencies += "com.typesafe.akka" %% "akka-agent" % "2.3.11"
libraryDependencies += "com.typesafe.akka" % "akka-testkit_2.11" % "2.3.11" % "test"
libraryDependencies += "com.typesafe.akka" % "akka-stream-experimental_2.11" % "2.0.3"


libraryDependencies += "net.spy" % "spymemcached" % "2.9.0"
libraryDependencies += "org.skyscreamer" % "jsonassert" % "1.3.0"


