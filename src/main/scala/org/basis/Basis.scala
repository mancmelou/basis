package org.basis

import scala.collection.mutable.Map
import javax.servlet.http._

class Basis extends HttpServlet {
  private val router   = new Router
  private var response = new Response
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

    params   = route.params
    response = new Response

    route.action()
  }

  //intern
  def render(res: HttpServletResponse) {
    res.setStatus(response.status)
    response.headers.foreach { h => res.addHeader(h._1, h._2) }

    res.getWriter().println(response.body)
  }

  //override
  override def doGet(req: HttpServletRequest, res: HttpServletResponse) {
    dispatch("GET", req.getRequestURI()) match {
      case n: Int => {
        response.status = n
      }
      case str: String => {
        response.body = str
      }
      case xml: scala.xml.Elem => {
        response.body = xml.toString
      }
      case collection: Iterable[Any] => {
        response.body = collection.mkString("")
      }
      case _ => {
        response.status = 404
        response.body   = "Basis cannot find the route"
      }
    }

    render(res)
  }

  //IGNORE: test app
  get("/") {
    status(200)

    header("X-App", "Basis")
    header("Content-type", "text/html")

    <html>
      <title>Welcome</title>
      <body>
        <h1>Hello stranger!</h1>

        <h3>DEBUG INFO</h3>
        <p>Router:   {router.toString}</p>
        <p>Response: {response.toString}</p>
        <p>Params:   {params.toString}</p>
      </body>
    </html>
  }

  get("/:name") {
    "Hello " + params("name") + "!!"
  }

  get("/200") {
    200
  }

  get("/503") {
    header("something", "something")
    503
  }

  get("/h1/:name") {
    <h1>Hello {params("name")}.</h1>
  }
}