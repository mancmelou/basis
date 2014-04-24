package org.basis

import scala.collection.mutable.Map
import javax.servlet.http.HttpServletRequest
import collection.JavaConversions.enumerationAsScalaIterator

class Request(private val _req: HttpServletRequest, private val _routeParams: Map[String, String]) {
  /**
   * Request headers container
   */
  private val _headers = Map.empty[String, String]

  /**
   * Request GET and POST params container
   */
  private val _params  = Map.empty[String, String]

  /**
   * Maps servlet request headers
   */
  private def mapRequestHeaders(): Unit = {
    for(name <- _req.getHeaderNames()) {
      _headers(name.asInstanceOf[String]) = _req.getHeader(name.asInstanceOf[String])
    }
  }

  /**
   * Maps servlet request params
   */
  private def mapRequestParams(): Unit = {
    for(name <- _req.getParameterNames()) {
      _params(name.asInstanceOf[String]) = _req.getParameter(name.asInstanceOf[String])
    }

    _params ++= _routeParams
  }

  /**
   * Returns the specified header's value
   *
   * @param name  Header name
   * @return      Header value
   */
  def header(name: String): String = _headers.getOrElse(name, "")

  /**
   * Returns the specified param's value
   *
   * @param name  Param name
   * @return      Param value
   */
  def param(name: String): String = _params.getOrElse(name, "")

  /**
   * Returns current request's {@link HttpServletRequest} object
   *
   * @return
   */
  def servlet = _req

  /**
   * Returns current request's {@link HttpServletRequest} object
   *
   * @return
   */
  def getHttpServletRequest = _req

  /**
   * Returns map of the fields stored in this object
   *
   * @return Mapping
   */
  def toMap: Map[String, String] = {
    Map(
      "headers" -> _headers.toString,
      "params"  -> _params.toString
    )
  }

  /**
   * Constructor procedure
   */
  mapRequestHeaders()
  mapRequestParams()
}