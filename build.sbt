organization in ThisBuild := "com.programmersnest"

version in ThisBuild := "1.0-SNAPSHOT"

scalaVersion in ThisBuild := "2.11.8"

val scalaTest = "org.scalatest" %% "scalatest" % "3.0.3"
val mockito = "org.mockito" % "mockito-all" % "1.10.19"
val macwire = "com.softwaremill.macwire" %% "macros" % "2.2.5" % "provided"
val mysql = "mysql" % "mysql-connector-java" % "6.0.6"
val playJsonDerivedCodecs = "org.julienrf" %% "play-json-derived-codecs" % "3.3"

val commonLagomImplSettings = libraryDependencies ++= Seq(
  lagomScaladslPersistenceJdbc,
  lagomScaladslKafkaBroker,
  lagomScaladslTestKit,
  mockito,
  scalaTest,
  macwire,
  mysql,
  "io.get-coursier" %% "coursier" % "1.0.0-RC3",
  "io.get-coursier" %% "coursier-cache" % "1.0.0-RC3"
)

lazy val `mae-app` = (project in file("."))
  .aggregate(`user-api`, `user-impl`)

lazy val common = (project in file("common"))
  .settings(
    libraryDependencies ++= Seq(
      scalaTest
    )
  )

lazy val `user-api` = (project in file("user-api"))
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslApi,
      playJsonDerivedCodecs
    )
  ).dependsOn(common)

lazy val `user-impl` = (project in file("user-impl"))
  .enablePlugins(LagomScala, CopyPasteDetector)
  .settings(lagomForkedTestSettings: _*)
  .settings(commonLagomImplSettings: _*)
  .dependsOn(`user-api`)
