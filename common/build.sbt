organization := "s2g2.reactive"

name := "common"

version := "1.0-SNAPSHOT"

scalaVersion := "2.11.8"

resolvers ++= Seq("Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/",
  Resolver.bintrayRepo("hseeberger", "maven"))

libraryDependencies ++= {
  val akkaVersion = "2.4.7"
  val akkaHttpVersion = "2.0.1"
  val json4sVersion = "3.2.11"
  val scalatestVersion = "2.2.6"

  Seq(
    "com.typesafe.akka" %% "akka-slf4j" % akkaVersion,
    "com.typesafe.akka" %% "akka-http-experimental" % akkaHttpVersion,
    "ch.qos.logback" % "logback-classic" % "1.1.2",
    "org.reactivemongo" %% "reactivemongo" % "0.10.5.0.akka23",
    "org.json4s" %% "json4s-native" % json4sVersion,
    "org.json4s" %% "json4s-ext" % json4sVersion,
    "de.heikoseeberger" %% "akka-http-json4s" % "1.4.2",
    "org.scalactic" %% "scalactic" % scalatestVersion,
    "org.scalatest" %% "scalatest" % scalatestVersion % "test"
  )
}
    