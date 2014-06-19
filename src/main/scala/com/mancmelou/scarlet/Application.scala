package com.mancmelou.scarlet

import javax.servlet.http._

class Application extends Servlet {
  /**
   * Router object {@link Router}
   */
  protected val router = new Router

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
    val root = "^" + req.getSession().getServletContext().getContextPath()
    val uri  = root.r.replaceAllIn(req.getRequestURI(), "")

    val route = router.find(req.getMethod(), uri)

    Processor(route, req, res).run()
  }
}
