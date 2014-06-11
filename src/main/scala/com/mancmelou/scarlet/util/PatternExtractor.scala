package com.mancmelou.scarlet.util

trait PatternExtractor {
  /**
   * Extracts named parameter names from a route pattern
   *
   * @param pattern   Route pattern
   * @return          List of parameter names
   */
  protected def extractParamNames(pattern: String): List[String] = {
    "(?<=:)[A-z0-9_]+".r.findAllIn(pattern).toList
  }

  /**
   * Extracts route pattern
   *
   * @param pattern   Route pattern
   * @return          Route Regular expression
   */
  protected def extractRoutePattern(pattern: String): String = {
    if ("(?<=:)[A-z0-9_]+".r.findAllIn(pattern).isEmpty) {
      "/+$".r.replaceAllIn(pattern, "")
    } else {
      "^" + ":[A-z0-9]+".r.replaceAllIn(pattern, "([A-z0-9]+)") + "/?$"
    }
  }
}