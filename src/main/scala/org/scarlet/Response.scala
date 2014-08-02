package org.scarlet

import scala.collection.mutable.Map
import javax.servlet.http.HttpServletResponse

class Response(private val res: HttpServletResponse) {
  /**
   * Response status code e.g. 200, 404, 503 ...
   */
  private var responseStatus = HttpServletResponse.SC_OK

  /**
   * Response headers mapping
   */
  private val responseHeaders = Map.empty[String, String]

  /**
   * Response body. The content that gets returned to the client.
   */
  private var responseBody = ""

  /**
   * Current response's status code
   *
   * @return Status code
   */
  def status: Int = responseStatus

  /**
   * Sets current response's status code
   *
   * @param n Response code
   */
  def status_=(n: Int): Unit = {
    responseStatus = n
  }

  /**
   * Gets current response's header value
   *
   * @param name  Header's name
   * @return      header's value
   */
  def header(name: String): String = {
    responseHeaders.getOrElse(name, "")
  }

  /**
   * Sets current response's header value
   *
   * @param name   Header's name
   * @param value  Header's value
   */
  def header(name: String, value: String): Unit = {
    responseHeaders(name) = value
  }

  /**
   * Gets current response's headers mapping
   *
   * @return
   */
  def headers: Map[String, String] = responseHeaders

  /**
   * Gets current response's body
   *
   * @return
   */
  def body: String = responseBody

  /**
   * Sets current response's body
   *
   * @param content  Response body
   */
  def body_=(content: String): Unit = {
    responseBody = content
  }

  /**
   * Returns map of the fields stored in this object
   *
   * @return Map[String, String]
   */
  def toMap: Map[String, String] = {
    Map(
      "headers" -> responseHeaders.toString,
      "status"  -> responseStatus.toString
    )
  }
}