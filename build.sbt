organization := "org.caoilte"

name := "bowling"

version := "0.1-SNAPSHOT"

resolvers += "typesafe" at "http://repo.typesafe.com/typesafe/ivy-releases/"

resolvers += "Sonatype OSS Releases" at
  "http://oss.sonatype.org/content/repositories/releases/"

libraryDependencies ++= Seq(
  "junit" % "junit" % "4.8.1" % "test",
  "org.scalatest" %% "scalatest" % "1.7.2" % "test",
  "org.scala-sbt" % "command" % "0.12.0"
  )

libraryDependencies +=
  "org.scalamock" %% "scalamock-scalatest-support" % "2.4"

