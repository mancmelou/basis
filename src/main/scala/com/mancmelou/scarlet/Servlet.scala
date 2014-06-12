package com.mancmelou.scarlet

import javax.servlet.http.{HttpServlet, HttpServletResponse, HttpServletRequest}

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
   * HttpServlet GET request handler override
   *
   * @param req
   * @param res
   */
  protected def doGet(req: HttpServletRequest, res: HttpServletResponse): Unit

  /**
   * HttpServlet POST request handler override
   *
   * @param req
   * @param res
   */
  protected def doPost(req: HttpServletRequest, res: HttpServletResponse): Unit
}