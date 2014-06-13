package com.mancmelou.scarlet

import javax.servlet.http.{HttpSession, HttpServletRequest}

trait ServletRequest extends HttpServletRequest {
  def getMethod(): String
  def getRequestURI(): String
  def getSession(): HttpSession
}
