package org.scarlet

import scala.collection.mutable.Map
import javax.servlet.http.HttpServletRequest
import collection.JavaConversions.enumerationAsScalaIterator

class Request(private val req: HttpServletRequest, private val routeParams: Map[String, String]) {
  /**
   * Construct procedure
   */
  mapRequestHeaders()
  mapRequestParams()

  /**
   * Request headers container
   */
  private val requestHeaders = Map.empty[String, String]

  /**
   * Request GET and POST params container
   */
  private val requestParamas  = Map.empty[String, String]

  /**
   * Maps servlet request requestHeaders
   */
  private def mapRequestHeaders(): Unit = {
    for(name <- req.getHeaderNames()) {
      requestHeaders(name.asInstanceOf[String]) = req.getHeader(name.asInstanceOf[String])
    }
  }

  /**
   * Maps servlet request requestParamas
   */
  private def mapRequestParams(): Unit = {
    for(name <- req.getParameterNames()) {
      requestParamas(name.asInstanceOf[String]) = req.getParameter(name.asInstanceOf[String])
    }

    requestParamas ++= routeParams
  }

  /**
   * Returns the specified header's value
   *
   * @param name  Header name
   * @return      Header value
   */
  def header(name: String): String = requestHeaders.getOrElse(name, "")

  /**
   * Returns the specified param's value
   *
   * @param name  Param name
   * @return      Param value
   */
  def param(name: String): String = requestParamas.getOrElse(name, "")

  /**
   * Returns map of the fields stored in this object
   *
   * @return Map[String, String]
   */
  def toMap: Map[String, String] = {
    Map(
      "headers" -> requestHeaders.toString,
      "params"  -> requestParamas.toString
    )
  }
}
