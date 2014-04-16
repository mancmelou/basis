package org.test

import org.scalatest.FunSpec
import org.basis.Basis

class BasisSpec extends FunSpec {
  describe("Basis") {
    val basis = new Basis

    describe("get") {
      it("runs / route") {
        basis.get("/") {
          "OK"
        }

        assert(basis.dispatch("get", "/") == "OK")
      }

      it("runs /home route") {
        basis.get("/home") {
          200
        }

        assert(basis.dispatch("get", "/home/") == 200)
      }

      it("runs /about/ route") {
        basis.get("/about/") {
          203
        }

        assert(basis.dispatch("get", "/about") == 203)
      }

      it("runs /:name route") {
        basis.get("/:name") {
          "Hello " + basis.params("name")
        }

        assert(basis.dispatch("get", "/vlad")  == "Hello vlad")
      }

      it("runs /:controller/:action/:id route") {
        basis.get("/:controller/:action/:id") {
          "Calling " + basis.params("controller") +
            "." + basis.params("action") + "(" + basis.params("id") + ")"
        }

        assert(basis.dispatch("get", "/users/view/21")  == "Calling users.view(21)")
      }
    }
  }
}
