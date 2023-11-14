package com.musinsa.harrodsclient.restdoc

import com.musinsa.commonmvc.restdoc.ENUM_DOCS_생성
import com.musinsa.commonmvc.restdoc.GET
import com.musinsa.commonmvc.restdoc.RestDocsEnumControllerHelper
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest

const val ERROR_DOCUMENT_URL = "error"

@WebMvcTest(controllers = [EnumController::class])
class EnumControllerTest : RestDocsEnumControllerHelper() {
    @Test
    fun ERROR_가져오기() {
        mockMvc.GET("/test/error")
            .ENUM_DOCS_생성(ERROR_DOCUMENT_URL, ERROR_VALUES())
    }
}
