package com.musinsa.stat.search.controller

import com.musinsa.stat.search.dto.Brand
import com.musinsa.stat.search.dto.Partner
import com.musinsa.stat.search.service.SearchService
import jakarta.validation.constraints.NotBlank
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/search")
@Validated
class SearchController(private val searchService: SearchService) {
    /**
     * 브랜드 리스트를 가져온다.
     *
     * @param searchTerms 검색어
     *
     * @return 조회된 브랜드 리스트
     */
    @GetMapping("/brand")
    fun getBrands(@NotBlank searchTerms: String): List<Brand> {
        return searchService.getBrands(searchTerms)
    }

    /**
     * 업체 리스트를 가져온다.
     *
     * @param searchTerms 검색어
     *
     * @return 조회된 업체 리스트
     */
    @GetMapping("/partner")
    fun getPartners(@NotBlank searchTerms: String): List<Partner> {
        return searchService.getPartners(searchTerms)
    }
}