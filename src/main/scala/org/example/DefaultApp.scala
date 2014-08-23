package org.example

import org.scarlet.ScarletApp

/**
 * Create a new class that extends the ScarletApp class.
 */
sealed class DefaultApp extends ScarletApp {
  /**
   * This will register "GET /" route.
   * Every GET request to the "/" URI will be routed to this block.
   */
  get("/") {
    <style type="text/css">
      <![CDATA[
      #title {
        color: #FF2400;
        font-family: Georgia;
        font-size: 12px;
        text-align: center;
      }
      ]]>
    </style>
    <div id="title">Awesome! You're running your first Scarlet app!</div>
  }
}