package com.musinsa.harrods.query.controller

import com.musinsa.harrods.query.domain.Query
import com.musinsa.harrods.query.dto.QueryRequest
import com.musinsa.harrods.query.dto.QueryResponse
import com.musinsa.harrods.query.service.QueryService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class QueryController(
    private val queryService: QueryService
) {

    @PostMapping("/queries")
    fun create(@RequestBody request: QueryRequest): ResponseEntity<List<QueryResponse>> {
        val savedQueries = queryService.create(request)
        return ResponseEntity.ok(savedQueries.map(QueryResponse::of))
    }
}
