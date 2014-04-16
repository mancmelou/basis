package org.basis

import scala.collection.mutable.Map
import javax.servlet.http._

class Basis extends HttpServlet {
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

  override def doGet(req: HttpServletRequest, res: HttpServletResponse) = {
    dispatch(req.getMethod(), req.getRequestURI) match {
      case ret if res.isInstanceOf[Int]    => "set status"
      case ret if res.isInstanceOf[String] => "set body"
      case _ => "set status to 404"
    }
  }
}