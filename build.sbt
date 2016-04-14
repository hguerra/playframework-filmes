name := """filmes"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(SbtWeb, PlayJava, PlayEbean)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs,
  evolutions
)

libraryDependencies += "mysql" % "mysql-connector-java" % "5.1.36"