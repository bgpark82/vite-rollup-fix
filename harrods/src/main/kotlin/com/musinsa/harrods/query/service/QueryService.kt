package com.musinsa.harrods.query.service

import com.musinsa.harrods.query.dto.QueryRequest

const val OPEN_DOUBLE_CURLY_BRACE = "{{"
const val CLOSE_DOUBLE_CURLY_BRACE = "}}"

class QueryService(
    private val paramCombinator: ParamCombinator
) {

    fun create(request: QueryRequest): List<String> {
        val (template, params) = request
        val result = mutableListOf<String>()

        val combination = paramCombinator.generate(params)

        for (map in combination) {
            var query = template
            for ((key, value) in map) {
                if (value is String) {
                    query = query.replace(wrapCurlyBraces(key), value)
                }
            }
            result.add(query)
        }

        return result
    }

    private fun wrapCurlyBraces(value: String): String {
        return OPEN_DOUBLE_CURLY_BRACE + value + CLOSE_DOUBLE_CURLY_BRACE
    }
}
