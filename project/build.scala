import sbt.Keys._
import sbt._

object Build extends sbt.Build {

  val hello = sbt.TaskKey[Unit]("hello", "Prints 'Hello World'")

  lazy val root = (project in file(".")).settings(
    name := "codavaj",
    version := "1.0.0",
    scalaVersion := "2.11.7",
    libraryDependencies ++= Seq(
      "nekohtml" % "nekohtml" % "0.9.5",
      "httpunit" % "httpunit" % "1.6",
      "dom4j" % "dom4j" % "1.6.1",
      "ant" % "ant" % "1.6.5",
      "ant" % "ant-launcher" % "1.6.5",
      "jaxen" % "jaxen" % "1.1-beta-6",
      "net.sf.jtidy" % "jtidy" % "r938"
    ),
    hello := {
      println("Hello World")
    },
    test in Test <<= (test in Test)
      .dependsOn(sbt.Keys.`package` in (Compile, packageBin) in input)
      //.dependsOn(sbt.Keys.doc in (Compile, doc) in input)
  )

  lazy val input = (project in file("input")).settings(
    version := "1.0.0",
    scalaVersion := "2.11.7"
  )



}