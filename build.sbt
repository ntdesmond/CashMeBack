ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.3"

lazy val root = (project in file("."))
  .settings(
    name := "CashMeBack",
    idePackagePrefix := Some("io.github.ntdesmond.cashmeback"),
  )

libraryDependencies ++= Seq(
  "dev.zio" %% "zio" % "2.1.9",
  "dev.zio" %% "zio-prelude" % "1.0.0-RC31",
  "dev.zio" %% "zio-test" % "2.1.9" % Test,
  "dev.zio" %% "zio-test-sbt" % "2.1.9" % Test,
  "dev.zio" %% "zio-test-magnolia" % "2.1.9" % Test,
)
