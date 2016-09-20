name := "file-jobs"

version := "1.0"

val catsVersion = "0.7.2"
val scalaV = "2.11.8"

lazy val coreSettings = Seq(
  scalaVersion := scalaV,
  libraryDependencies ++= Seq(
    "org.typelevel" %% "cats" % catsVersion,
    "org.typelevel" %% "cats-free" % catsVersion,
    "commons-io" % "commons-io" % "2.4",
    "org.scalatest" % "scalatest_2.11" % "3.0.0" % "test",
    "com.amazonaws" % "aws-java-sdk-bom" % "1.11.35",
    "com.amazonaws" % "aws-java-sdk-s3" % "1.11.35"
  )
)

lazy val examplesSettings = Seq(
  scalaVersion := scalaV
)

lazy val root = project.in( file(".") )
  .aggregate(core, examples)

lazy val core = project.in( file("core") )
  .settings(coreSettings)

lazy val examples = project.in( file("examples") )
  .settings(examplesSettings)
  .dependsOn(core)