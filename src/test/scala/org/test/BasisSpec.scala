package org.test

import org.scalatest.FunSpec
import org.basis.Basis
import javax.servlet.http._
import org.easymock.EasyMock._

class BasisSpec extends FunSpec {
  describe("Basis") {
    val basis = new Basis

    val req = createMock(classOf[HttpServletRequest])
    val res = createMock(classOf[HttpServletResponse])

    describe("get") {
      it("defines / route") {
        basis.get("/") {
          "OK"
        }

        assert(basis.dispatch(req, res) == "OK")
      }

      it("defines /home route") {
        basis.get("/home") {
          200
        }
      }

      it("defines /about/ route") {
        basis.get("/about/") {
          203
        }
      }

      it("defines /:name route") {
        basis.get("/:name") {
          "Hello " + basis.param("name")
        }
      }

      it("defines /:controller/:action/:id route") {
        basis.get("/:controller/:action/:id") {
          "Calling " + basis.param("controller") +
            "." + basis.param("action") + "(" + basis.param("id") + ")"
        }
      }
    }
  }
}
