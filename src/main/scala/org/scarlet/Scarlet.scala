package org.scarlet

import javax.servlet.http._
import scala.util.DynamicVariable

abstract class Scarlet extends Servlet {
  /**
   * Router object
   */
  protected val router = new Router

  /**
   * Response option object
   */
  protected val response = new DynamicVariable[Response](null)

  /**
   * Request option object
   */
  protected val request = new DynamicVariable[Request](null)

  /**
   * Sets response status code. DSL to be used withing `post` or `get`.
   *
   * @param code  Http response code
   */
  def status(code: Int): Unit = {
    response.value.status = code
  }

  /**
   * Sets response headers. DSL to be used withing `post` or `get`.
   *
   * @param name   Header name
   * @param value  Header value
   */
  def header(name: String, value: String): Unit = {
    response.value.header(name, value)
  }

  /**
   * Gets response header value. DSL to be used withing `post` or `get`.
   *
   * @param name  Header name
   * @return      Header value
   */
  def header(name: String): String = {
    request.value.header(name)
  }

  /**
   * Gets request parameter value. DSL to be used withing `post` or `get`.
   *
   * @param name  Parameter name
   * @return      Parameter value
   */
  def param(name: String): String = request.value.param(name)

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
      case c: Iterable[Any]  => c.mkString("")
      case f: (() => String) => f()
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
      case c: Iterable[Any]  => c.mkString("")
      case f: (() => String) => f()
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
    response.withValue(new Response(res)) {
      dispatch(req, res) match {
        case n: Int => {
          response.value.status = n
        }
        case str: String => {
          response.value.body = str
        }
        case xml: scala.xml.Elem => {
          response.value.body = xml.toString()
        }
        case collection: Iterable[Any] => {
          response.value.body = collection.mkString("")
        }
        case _ => {
          response.value.status = 404
          response.value.body = "Route not found! " + req.getRequestURI
        }
      }

      render(res)
    }
  }

  /**
   * Dispatches request from servlet
   *
   * @param req   A HttpServletRequest object
   * @param res   A HttpServletResponse object
   * @return      Return value from the action block
   */
  def dispatch(req: HttpServletRequest, res: HttpServletResponse): Any = {
    val root  = "^" + req.getSession.getServletContext.getContextPath
    val uri   = root.r.replaceAllIn(req.getRequestURI, "")
    val route = router.find(req.getMethod, uri)

    request.withValue(new Request(req, route.params)) {
      route.action()
    }
  }

  /**
   * Renders action result
   *
   * @param res
   */
  def render(res: HttpServletResponse): Unit = {
    res.setStatus(response.value.status)
    response.value.headers.foreach { case (key, value) => res.addHeader(key, value) }

    res.getWriter.print(response.value.body)
  }
}
