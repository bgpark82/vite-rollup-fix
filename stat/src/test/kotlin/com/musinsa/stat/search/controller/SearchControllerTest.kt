package com.musinsa.stat.search.controller

import com.musinsa.stat.restdoc.DOCS_생성
import com.musinsa.stat.restdoc.GET
import com.musinsa.stat.restdoc.RestDocsControllerHelper
import com.musinsa.stat.restdoc.성공_검증
import com.musinsa.stat.search.dto.*
import com.musinsa.stat.search.service.SearchService
import com.musinsa.stat.util.ObjectMapperFactory.writeValueAsString
import org.junit.jupiter.api.Test
import org.mockito.kotlin.whenever
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.restdocs.request.RequestDocumentation.parameterWithName
import org.springframework.util.LinkedMultiValueMap

@WebMvcTest(controllers = [SearchController::class])
class SearchControllerTest : RestDocsControllerHelper() {
    @MockBean
    lateinit var searchService: SearchService

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

    @Test
    fun 태그_목록_가져오기() {
        val 응답값 = 태그_목록().map { it.tag }.toList()
        val 검색어 = "스트라이프"
        whenever(
            searchService.getTags(검색어)
        ).thenReturn(응답값)

        val queryParams = LinkedMultiValueMap<String, String>()
        queryParams["searchTerms"] = 검색어

        mockMvc.GET("/search/tag", queryParams)
            .성공_검증(writeValueAsString(응답값))
            .DOCS_생성(
                "search/tag",
                listOf(parameterWithName("searchTerms").description("검색어"))
            )
    }
}