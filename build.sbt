ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.3"

lazy val root = (project in file(".")).settings(
  name := "CashMeBack",
  idePackagePrefix.withRank(KeyRanks.Invisible) := Some(
    "io.github.ntdesmond.cashmeback",
  ),
)

libraryDependencies ++= Seq(
  "dev.zio" %% "zio" % "2.1.9",
  "dev.zio" %% "zio-prelude" % "1.0.0-RC31",
  "dev.zio" %% "zio-config" % "4.0.2",
  "dev.zio" %% "zio-config-typesafe" % "4.0.2",
  "dev.zio" %% "zio-config-magnolia" % "4.0.2",
  "dev.zio" %% "zio-test" % "2.1.9" % Test,
  "dev.zio" %% "zio-test-sbt" % "2.1.9" % Test,
  "dev.zio" %% "zio-test-magnolia" % "2.1.9" % Test,
  "io.getquill" %% "quill-jdbc-zio" % "4.8.4",
  "org.postgresql" % "postgresql" % "42.7.3",
  "org.flywaydb" % "flyway-core" % "10.18.2",
)
