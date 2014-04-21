package org.basis

import scala.collection.mutable.Map
import javax.servlet.http.HttpServletResponse

class Response(var status: Int = HttpServletResponse.SC_OK, var headers: Map[String, String] = Map(), var body: String = "") {
  def header(name: String) = headers.getOrElse(name, "")

  def header(name: String, value: String) {
    headers(name) = value
  }

  // debug helper
  def toMap: Map[String, String] = {
    Map(
      "headers" -> headers.toString,
      "status"  -> status.toString
    )
  }
}