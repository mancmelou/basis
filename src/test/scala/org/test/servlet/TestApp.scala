package org.test.servlet

import org.basis._

class TestApp extends Basis {
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
