package com.musinsa.stat.sales.domain

import com.musinsa.stat.restdoc.ENUM_DOCS_생성
import com.musinsa.stat.restdoc.GET
import com.musinsa.stat.restdoc.RestDocsEnumControllerHelper
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest

@WebMvcTest(controllers = [MetricController::class])
class MetricControllerTest : RestDocsEnumControllerHelper() {
    @Test
    fun METRIC_목록_가져오기() {
        mockMvc.GET("/test/metric").ENUM_DOCS_생성("test/metric", METRIC_결과())
    }
}