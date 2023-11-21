package com.musinsa.harrodsclient.restdoc

import com.musinsa.commonwebflux.restdoc.ENUM_DOCS_생성
import com.musinsa.commonwebflux.restdoc.GET
import com.musinsa.commonwebflux.restdoc.WebFluxRestDocsEnumControllerHelper
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest

const val ERROR_DOCUMENT_URL = "error"

@WebFluxTest(controllers = [EnumController::class])
class EnumControllerTest : WebFluxRestDocsEnumControllerHelper() {
    @Test
    fun ERROR_가져오기() {
        webTestClient.GET("/test/error")
            .ENUM_DOCS_생성(ERROR_DOCUMENT_URL, ERROR_VALUES())
    }
}
