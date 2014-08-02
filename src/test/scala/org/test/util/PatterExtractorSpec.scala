package org.test.util

import org.scalatest.FunSpec
import org.scarlet.util.PatternExtractor

class PatterExtractorSpec extends FunSpec with PatternExtractor {
  describe("PatternExtractor") {
    describe("extractParamNames") {
      it("finds no parameters") {
        assert(extractParamNames("/pages/about") == List.empty[String])
      }

      it("finds parameters") {
        assert(extractParamNames("/pages/:pageName")  == List("pageName"))
        assert(extractParamNames("/:user/:page.html") == List("user", "page"))
      }
    }

    describe("extractRoutePattern") {
      it("returns regex pattern as a string") {
        assert(extractRoutePattern("/pages/about")      == "/pages/about")
        assert(extractRoutePattern("/pages/:pageName")  == "^/pages/([A-z0-9]+)/?$")
        assert(extractRoutePattern("/:user/:page.html") == "^/([A-z0-9]+)/([A-z0-9]+).html/?$")
      }
    }
  }
}
