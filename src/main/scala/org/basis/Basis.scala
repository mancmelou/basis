package org.basis

import scala.collection.mutable.Map

class Basis {
  val router  = new Router
  var params  = Map.empty[String, String]
  var headers = Map.empty[String, String]

  def get(pattern: String)(block: => Any) {
    router.register("get", pattern, block)
  }

  def dispatch(method: String, url: String): Any = {
    val route = router.find(method, url)
    params = route.params

    route.action()
  }
}