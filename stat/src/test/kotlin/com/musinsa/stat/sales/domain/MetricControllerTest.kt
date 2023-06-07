package com.musinsa.stat.sales.domain

import com.musinsa.stat.restdoc.GET
import com.musinsa.stat.restdoc.RestDocsControllerHelper
import com.musinsa.stat.restdoc.성공_검증
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest

@WebMvcTest(controllers = [MetricController::class])
class MetricControllerTest : RestDocsControllerHelper() {
    @Test
    fun METRIC_목록_가져오기() {
        mockMvc.GET("/test/metric").성공_검증(
            """
                {"DAILY":"일별","MONTLY":"월별","PARTNER":"업체별","BRAND":"브랜드별","BRAND_PARTNER":"브랜드업체별","GOODS":"상품별","AD":"광고별","COUPON":"쿠폰별","CATEGORY":"카테고리별"}
            """.trimIndent()
        )
    }
}