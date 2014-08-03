package org.scarlet

import javax.servlet.http.{HttpServletResponse, HttpServletRequest, HttpServlet}

trait Servlet extends HttpServlet {
  /**
   * Servlet request handler
   *
   * @param req A HttpServletRequest object
   * @param res A HttpServletResponse object
   */
  def process(req: HttpServletRequest, res: HttpServletResponse): Unit

  /**
   * Dispatches request from servlet
   *
   * @param req   A HttpServletRequest object
   * @param res   A HttpServletResponse object
   * @return      Return value from the action block
   */
  def dispatch(req: HttpServletRequest, res: HttpServletResponse): Any

  /**
   * Renders action result
   *
   * @param res
   */
  def render(res: HttpServletResponse): Unit

  /**
   * Servlet service method. All requests will go through
   * @param req
   * @param res
   */
  override def service(req: HttpServletRequest, res: HttpServletResponse): Unit = process(req, res)
}