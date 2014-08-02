package org.mydomain.myapp

import org.scarlet.ScarletApp

/**
 * Create a new class that extends the ScarletApp class.
 */
class DefaultApp extends ScarletApp {
  /**
   * This will register "GET /" route.
   * Every GET request to the "/" URI will be routed to this block.
   */
  get("/") {
    "Congratulations! You're running your first Scarlet app."
  }
}