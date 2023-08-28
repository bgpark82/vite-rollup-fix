package com.musinsa.harrods.query.service

import com.musinsa.harrods.error.ErrorCode
import org.springframework.stereotype.Component


const val OPEN_DOUBLE_CURLY_BRACE = "{{"
const val CLOSE_DOUBLE_CURLY_BRACE = "}}"

@Component
class QueryGenerator {

    /**
     * 쿼리 템플릿에서 {{ key }}을 value으로 변경한다
     *
     * @param template 쿼리 템플릿
     * @param param key와 value
     *
     * @return 템플릿과 파라미터로 조합된 완전한 쿼리
     */
    fun generate(template: String, param: Map<String, Any>): String {
        var query = template
        for ((key, value) in param) {
            query = query.replace(wrapCurlyBraces(key), convertToString(value))
        }
        return query
    }

    private fun wrapCurlyBraces(value: String): String {
        return OPEN_DOUBLE_CURLY_BRACE + value + CLOSE_DOUBLE_CURLY_BRACE
    }

    private fun convertToString(value: Any): String {
        when (value) {
            is String -> return value
            is Number -> return value.toString()
            else -> ErrorCode.INVALID_TYPE.throwMe()
        }
    }
}
