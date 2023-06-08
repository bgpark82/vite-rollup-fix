package com.musinsa.stat.restdoc.enumcontroller

import com.musinsa.stat.restdoc.ENUM_DOCS_생성
import com.musinsa.stat.restdoc.GET
import com.musinsa.stat.restdoc.RestDocsEnumControllerHelper
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest

@WebMvcTest(controllers = [EnumController::class])
class EnumControllerTest : RestDocsEnumControllerHelper() {
    @Test
    fun METRIC_목록_가져오기() {
        mockMvc.GET("/test/metric").ENUM_DOCS_생성("test/metric", METRIC_결과())
    }
}