package org.test

import org.scalatest.FunSpec
import org.basis.{Request, Response, Basis}
import org.mockito.Mockito._
import org.mockito.Matchers._

class BasisSpec extends FunSpec {
  describe("Basis") {
    val basis = mock(classOf[Basis])

    describe("get") {
      it("defines / route") {
        //basis.router.register(any[String], any[String], any[() => Any])
        val spy: Basis = spy(basis)
        verify(spy, times(1)).router.register("GET", "/", any[() => Any])

        basis.get("/")({"OK"})
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

    describe("post") {
      it("defines a route") {
        basis.post("/") {
          "OK"
        }
      }
    }
  }
}
