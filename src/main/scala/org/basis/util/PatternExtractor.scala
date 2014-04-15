package org.basis.util

trait PatternExtractor {
  protected def extractParamNames(pattern: String): List[String] = {
    "(?<=:)[A-z0-9_]+".r.findAllIn(pattern).toList
  }

  protected def extractRoutePattern(pattern: String): String = {
    if ("(?<=:)[A-z0-9_]+".r.findAllIn(pattern).isEmpty) {
      "^" + "/+$".r.replaceAllIn(pattern, "") + "/?$"
    } else {
      "^" + ":[A-z0-9]+".r.replaceAllIn(pattern, "([A-z0-9]+)") + "/?$"
    }
  }
}