package com.musinsa.stat.sales.domain

import com.musinsa.stat.restdoc.EnumResponseFieldsSnippet
import com.musinsa.stat.restdoc.GET
import com.musinsa.stat.restdoc.RestDocsControllerHelper
import com.musinsa.stat.restdoc.성공_검증
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.payload.FieldDescriptor
import org.springframework.restdocs.payload.PayloadDocumentation.beneathPath
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.payload.PayloadSubsectionExtractor
import org.springframework.restdocs.snippet.Attributes.attributes
import org.springframework.restdocs.snippet.Attributes.key


@WebMvcTest(controllers = [MetricController::class])
class MetricControllerTest : RestDocsControllerHelper() {
    @Test
    fun METRIC_목록_가져오기() {
        mockMvc.GET("/test/metric").성공_검증(
            """
                {"DAILY":"일별","MONTLY":"월별","PARTNER":"업체별","BRAND":"브랜드별","BRAND_PARTNER":"브랜드업체별","GOODS":"상품별","AD":"광고별","COUPON":"쿠폰별","CATEGORY":"카테고리별"}
            """.trimIndent()
        ).andDo(
            document(
                "test/metric",
                enumResponseFields(
                    "enum-response",
                    enumConvertFieldDescriptor(
                        Metric.values()
                            .associate { Pair(it.name, it.description) }
                    ),
                    attributes(key("name").value("description")),
                    beneathPath("")
                )
            )
        )
    }

    /**
     * EnumResponseFieldSnippet 사용
     */
    fun enumResponseFields(
        type: String,
        descriptors: List<FieldDescriptor>,
        attributes: Map<String, Any>,
        subsectionExtractor: PayloadSubsectionExtractor<*>
    ): EnumResponseFieldsSnippet {
        return EnumResponseFieldsSnippet(
            type = type,
            descriptors = descriptors,
            attributes = attributes,
            subsectionExtractor = subsectionExtractor
        )
    }

    /**
     * enumValue to FieldDescriptor
     */
    fun enumConvertFieldDescriptor(enumValues: Map<String, String>): List<FieldDescriptor> {
        return enumValues.map { enum -> fieldWithPath(enum.key).description(enum.value) }
            .toList()
    }
}