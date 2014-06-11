package org.scarlet

import scala.collection.mutable.Map

/**
 * Matched route object that is returned after calling Router.find method
 *
 * @param params  Parameter map Map(name -> value)
 * @param action  Block to execute for this route
 */
case class Route(params: Map[String, String], action: () => Any)
