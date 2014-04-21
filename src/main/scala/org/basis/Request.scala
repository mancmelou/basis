package org.basis

import scala.collection.mutable.Map
import javax.servlet.http.HttpServletRequest
import collection.JavaConversions.enumerationAsScalaIterator

class Request(val req: HttpServletRequest, val routeParams: Map[String, String]) {
  private val _headers = Map.empty[String, String]
  private val _params  = Map.empty[String, String]

  private def mapRequestHeaders() {
    for(name <- req.getHeaderNames()) {
      _headers(name.asInstanceOf[String]) = req.getHeader(name.asInstanceOf[String])
    }
  }

  private def mapRequestParams() {
    for(name <- req.getParameterNames()) {
      _params(name.asInstanceOf[String]) = req.getParameter(name.asInstanceOf[String])
    }

    _params ++= routeParams
  }

  def header(name: String) = _headers.getOrElse(name, "")

  def param(name: String)  = _params.getOrElse(name, "")

  def toMap: Map[String, String] = {
    Map(
      "headers" -> _headers.toString,
      "params"  -> _params.toString,
      "EXTRA"   -> req.getSession().getServletContext().getContextPath()
    )
  }

  mapRequestHeaders()
  mapRequestParams()
}

// servlet root req.getSession().getServletContext().getContextPath()