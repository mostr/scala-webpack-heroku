import sbt._

object Deps {
  lazy val scalaTestDeps = {
    val scalaTestVersion = "3.0.5"
    Seq(
      "org.scalatest" %% "scalatest" % scalaTestVersion
    ).map(_ % Test)
  }

  lazy val akkaDeps = {
    val akkaVersion = "2.5.12"
    val akkaHttpVersion = "10.0.11"
    Seq(
      "com.typesafe.akka" %% "akka-slf4j" % akkaVersion,
      "com.typesafe.akka" %% "akka-stream" % akkaVersion,
      "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
      "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion
    )
  }

  lazy val loggerDeps = Seq(
    "ch.qos.logback" % "logback-classic" % "1.2.3",
    "ch.qos.logback" % "logback-core" % "1.2.3",
    "com.typesafe.scala-logging" %% "scala-logging" % "3.7.2",
    "org.slf4j" % "log4j-over-slf4j" % "1.7.25"
  )

  lazy val otherDeps = Seq(
    "io.monix" %% "monix" % "3.0.0-RC1"
  )
}
