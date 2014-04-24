package org.basis

import scala.collection.mutable.Map
import javax.servlet.http.HttpServletResponse

class Response(private val _res: HttpServletResponse) {
  /**
   * Response status code e.g. 200, 404, 503 ...
   */
  private var _status = HttpServletResponse.SC_OK

  /**
   * Response headers mapping
   */
  private val _headers = Map.empty[String, String]

  /**
   * Response body. The content that gets returned to the client.
   */
  private var _body = ""

  /**
   * Current response's status code
   *
   * @return Status code
   */
  def status: Int = _status

  /**
   * Sets current response's status code
   *
   * @param n Response code
   */
  def status_=(n: Int): Unit = {
    _status = n
  }

  /**
   * Gets current response's header value
   *
   * @param name  Header's name
   * @return      header's value
   */
  def header(name: String): String = _headers.getOrElse(name, "")

  /**
   * Sets current response's header value
   *
   * @param name   Header's name
   * @param value  Header's value
   */
  def header(name: String, value: String): Unit = {
    _headers(name) = value
  }

  /**
   * Gets current response's headers mapping
   *
   * @return
   */
  def headers: Map[String, String] = _headers

  /**
   * Gets current response's body
   *
   * @return
   */
  def body: String = _body

  /**
   * Sets current response's body
   *
   * @param str  Response body
   */
  def body_=(str: String): Unit = {
    _body = str
  }

  /**
   * Handle to the current response's HttpServletResponse object
   *
   * @return A HttpServletResponse object
   */
  def servlet: HttpServletResponse = _res

  /**
   * Returns map of the fields stored in this object
   *
   * @return Mapping
   */
  def toMap: Map[String, String] = {
    Map(
      "headers" -> _headers.toString,
      "status"  -> _status.toString
    )
  }
}