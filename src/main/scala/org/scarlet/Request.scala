package org.scarlet

import scala.collection.mutable.Map
import javax.servlet.http.HttpServletRequest
import collection.JavaConversions.enumerationAsScalaIterator

class Request(private val request: HttpServletRequest, private val routeParams: Map[String, String]) {
  /**
   * Request headers container
   */
  private val requestHeaders = Map.empty[String, String]

  /**
   * Request GET and POST requestParams container
   */
  private val requestParams = Map.empty[String, String]

  /**
   * Maps servlet request requestHeaders
   */
  private def mapRequestHeaders(): Unit = {
    for(name <- request.getHeaderNames()) {
      requestHeaders(name.asInstanceOf[String]) = request.getHeader(name.asInstanceOf[String])
    }
  }

  /**
   * Maps servlet request requestParams
   */
  private def mapRequestParams(): Unit = {
    for(name <- request.getParameterNames()) {
      requestParams(name.asInstanceOf[String]) = request.getParameter(name.asInstanceOf[String])
    }

    requestParams ++= routeParams
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
  def param(name: String): String = requestParams.getOrElse(name, "")

  /**
   * Returns map of the fields stored in this object
   *
   * @return Mapping
   */
  def toMap: Map[String, String] = {
    Map(
      "requestHeaders" -> requestHeaders.toString,
      "requestParams"  -> requestParams.toString
    )
  }

  /**
   * Constructor procedure
   */
  mapRequestHeaders()
  mapRequestParams()
}