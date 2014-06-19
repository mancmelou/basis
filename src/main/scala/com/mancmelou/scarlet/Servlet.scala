package com.mancmelou.scarlet

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
   * Servlet GET request handler override
   *
   * @param req HttpServletRequest
   * @param res HttpServletResponse
   */
  override def doGet(req: HttpServletRequest, res: HttpServletResponse): Unit = process(req, res)

  /**
   * Servlet POST request handler override
   *
   * @param req HttpServletRequest
   * @param res HttpServletResponse
   */
  override def doPost(req: HttpServletRequest, res: HttpServletResponse): Unit = process(req, res)
}