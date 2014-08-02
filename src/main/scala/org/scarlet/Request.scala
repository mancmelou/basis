package org.scarlet

import scala.collection.mutable.Map
import javax.servlet.http.HttpServletRequest
import collection.JavaConversions.enumerationAsScalaIterator

class Request(private val request: HttpServletRequest, private val _routeParams: Map[String, String]) {
  /**
   * Request headers container
   */
  private val headers = Map.empty[String, String]

  /**
   * Request GET and POST params container
   */
  private val params  = Map.empty[String, String]

  /**
   * Maps servlet request headers
   */
  private def mapRequestHeaders(): Unit = {
    for(name <- request.getHeaderNames()) {
      headers(name.asInstanceOf[String]) = request.getHeader(name.asInstanceOf[String])
    }
  }

  /**
   * Maps servlet request params
   */
  private def mapRequestParams(): Unit = {
    for(name <- request.getParameterNames()) {
      params(name.asInstanceOf[String]) = request.getParameter(name.asInstanceOf[String])
    }

    params ++= _routeParams
  }

  /**
   * Returns the specified header's value
   *
   * @param name  Header name
   * @return      Header value
   */
  def header(name: String): String = headers.getOrElse(name, "")

  /**
   * Returns the specified param's value
   *
   * @param name  Param name
   * @return      Param value
   */
  def param(name: String): String = params.getOrElse(name, "")

  /**
   * Returns map of the fields stored in this object
   *
   * @return Mapping
   */
  def toMap: Map[String, String] = {
    Map(
      "headers" -> headers.toString,
      "params"  -> params.toString
    )
  }

  /**
   * Constructor procedure
   */
  mapRequestHeaders()
  mapRequestParams()
}