package org.basis

import scala.collection.mutable.Map
import javax.servlet.http._

class Basis extends HttpServlet {
  private val router   = new Router
  private val response = new Response
  private var params   = Map.empty[String, String]

  //dsl
  def status(code: Int) {
    response.status = code
  }

  //dsl
  def header(name: String, value: String) {
    response.header(name, value)
  }

  //dsl
  def get(pattern: String)(block: => Any) {
    router.register("GET", pattern, block)
  }

  //intern
  def dispatch(method: String, url: String): Any = {
    val route = router.find(method, url)
    params    = route.params

    route.action()
  }

  //inern
  def render(res: HttpServletResponse) {
    res.setStatus(response.status)
    response.headers.foreach { h => res.addHeader(h._1, h._2) }

    res.getWriter().print(response.body)
  }

  //override
  override def doGet(req: HttpServletRequest, res: HttpServletResponse) {
    dispatch("GET", req.getRequestURI()) match {
      case int: Int => {
        response.status = int
      }
      case str: String => {
        response << str
      }
      case xml: scala.xml.Elem => {
        response << xml.toString
      }
    }

    render(res)
  }

  //IGNORE: test app
  get("/") {
    status(404)
    header("X-App", "Basis")

    "Not found :P"
  }

  get("/:name") {
    "Hello " + params("name") + "!!"
  }

  get("/200") {
    200
  }

  get("/503") {
    // header("something", "something")
    200
  }

  get("/h1/:name") {
    <h1>Hello {params("name")}.</h1>
  }
}