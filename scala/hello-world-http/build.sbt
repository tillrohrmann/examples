import Dependencies._

ThisBuild / scalaVersion     := "2.13.12"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "dev.restate"
ThisBuild / organizationName := "restate"

Compile / PB.targets := Seq(
  scalapb.gen(grpc = true) -> (Compile / sourceManaged).value / "scalapb",
)

val restateVersion = "0.7.0"

lazy val root = (project in file("."))
  .settings(
    name := "scala-hello-world-http",
    libraryDependencies ++= Seq(
      // Restate SDK
      "dev.restate" % "sdk-common" % restateVersion % "protobuf-src" intransitive(),
      "dev.restate" % "sdk-api" % restateVersion,
      "dev.restate" % "sdk-http-vertx" % restateVersion,

      // To use Jackson to read/write state entries (optional)
      "dev.restate" % "sdk-serde-jackson" % restateVersion,

      // Logging (optional)
      "org.apache.logging.log4j" % "log4j-core" % "2.20.0",

      // Protobuf definitions
      "com.thesamet.scalapb" %% "scalapb-runtime" % scalapb.compiler.Version.scalapbVersion % "protobuf",

      // gRPC dependencies
      "io.grpc" % "grpc-netty" % scalapb.compiler.Version.grpcJavaVersion,
      "com.thesamet.scalapb" %% "scalapb-runtime-grpc" % scalapb.compiler.Version.scalapbVersion,

      // testing
      munit % Test,
    ),
  )

run / fork := true
