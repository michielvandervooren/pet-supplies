organization := "s2g2.reactive"

name := "order-service"

version := "1.0-SNAPSHOT"

scalaVersion := "2.11.8"

libraryDependencies ++= {
  Seq(
    "s2g2.reactive" %% "common" % "1.0-SNAPSHOT"
  )
}