package com.musinsa.harrods.query.controller

import com.musinsa.harrods.query.dto.QueryRequest
import com.musinsa.harrods.query.dto.QueryResponse
import com.musinsa.harrods.query.service.QueryService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/queries")
class QueryController(
    private val queryService: QueryService
) {

    @PostMapping
    fun create(
        @Valid @RequestBody
        request: QueryRequest
    ): ResponseEntity<List<QueryResponse>> {
        val savedQueries = queryService.create(request)
        return ResponseEntity.ok(savedQueries.map(QueryResponse::of))
    }

    @GetMapping
    fun getAll(): ResponseEntity<List<QueryResponse>> {
        val queries = queryService.findAll()
        return ResponseEntity.ok(queries.map(QueryResponse::of))
    }

    @GetMapping("/{id}")
    fun getAll(@PathVariable id: Long): ResponseEntity<QueryResponse> {
        val query = queryService.findById(id)
        return ResponseEntity.ok(QueryResponse.of(query))
    }
}
