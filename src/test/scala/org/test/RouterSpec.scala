package org.test

import org.scalatest.FunSpec
import org.basis.Router

class RouterSpec extends FunSpec {
  describe("Router") {
    val router = new Router

    describe("register") {
      it("registers a route with no params") {
        router.register("get", "/", {})

        assert(router.find("get", "/")._1 == Map.empty[String, String])
      }

      it("registers a route with params") {
        router.register("get", "/:user", {})

        assert(router.find("get", "/JohnDoe")._1 == Map("user" -> "JohnDoe"))
      }
    }
  }
}
