package com.musinsa.harrods.query.dto

data class QueryRequest(
    val template: String,
    val params: Map<String, Any>
)
