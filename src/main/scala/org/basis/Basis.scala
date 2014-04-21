package org.basis

import javax.servlet.http._

class Basis extends HttpServlet {
  protected val router = new Router // change this to private

  protected var response: Option[Response] = None // to private
  protected var request : Option[Request]  = None // to private

  //dsl
  def status(code: Int) {
    response.get.status = code
  }

  // dsl
  // sets header value
  def header(name: String, value: String) {
    response.get.header(name, value)
  }

  // dsl
  // gets a header value
  def header(name: String) {
    request.get.header(name)
  }

  // dsl
  // define GET route
  def get(pattern: String)(block: => Any) {
    router.register("GET", pattern, block)
  }

  // dsl
  // take param from GET, POST, COOKIE, Named route
  def param(name: String) = request.get.param(name)

  //intern
  def dispatch(req: HttpServletRequest): Any = {
    val root = "^" + req.getSession().getServletContext().getContextPath()
    val uri  = root.r.replaceAllIn(req.getRequestURI(), "")

    val route = router.find(req.getMethod(), uri)

    response = Some(new Response)
    request  = Some(new Request(req, route.params))

    route.action()
  }

  //intern
  def process(req: HttpServletRequest, res: HttpServletResponse) {
    dispatch(req) match {
      case n: Int => {
        response.get.status = n
      }
      case str: String => {
        response.get.body = str
      }
      case xml: scala.xml.Elem => {
        response.get.body = xml.toString
      }
      case collection: Iterable[Any] => {
        response.get.body = collection.mkString("")
      }
      case _ => {
        response.get.status = HttpServletResponse.SC_NOT_FOUND
        response.get.body   = "Route not found! " + req.getRequestURI()
      }
    }

    render(res)
  }

  //intern
  def render(res: HttpServletResponse) {
    res.setStatus(response.get.status)
    response.get.headers.foreach { case (key, value) => res.addHeader(key, value) }

    res.getWriter().println(response.get.body)
  }

  //override servlet
  override def doGet(req: HttpServletRequest, res: HttpServletResponse) {
    process(req, res)
  }

  //override servlet
  override def doPost(req: HttpServletRequest, res: HttpServletResponse) {
    process(req, res)
  }
}