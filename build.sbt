name := "file-jobs"

version := "1.0"



val catsVersion = "0.7.2"

lazy val coreSettings = Seq(
  scalaVersion := "2.11.8",
  libraryDependencies ++= Seq(
    "org.typelevel" %% "cats" % catsVersion,
    "org.typelevel" %% "cats-free" % catsVersion
  )
)

lazy val core = project.in( file("core") )
  .settings(coreSettings)