package org.example

import org.scarlet.Scarlet

/**
 * Create a new class that extends the Scarlet class.
 */
class DefaultApp extends Scarlet {
  /**
   * This will register "GET /" route.
   * Every GET request to the "/" URI will be routed to this block.
   */
  get("/") {
    header("Content-type", "text/json")

    "{status: 'OK', message: 'Scarlet app is up and running!', data: []}"
  }
}