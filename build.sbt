
lazy val commonSettings = Seq(
    scalaVersion := "2.13.5",
    scalacOptions ++= Seq("-deprecation"),
    libraryDependencies ++= Seq(
        "org.scalatest" %% "scalatest" % "3.2.9" % Test,
        "org.scalacheck" %% "scalacheck" % "1.15.4" % Test,
    )
)

lazy val root = (project in file("."))
    .settings(commonSettings)
    .aggregate(adalia, mathModule, pathModule, shapeModule)

lazy val adalia = (project in file("adalia"))
    .settings(commonSettings)

lazy val mathModule = (project in file("modules/math"))
    .settings(commonSettings)

lazy val pathModule = (project in file("modules/path"))
    .settings(commonSettings)
    .aggregate(mathModule)
    .dependsOn(mathModule)

lazy val shapeModule = (project in file("modules/shape"))
    .settings(commonSettings)
    .aggregate(mathModule, pathModule)
    .dependsOn(mathModule, pathModule)

