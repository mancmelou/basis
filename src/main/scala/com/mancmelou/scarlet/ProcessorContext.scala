package com.mancmelou.scarlet

trait ProcessorContext {
  /**
   * Response option object
   */
  protected var response: Option[Response] = None

  /**
   * Request option object
   */
  protected var request: Option[Request] = None

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
      case c: Iterable[Any] => c.mkString("")
      case f: (() => Any)   => f().toString
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
      case c: Iterable[Any] => c.mkString("")
      case f: (() => Any)   => f().toString
      case _ => block.toString
    }
  }
}
