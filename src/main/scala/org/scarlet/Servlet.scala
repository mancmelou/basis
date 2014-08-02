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
   * Servlet GET request handler override
   *
   * @param req
   * @param res
   */
  override def doGet(req: HttpServletRequest, res: HttpServletResponse): Unit = process(req, res)

  /**
   * Servlet POST request handler override
   *
   * @param req
   * @param res
   */
  override def doPost(req: HttpServletRequest, res: HttpServletResponse): Unit = process(req, res)
}