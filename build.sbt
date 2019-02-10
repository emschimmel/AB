organization := "nl.emschimmel.ab"

name := "ab-example-application"

version := "1.0-SNAPSHOT"

scalaVersion := "2.12.8"

val akkaVersion = "2.4.20"
val akkaHttpVersion = "10.0.11"
val phantomVersion = "2.33.0"
val circeVersion = "0.11.1"
val scalaTestVersion = "3.0.5"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
  "com.typesafe.akka" %% "akka-actor" % akkaVersion,
  "io.circe" %% "circe-core" % circeVersion,
  "com.outworkers" %% "phantom-dsl" % phantomVersion,
  "org.scalatest" %% "scalatest" % scalaTestVersion % Test,
  "org.mockito" % "mockito-core" % "2.7.22" % Test
)
