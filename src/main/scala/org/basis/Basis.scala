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
    dispatch("get", req.getRequestURI) match {
      case ret if res.isInstanceOf[Int]    => res.getWriter().println(ret)
      case ret if res.isInstanceOf[String] => res.getWriter().println(ret)
      case _ => "set status to 404"
    }

    res.setContentType("text/plain")
  }
}