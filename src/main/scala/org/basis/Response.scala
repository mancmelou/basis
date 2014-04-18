package org.basis

import scala.collection.mutable.Map

class Response(var status: Int = 200, var headers: Map[String, String] = Map(), var body: String = "") {
  def header(name: String) = headers(name)

  def header(name: String, value: String) {
    headers(name) = value
  }

  def <<(out: String): Response = {
    body += out
    this
  }

  def toMap = {
    Map(
      "status"  -> status,
      "headers" -> headers,
      "body"    -> body
    )
  }
}

object Response {
  def apply(status: Int, headers: Map[String, String], content: String) = {
    new Response(status, headers, content)
  }
}