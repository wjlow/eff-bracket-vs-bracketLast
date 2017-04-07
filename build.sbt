
scalaVersion := "2.12.1"

sbtVersion := "0.13.11"


addCompilerPlugin("org.spire-math" %% "kind-projector" % "0.9.3")


val effVersion = "4.2.0"

libraryDependencies ++= Seq(
  "org.typelevel" %% "cats" % "0.9.0",
  "org.atnos" %% "eff" % effVersion,
  "org.atnos" %% "eff-monix" % effVersion,
  "org.specs2" %% "specs2-core" % "3.8.6" % "test"
)


scalacOptions ++= Seq(
  "-Xfatal-warnings",
  "-deprecation",
  "-Ypartial-unification",
  "-unchecked",
  "-feature",
  "-language:higherKinds")

