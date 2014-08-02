package org.test.servlet

import org.scarlet.ScarletApp

class TestApp extends ScarletApp {
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
