package com.musinsa.stat.restdoc

import com.musinsa.common.restdoc.*
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
                "error-body", 에러_명세_가져오기()
            )
    }
}