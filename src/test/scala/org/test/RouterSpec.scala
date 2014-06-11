package org.test

import org.scalatest.FunSpec
import com.mancmelou.scarlet.Router

class RouterSpec extends FunSpec {
  describe("Router") {
    val router = new Router

    describe("register") {
      it("registers a route with no params") {
        router.register("get", "/", { })

        assert(router.find("get", "/").params == Map.empty[String, String])
      }

      it("registers a route with params") {
        router.register("get", "/:user", { })

        assert(router.find("get", "/JohnDoe").params == Map("user" -> "JohnDoe"))
      }
    }
  }
}
