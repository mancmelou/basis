package org.basis

import scala.collection.mutable.Map
import scala.util.matching.Regex
import org.basis.util.PatternExtractor

case class Route(params: Map[String, String], action: () => Any)

class Router extends PatternExtractor {
  private case class Pattern(url: String, params: List[String], action: () => Any)

  private val patterns = Map.empty[String, List[Pattern]]

  def register(method: String, pattern: String, action: => Any) {
    val httpMethod = method.toUpperCase

    if (patterns.get(httpMethod) == None) {
      patterns(httpMethod) = Nil
    }

    patterns(httpMethod) ++= List(Pattern(extractRoutePattern(pattern), extractParamNames(pattern), () => action))
  }

  def find(method: String, url: String): Route = {
    var mappedParams = Map.empty[String, String]
    val httpMethod   = method.toUpperCase

    patterns(httpMethod).foreach { route =>
      if ("/+$".r.replaceAllIn(url, "") == route.url) {
        return Route(Map(), route.action)
      }
    }

    patterns(httpMethod).toList.sortBy { route => route.params.size }.foreach { route =>
      val pattern = new Regex(route.url, route.params:_*)
      pattern.findAllIn(url).matchData.foreach { m =>
        m.groupNames.foreach { g =>
          mappedParams(g) = m.group(g)
        }
      }

      if (route.params.size > 0 && mappedParams.size == route.params.size) {
        return Route(mappedParams, route.action)
      } else {
        mappedParams = Map()
      }
    }

    Route(Map(), {() => 404})
  }
}
