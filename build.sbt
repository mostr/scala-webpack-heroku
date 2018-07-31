import Deps._
import sbt._
import complete.DefaultParsers._

import scala.sys.process.Process

lazy val installYarn = taskKey[Unit]("Install yarn")
lazy val updateYarnDeps = taskKey[Unit]("Update yarn deps")
lazy val yarnTask = inputKey[Unit]("Run yarn with arguments")

def haltOnCmdResultError(result: Int) {
  if (result != 0) {
    throw new Exception("Build failed.")
  }
}

lazy val commonSettings = Seq(
  organization := "sample",
  version := "0.0.1-SNAPSHOT",
  scalaVersion := "2.12.6",
  scalafmtOnCompile := true,
  scalafmtVersion := "1.2.0",
//  installYarn := {
//    println("Installing yarn")
//    haltOnCmdResultError(Process("npm install yarn -g").!)
//  },
//  updateYarnDeps := {
//    installYarn.value
//    println("Updating yarn dependencies")
//    haltOnCmdResultError(Process("yarn install", baseDirectory.value / "ui").!)
//  },
  yarnTask := {
    haltOnCmdResultError(Process("yarn install --production=false", baseDirectory.value / "ui") !)
    val taskName = spaceDelimited("<arg>").parsed.mkString(" ")
    val localYarnCommand = "yarn " + taskName
    haltOnCmdResultError(Process(localYarnCommand, baseDirectory.value / "ui") !)
  }
)

enablePlugins(JavaAppPackaging)

lazy val ui = (project in file("ui"))
  .settings(commonSettings: _*)
  .settings(test in Test := (test in Test).dependsOn(yarnTask.toTask(" run test:single")).value)

lazy val root = (project in file("."))
  .settings(commonSettings)
  .settings(
    name := "sample",
    herokuAppName in Compile := "sample-pipeline",
    libraryDependencies ++= akkaDeps ++ loggerDeps ++ scalaTestDeps ++ otherDeps,
    stage := stage.dependsOn(yarnTask.toTask(" run build")).value,
    unmanagedResourceDirectories in Compile := {
      (unmanagedResourceDirectories in Compile).value ++ List(
        baseDirectory.value / ui.base.getName / "build"
      )
    }
  )
  .aggregate(ui)
