organization := "s2g2.reactive"

name := "product-service"

version := "1.0-SNAPSHOT"

scalaVersion := "2.11.8"

libraryDependencies ++= {
  val akkaVersion = "2.4.7"
  val scalatestVersion = "2.2.6"
  Seq(
    "s2g2.reactive" %% "common" % "1.0-SNAPSHOT",
    "org.scalatest" %% "scalatest" % scalatestVersion % "test",
    "com.typesafe.akka" %% "akka-http-testkit" % akkaVersion % "test"
  )
}