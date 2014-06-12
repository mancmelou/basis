package com.mancmelou.scarlet

import javax.servlet.http.{HttpSession, HttpServletResponse}
import java.io.PrintWriter

trait ServletResponse extends HttpServletResponse {
  def addHeader(name: String, value: String): Unit
  def setStatus(code: Int): Unit
  def getWritter(): PrintWriter
  def getMethod(): String
  def getRequestURI(): String
  def getSession(): HttpSession
}