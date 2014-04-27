package org.test

import org.scalatest.FunSpec
import com.meterware.servletunit._
import com.meterware.httpunit._

class BasisSpec extends FunSpec {
  val runner = new ServletRunner
  val client = runner.newClient()

  runner.registerServlet("", "org.test.servlet.TestApp")

  describe("Basis") {
    describe("get") {
      it("runs / route") {
        val request  = new GetMethodWebRequest("http://localhost/")
        val response = client.getResponse(request)

        assert(response.getResponseCode() == 200)
        assert(response.getText() == "ROOT")
      }

      it("runs /home route") {
        val request  = new GetMethodWebRequest("http://localhost/home")
        val response = client.getResponse(request)

        assert(response.getResponseCode() == 200)
        assert(response.getText() == "")
      }

      it("runs /about/ route") {
        val request  = new GetMethodWebRequest("http://localhost/about")
        val response = client.getResponse(request)

        assert(response.getResponseCode() == 203)
        assert(response.getText() == "")
      }

      it("runs /:name route") {
        val request  = new GetMethodWebRequest("http://localhost/Scala")
        val response = client.getResponse(request)

        assert(response.getResponseCode() == 200)
        assert(response.getText() == "Hello Scala")
      }

      it("runs /:controller/:action/:id route") {
        val request  = new GetMethodWebRequest("http://localhost/ctrl/act/11")
        val response = client.getResponse(request)

        assert(response.getResponseCode() == 200)
        assert(response.getText() == "Calling ctrl.act(11)")
      }

      it("runs find a route") {
        intercept[HttpNotFoundException] {
          val request = new GetMethodWebRequest("http://localhost/foo/bar")
          client.getResponse(request)
        }
      }
    }

    describe("post") {
      it("runs a POST route") {
        val request = new PostMethodWebRequest("http://localhost/")
        assert(client.getResponse(request).getText() == "<h1>POST OK</h1>")
      }
    }
  }
}
