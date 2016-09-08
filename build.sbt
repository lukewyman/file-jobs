name := "file-jobs"

version := "1.0"

val catsVersion = "0.7.2"
val scalaV = "2.11.8"

lazy val coreSettings = Seq(
  scalaVersion := scalaV,
  libraryDependencies ++= Seq(
    "org.typelevel" %% "cats" % catsVersion,
    "org.typelevel" %% "cats-free" % catsVersion
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