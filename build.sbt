

name := "fol_ilp"

version := "1.0"

scalaVersion := "2.11.8"



resolvers += (
             "CogcompSoftware" at "http://cogcomp.cs.illinois.edu/m2repo/"
             )

resolvers += Resolver.mavenLocal

resolvers += "Local Maven Repository" at "file://"+Path.userHome.absolutePath+"/.m2/repository"


libraryDependencies ++= Seq(
  "com.codepoetics" % "protonpack" % "1.8",
  "com.googlecode.java-diff-utils" % "diffutils" % "1.2.1",
  "commons-configuration" % "commons-configuration" % "1.6",
  "org.jblas" % "jblas" % "1.2.4",
  "junit" % "junit" % "4.12",

  "net.sf.tweety.logics" % "fol" % "1.6" excludeAll(
    ExclusionRule(organization = "org.ojalgo"),
    ExclusionRule(organization = "jspf"),
    ExclusionRule(organization = "org.slf4j")
    ),

  //"net.sf.tweety" % "tweety-full" % "1.6",
  //"net.sf.tweety" % "plugin" % "1.6",
  //"net.sf.tweety" % "commons" % "1.6",
  //"net.sf.tweety" % "math" % "1.6",
  "org.apache.commons" % "commons-math3" % "3.6.1"
)

// http://mvnrepository.com/artifact/org.apache.commons/commons-lang3
libraryDependencies += "org.apache.commons" % "commons-lang3" % "3.0"
