package com.musinsa.stat.search.controller

import com.musinsa.stat.search.dto.무신사_브랜드_목록
import com.musinsa.stat.search.dto.브랜드_명세
import com.musinsa.stat.search.dto.아디다스_업체_목록
import com.musinsa.stat.search.dto.업체_명세
import com.musinsa.stat.search.service.SearchService
import com.musinsa.stat.util.*
import com.musinsa.stat.util.ObjectMapperFactory.writeValueAsString
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.RestDocumentationExtension
import org.springframework.restdocs.request.RequestDocumentation.parameterWithName
import org.springframework.test.web.servlet.MockMvc
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.context.WebApplicationContext

@ExtendWith(RestDocumentationExtension::class)
@WebMvcTest(controllers = [SearchController::class])
class SearchControllerTest(@Autowired var mockMvc: MockMvc) {
    @MockBean
    lateinit var searchService: SearchService

    @BeforeEach
    fun setUp(
        webApplicationContext: WebApplicationContext,
        restDocumentationContextProvider: RestDocumentationContextProvider
    ) {
        this.mockMvc = buildMockMvc(
            webApplicationContext,
            restDocumentationContextProvider
        )
    }

    @Test
    fun 브랜드_목록_가져오기() {
        val 응답값 = 무신사_브랜드_목록()
        val 검색어 = "무신"
        whenever(
            searchService.getBrands(검색어)
        ).thenReturn(응답값)

        val queryParams = LinkedMultiValueMap<String, String>()
        queryParams["searchTerms"] = 검색어

        mockMvc.GET("/search/brand", queryParams)
            .성공_검증(writeValueAsString(응답값))
            .DOCS_생성(
                "search/brand",
                listOf(parameterWithName("searchTerms").description("검색어")),
                브랜드_명세()
            )
    }

    @Test
    fun 업체_목록_가져오기() {
        val 응답값 = 아디다스_업체_목록()
        val 검색어 = "adid"
        whenever(
            searchService.getPartners(검색어)
        ).thenReturn(응답값)

        val queryParams = LinkedMultiValueMap<String, String>()
        queryParams["searchTerms"] = 검색어

        mockMvc.GET("/search/partner", queryParams)
            .성공_검증(writeValueAsString(응답값))
            .DOCS_생성(
                "search/partner",
                listOf(parameterWithName("searchTerms").description("검색어")),
                업체_명세()
            )
    }
}