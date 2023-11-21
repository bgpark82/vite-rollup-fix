package com.musinsa.harrodsclient.restdoc

import com.musinsa.common.restdoc.ERROR_BODY_URL
import com.musinsa.common.restdoc.ErrorController
import com.musinsa.common.restdoc.에러_명세_가져오기
import com.musinsa.commonwebflux.restdoc.DOCS_생성
import com.musinsa.commonwebflux.restdoc.GET
import com.musinsa.commonwebflux.restdoc.WebFluxRestDocsControllerHelper
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.context.annotation.Import

@Import(ErrorController::class)
@WebFluxTest(controllers = [ErrorController::class])
class ErrorControllerTest : WebFluxRestDocsControllerHelper() {
    @Test
    fun 에러_BODY_가져오기() {
        webTestClient.GET(ERROR_BODY_URL)
            .DOCS_생성(
                "error-body",
                에러_명세_가져오기()
            )
    }
}
