package com.musinsa.harrods.query.service

import com.musinsa.harrods.query.dto.QueryRequest

const val OPEN_DOUBLE_CURLY_BRACE = "{{"
const val CLOSE_DOUBLE_CURLY_BRACE = "}}"
class QueryService {

    fun create(request: QueryRequest): String {
        val (template, params) = request
        var query = template

        for ((key, value) in params) {
            if (value is String) {
                query = query.replace(wrapCurlyBraces(key), value)
            }
        }

        return query
    }

    private fun wrapCurlyBraces(value: String): String {
        return OPEN_DOUBLE_CURLY_BRACE + value + CLOSE_DOUBLE_CURLY_BRACE
    }
}
