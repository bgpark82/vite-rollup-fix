package com.musinsa.harrodsclient.restdoc

import com.musinsa.commonmvc.restdoc.DOCS_생성
import com.musinsa.commonmvc.restdoc.ERROR_BODY_URL
import com.musinsa.commonmvc.restdoc.ErrorController
import com.musinsa.commonmvc.restdoc.GET
import com.musinsa.commonmvc.restdoc.RestDocsControllerHelper
import com.musinsa.commonmvc.restdoc.에러_명세_가져오기
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.Import

@Import(ErrorController::class)
@WebMvcTest(controllers = [ErrorController::class])
class ErrorControllerTest : RestDocsControllerHelper() {
    @Test
    fun 에러_BODY_가져오기() {
        mockMvc.GET(ERROR_BODY_URL)
            .DOCS_생성(
                "error-body",
                에러_명세_가져오기()
            )
    }
}
