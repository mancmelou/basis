/**
 * Your project namespace
 */
package org.example

/**
 * Import the Application class and you're ready to go!
 */
import com.mancmelou.scarlet.Application

/**
 * Also, import any other classes you want to use in your project
 */
import java.util.Calendar

/**
 * Create a new class that extends Application
 */
class SimpleScarletApp extends Application {
  /**
   * This will register "GET /" route.
   * Every GET request to the "/" URI will
   * be routed to this block.
   */
  get("/") {
    header("Content-type", "text/html")

    <h1>It works!</h1>
    <p><a href="/debug">See debug info</a></p>
    <p><a href="/form">Test form</a></p>
    <small>Generated on: { Calendar.getInstance.getTime }</small>
  }

  get("/form") {
    /**
     * `html` block will force text/html content type
     */
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

  get("/debug") {

    // text block will force text/plain content type
    text {
      "Router: " + router.toString + "\n\n" +
      "Response: " + response.toString + " " + response.get.toMap + "\n\n" +
      "Request: " + request.toString + " " + request.get.toMap + "\n\n"
    }
  }
}
