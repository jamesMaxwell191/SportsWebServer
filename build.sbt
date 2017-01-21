name := "sports-web-server"

version := "1.0"

scalaVersion := "2.11.8"

lazy val akkaVersion = "2.4.16"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-cluster" % akkaVersion,
  "com.typesafe.akka" %% "akka-cluster-tools" % akkaVersion,
  "com.typesafe.akka" %% "akka-cluster-metrics" % akkaVersion,
  "com.typesafe.akka" %% "akka-http-spray-json" % "10.0.1",
  "io.spray" %% "spray-json" % "1.3.3",
  "com.marcom" %% "sportscommon" % "1.0",
  "com.typesafe.akka" %% "akka-http" % "10.0.1",
  "com.typesafe.akka" %% "akka-stream-kafka" % "0.13",
  "org.scalatest" %% "scalatest" % "3.0.1" % "test"
)


    
