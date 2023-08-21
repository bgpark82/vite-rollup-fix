package com.musinsa.harrods.query.dto

data class QueryRequest(
    val template: String,
    val params: List<Map<String, Any>>
)
