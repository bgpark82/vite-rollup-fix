package com.musinsa.harrods.query.controller

import com.musinsa.harrods.query.dto.QueryResponse
import com.musinsa.harrods.query.service.QueryService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/queries")
class QueryController(
    private val queryService: QueryService
) {

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
