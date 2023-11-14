package com.musinsa.stat.restdoc

import com.musinsa.commonmvc.restdoc.ENUM_DOCS_생성
import com.musinsa.commonmvc.restdoc.GET
import com.musinsa.commonmvc.restdoc.RestDocsEnumControllerHelper
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest

const val ERROR_DOCUMENT_URL = "error"
const val METRIC_DOCUMENT_URL = "enum/metric"
const val ORDER_BY_DOCUMENT_URL = "enum/order-by"
const val SALES_START_DOCUMENT_URL = "enum/sales-start"
const val ORDER_DIRECTION_DOCUMENT_URL = "enum/order-direction"
const val PARTNER_TYPE_DOCUMENT_URL = "enum/partner-type"
const val GOODS_KIND_DOCUMENT_URL = "enum/goods-kind"
const val SALES_FUNNEL_DOCUMENT_URL = "enum/sales-funnel"

@WebMvcTest(controllers = [EnumController::class])
class EnumControllerTest : RestDocsEnumControllerHelper() {
    @Test
    fun ERROR_가져오기() {
        mockMvc.GET("/test/error")
            .ENUM_DOCS_생성(ERROR_DOCUMENT_URL, ERROR_VALUES())
    }

    @Test
    fun METRIC_목록_가져오기() {
        mockMvc.GET("/test/metric")
            .ENUM_DOCS_생성(METRIC_DOCUMENT_URL, METRIC_VALUES())
    }

    @Test
    fun ORDER_BY_목록_가져오기() {
        mockMvc.GET("/test/order-by")
            .ENUM_DOCS_생성(ORDER_BY_DOCUMENT_URL, ORDER_BY_VALUES())
    }

    @Test
    fun SALES_START_목록_가져오기() {
        mockMvc.GET("/test/sales-start")
            .ENUM_DOCS_생성(SALES_START_DOCUMENT_URL, SALES_START_VALUES())
    }

    @Test
    fun ORDER_DIRECTION_목록_가져오기() {
        mockMvc.GET("/test/order-direction")
            .ENUM_DOCS_생성(
                ORDER_DIRECTION_DOCUMENT_URL,
                ORDER_DIRECTION_VALUES()
            )
    }

    @Test
    fun `PARTNER TYPE 목록 가져오기`() {
        mockMvc.GET("/test/partner-type")
            .ENUM_DOCS_생성(
                PARTNER_TYPE_DOCUMENT_URL,
                PARTNER_TYPE_VALUES()
            )
    }

    @Test
    fun `GOODS KIND 목록 가져오기`() {
        mockMvc.GET("/test/goods-kind")
            .ENUM_DOCS_생성(
                GOODS_KIND_DOCUMENT_URL,
                GOODS_KIND_VALUES()
            )
    }

    @Test
    fun `SALES_FUNNEL 목록 가져오기`() {
        mockMvc.GET("/test/sales-funnel")
            .ENUM_DOCS_생성(
                SALES_FUNNEL_DOCUMENT_URL,
                SALES_FUNNEL_VALUES()
            )
    }
}
