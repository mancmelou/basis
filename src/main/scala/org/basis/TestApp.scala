package org.basis

class TestApp extends Basis {
  get("/") {
    header("X-App", "Basis")
    header("Content-type", "text/html")

    <html>
      <title>Welcome</title>
      <body>
        <h1>It works!</h1>
      </body>
    </html>
  }

  get("/:name") {
    "Hello " + param("name") + "!!"
  }

  get("/debug") {
    header("Content-type", "text/plain")

    "Router: "   + router.toString   + "\n\n" +
    "Response: " + response.toString + " " + response.get.toMap + "\n\n" +
    "Request: "  + request.toString  + " " + request.get.toMap + "\n\n"
  }
}