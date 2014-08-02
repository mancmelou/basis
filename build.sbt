name := "Scarlet"

version := "1.0"

libraryDependencies += "org.scalatest" % "scalatest_2.10" % "2.1.0" % "test"

libraryDependencies += "javax.servlet" % "servlet-api" % "2.5" % "provided"

libraryDependencies += "org.mockito" % "mockito-core" % "1.9.5"

libraryDependencies += "httpunit" % "httpunit" % "1.7"

libraryDependencies += "commons-io" % "commons-io" % "2.4"

lazy val tomcat = taskKey[Unit]("Deploys servlet to local web server")

tomcat := {
  import scala.sys.process._
  println("cp target/scala-2.10/basis_2.10-1.0.jar WEB-INF/lib" !!)
  println("zip -r ROOT.war WEB-INF" !!)
  println("mv ROOT.war tomcat/ROOT.war" !!)
  println("catalina stop"!!)
  println("catalina start"!!)
  println("Done!")
}
