package org.basis

import scala.collection.mutable.Map
import scala.util.matching.Regex
import org.basis.util.PatternExtractor

class Router extends PatternExtractor {
  private case class Route(pattern: String, params: List[String], action: () => Unit)

  private val routes = Map.empty[String, List[Route]]

  def register(httpMethod: String, pattern: String, action: => Unit): Unit = {
    val routeRegex = extractRoutePattern(pattern)
    val method = httpMethod.toUpperCase

    if (routes.get(method) == None) routes(method) = Nil

    routes(method) ++= List(Route(routeRegex, extractParamNames(pattern), () => action))
  }

  def find(httpMethod: String, url: String): (Map[String, String], () => Unit) = {
    var mappedParams = Map.empty[String, String]
    val method = httpMethod.toUpperCase

    routes(method).foreach { route =>
      if ("/+$".r.replaceAllIn(url, "") == route.pattern) {
        return (Map(), route.action)
      }
    }

    routes(method).toList.sortBy { route => route.params.size }.foreach { route =>
      val pattern = new Regex(route.pattern, route.params:_*)
      pattern.findAllIn(url).matchData.foreach { m =>
        m.groupNames.foreach { g =>
          mappedParams(g) = m.group(g)
        }
      }

      if (route.params.size > 0 && mappedParams.size == route.params.size) {
        return (mappedParams, route.action)
      } else {
        mappedParams = Map()
      }
    }

    (Map(), {() => })
  }
}
