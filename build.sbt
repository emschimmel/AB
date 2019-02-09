organization := "nl.emschimmel.ab"

name := "ab-example-application"

version := "1.0-SNAPSHOT"

scalaVersion := "2.12.8"

val akkaVersion = "2.4.20"
val akkaHttpVersion = "10.0.11"
val cassandraVersion = "3.6.0"
val circeVersion = "0.11.1"
val scalaTestVersion = "3.0.5"

libraryDependencies ++= Seq(
  "com.datastax.cassandra" % "cassandra-driver-core" % cassandraVersion,
  "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
  "com.typesafe.akka" %% "akka-actor" % akkaVersion,
  "io.circe" %% "circe-core" % circeVersion,
  "org.scalatest" %% "scalatest" % scalaTestVersion % Test
)
