resolvers ++= Seq(Resolver.mavenLocal,
  "Local Maven Repository" at "file://" + Path.userHome.absolutePath + "/.m2/repository",
  "Local Maven Repository 2" at "file:///" + Path.userHome.absolutePath + "/.m2/repository")

val ccgGroupId = "edu.illinois.cs.cogcomp"

lazy val commonSettings = Seq(
  organization := ccgGroupId,
  version := "0.1.0",
  autoScalaLibrary := false,
  resolvers += "CogcompSoftware" at "http://cogcomp.cs.illinois.edu/m2repo/",
  crossPaths := false,
  publishTo := Some(
    Resolver.ssh(
      "CogcompSoftwareRepo", "bilbo.cs.illinois.edu",
      "/mounts/bilbo/disks/0/www/cogcomp/html/m2repo/"
    )
  )
)

lazy val core = (project in file("core")).
  settings(commonSettings: _*).
  settings(
    name := "Logic2ILP-core",
    libraryDependencies ++= Seq(
      "junit" % "junit" % "4.12" % "test",
      "org.apache.commons" % "commons-lang3" % "3.0",
      ccgGroupId % "illinois-inference" % "0.6.0"
      // "net.sf.trove4j" % "trove4j" % "3.0.3",
      // "org.apache.commons" % "commons-math3" % "3.6.1"
    )
  )

lazy val core_lang = (project in file("core-lang")).dependsOn(core).
  settings(commonSettings: _*).
  settings(
    name := "Logic2ILP-core-lang",
    libraryDependencies ++= Seq(
      "org.antlr" % "antlr4-runtime" % "4.5.3"
    )
  )

lazy val java_lang = (project in file("java_interface")).dependsOn(core).
  settings(commonSettings: _*).
  settings(
    name := "Logic2ILP-java-interface",
    libraryDependencies ++= Seq(
      ccgGroupId % "LBJava" % "1.2.13" % "test"
    )
  )

lazy val example = (project in file("example")).dependsOn(java_lang).
  settings(commonSettings: _*).
  settings(
    name := "Logic2ILP-examples",
    libraryDependencies ++= Seq(
      ccgGroupId % "LBJava" % "1.2.24"
    )
  )

lazy val main = project.in(file("."))
  .aggregate(core, core_lang, java_lang, example)
