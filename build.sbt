name := "Scarlet"

version := "1.0"

libraryDependencies += "org.scalatest" % "scalatest_2.10" % "2.1.0" % "test"

libraryDependencies += "javax.servlet" % "servlet-api" % "2.5" % "provided"

libraryDependencies += "org.mockito" % "mockito-core" % "1.9.5"

libraryDependencies += "httpunit" % "httpunit" % "1.7"

libraryDependencies += "commons-io" % "commons-io" % "2.4"

lazy val war = taskKey[Unit]("Prepares WAR file")

war := {
  import scala.sys.process._
  val app_name = "ROOT"
  println("cp target/scala-2.10/scarlet_2.10-1.0.jar webapp/WEB-INF/lib" !!)
  println("cp -R webapp/WEB-INF WEB-INF" !!)
  println("zip -r " + app_name + ".war WEB-INF" !!)
  println("rm -rf WEB-INF" !!)
  println("mv " + app_name + ".war target/" + app_name + ".war" !!)
  println("Done!")
}