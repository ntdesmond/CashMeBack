ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.3"

lazy val root = (project in file("."))
  .settings(
    name := "CashMeBack",
    idePackagePrefix := Some("io.github.ntdesmond.cashmeback")
  )

libraryDependencies += "dev.zio" %% "zio" % "2.1.9"