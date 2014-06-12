package com.mancmelou.scarlet

import javax.servlet.http._

class Application extends Servlet {
  /**
   * Router object {@link Router}
   */
  protected val router = new Router

  /**
   * Response option object {@link Router}
   */
  protected var response: Option[Response] = None

  /**
   * Request option object {@link Router}
   */
  protected var request : Option[Request]  = None

  /**
   * Sets response status code. DSL to be used withing `post` or `get`.
   *
   * @param code  Http response code
   */
  def status(code: Int): Unit = {
    response.get.status = code
  }

  /**
   * Sets response headers. DSL to be used withing `post` or `get`.
   *
   * @param name   Header name
   * @param value  Header value
   */
  def header(name: String, value: String): Unit = {
    response.get.header(name, value)
  }

  /**
   * Gets response header value. DSL to be used withing `post` or `get`.
   *
   * @param name  Header name
   * @return      Header value
   */
  def header(name: String): String = {
    request.get.header(name)
  }

  /**
   * Gets request parameter value. DSL to be used withing `post` or `get`.
   *
   * @param name  Parameter name
   * @return      Parameter value
   */
  def param(name: String): String = request.get.param(name)

  /**
   * Returns html to be rendered. A side effect is that the "Content-type" response
   * header is set to "text/html"
   *
   * @param block   Any
   * @return        String
   */
  def html(block: Any): String = {
    header("Content-type", "text/html")

    block match {
      case c: Iterable[Any]     => c.mkString("")
      case f: Function0[String] => f()
      case _ => block.toString
    }
  }

  /**
   * Returns text to be rendered. A side effect is that the "Content-type" response
   * header is set to "text/plain"
   *
   * @param block   Any
   * @return        String
    */
  def text(block: Any): String = {
    header("Content-type", "text/plain")

    block match {
      case c: Iterable[Any]     => c.mkString("")
      case f: Function0[String] => f()
      // TODO: case f: (Unit => String) => f()
      case _ => block.toString
    }
  }

  /**
   * Defines a GET request handler.
   *
   * @param pattern  Route pattern e.g. "/", "/:user", "/:page/:user"
   * @param block    Route handler block
   * @return         Returns the value returned from the block
   */
  def get(pattern: String)(block: => Any): Unit = {
    router.register("GET", pattern, block)
  }

  /**
   * Defines a POST request handler.
   *
   * @param pattern  Route pattern e.g. "/", "/:user", "/:page/:user"
   * @param block    Route handler block
   * @return         Returns the value returned from the block
   */
  def post(pattern: String)(block: => Any): Unit = {
    router.register("POST", pattern, block)
  }

  /**
   * Servlet request handler
   *
   * @param req A HttpServletRequest object
   * @param res A HttpServletResponse object
   */
  def process(req: HttpServletRequest, res: HttpServletResponse): Unit = {
    dispatch(req, res) match {
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

  /**
   * Dispatches request from servlet
   *
   * @param req   A HttpServletRequest object
   * @param res   A HttpServletResponse object
   * @return      Return value from the action block
   */
  def dispatch(req: HttpServletRequest, res: HttpServletResponse): Any = {
    val root = "^" + req.getSession().getServletContext().getContextPath()
    val uri  = root.r.replaceAllIn(req.getRequestURI(), "")

    val route = router.find(req.getMethod(), uri)

    response = Some(new Response(res))
    request  = Some(new Request(req, route.params))

    route.action()
  }

  /**
   * Renders action result
   *
   * @param res
   */
  def render(res: HttpServletResponse): Unit = {
    res.setStatus(response.get.status)
    response.get.headers.foreach { case (key, value) => res.addHeader(key, value) }

    res.getWriter().print(response.get.body)
  }

  /**
   * Servlet GET request handler override
   *
   * @param req
   * @param res
   */
  override def doGet(req: HttpServletRequest, res: HttpServletResponse): Unit = {
    process(req, res)
  }

  /**
   * Servlet POST request handler override
   *
   * @param req
   * @param res
   */
  override def doPost(req: HttpServletRequest, res: HttpServletResponse): Unit = {
    process(req, res)
  }
}
