package org.basis

import java.util.Calendar
import scala.concurrent._
import ExecutionContext.Implicits.global
import scala.sys.process._
import java.io._

class ExampleApp extends Basis {
  get("/") {
    header("X-App", "Basis")
    header("Content-type", "text/html")

    future {
      Thread.sleep(5000)

      val pw = new PrintWriter(new File("/tmp/a" ))
      pw.write(".")
      pw.close
    }

    <h1>It works!</h1>
    <p><a href="/debug">See debug info</a></p>
    <p><a href="/form">Test form</a></p>
    <small>Generated on: { Calendar.getInstance.getTime }</small>
  }

  get("/debug") {
    header("Content-type", "text/plain")

    "Router: "   + router.toString   + "\n\n" +
    "Response: " + response.toString + " " + response.get.toMap + "\n\n" +
    "Request: "  + request.toString  + " " + request.get.toMap + "\n\n"
  }

  get("/form") {
    header("Content-type", "text/html")

    html {
      <form action="/form" method="post">
        <span>Your query:</span>
        <input type="text" name="query" />
        <input type="submit" value="Go!" />
      </form>
    }
  }

  post("/form") {
    "You submitted " + param("query") + "!"
  }
}
