Scarlet
==
Minimalistic web framework like Sinatra, Express or Scalatra.

Example app
--
```scala 
package org.example

import org.scarlet.Scarlet

/**
 * Create a new class that extends the Scarlet class.
 */
class MyApp extends Scarlet {
  /**
   * This will register "GET /" route.
   * Every GET request to the "/" URI will be routed to this block.
   */
  get("/") {
    header("Content-type", "text/json")

    "{status: 'OK', message: 'Scarlet app is up and running!', data: []}"
  }
}
```

