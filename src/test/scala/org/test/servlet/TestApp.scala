package org.test.servlet

import org.scarlet._

class TestApp extends Application {
  get("/") {
    "ROOT"
  }

  get("/home") {
    200
  }

  get("/about/") {
    203
  }

  get("/:name") {
    "Hello " + param("name")
  }

  get("/:controller/:action/:id") {
    "Calling " + param("controller") +
      "." + param("action") + "(" + param("id") + ")"
  }

  post("/") {
    html {
      <h1>POST OK</h1>
    }
  }
}
