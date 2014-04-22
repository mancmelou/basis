name := "Basis"

version := "1.0"

libraryDependencies += "org.scalatest" % "scalatest_2.10" % "2.1.0" % "test"

libraryDependencies += "javax.servlet" % "servlet-api" % "2.5" % "provided"

libraryDependencies += "org.easymock" % "easymock" % "3.1"

lazy val tomcat = taskKey[Unit]("Deploys app to local web server")

tomcat := {
  import scala.sys.process._
  println("cp target/scala-2.10/basis_2.10-1.0.jar WEB-INF/lib" !!)
  println("zip -r ROOT.war WEB-INF" !!)
  println("mv ROOT.war tomcat/ROOT.war" !!)
  println("catalina stop"!!)
  println("catalina start"!!)
  println("Done!")
}
