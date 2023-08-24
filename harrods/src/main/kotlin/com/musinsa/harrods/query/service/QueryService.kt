package com.musinsa.harrods.query.service

import com.musinsa.harrods.query.dto.QueryRequest

const val OPEN_DOUBLE_CURLY_BRACE = "{{"
const val CLOSE_DOUBLE_CURLY_BRACE = "}}"

class QueryService(
    private val paramCombinator: ParamCombinator,
    private val keyCreator: KeyCreator
) {

    /**
     * 템플릿과 파라미터로 쿼리를 생성한다
     *
     * @param request 쿼리 생성 요청
     * @return 생성된 쿼리 리스트
     */
    fun create(request: QueryRequest): List<String> {
        val (template, params) = request
        val result = mutableListOf<String>()

        val combination = paramCombinator.generate(params)

        for (param in combination) {
            var query = template
            for ((key, value) in param) {
                if (value is String) {
                    query = query.replace(wrapCurlyBraces(key), value)
                }
            }
            val key = keyCreator.create(query, param)
            result.add(query)
        }

        return result
    }

    private fun wrapCurlyBraces(value: String): String {
        return OPEN_DOUBLE_CURLY_BRACE + value + CLOSE_DOUBLE_CURLY_BRACE
    }
}
