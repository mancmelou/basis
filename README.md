Scarlet
==
Minimalistic web framework similar to Scalatra (Scala), Sinatra (Ruby), Express (JS), Spark (Java)

Example app
--
```scala 
package org.myapp

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

