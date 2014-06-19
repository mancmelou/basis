package com.mancmelou.scarlet

import javax.servlet.http.{HttpServletResponse, HttpServletRequest}

/**
 * Thread safe request processor
 *
 * @param route Matched route, result returned after calling Router.find
 * @param req   HttpServletRequest
 * @param res   HttpServletResponse
 */
class Processor(route: Route, req: HttpServletRequest, res: HttpServletResponse) extends ProcessorContext {
  /**
   * Thread safe request processor run method
   *
   * @return Any
   */
  def run(): Any = {
    response = Some(new Response(res))
    request  = Some(new Request(req, route.params))

    route.action() match {
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
        response.get.status = 404
        response.get.body   = "Route not found! " + req.getRequestURI()
      }
    }

    render(res)
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
}

/**
 * Processor class companion object for easier instantiation
 *
 * @param route Matched route, result returned after calling Router.find
 * @param req   HttpServletRequest
 * @param req   HttpServletResponse
 */
object Processor {
  def apply(route: Route, req: HttpServletRequest, res: HttpServletResponse): Processor = {
    new Processor(route, req, res)
  }
}