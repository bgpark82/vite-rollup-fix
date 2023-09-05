package com.musinsa.harrods.query.service

import com.musinsa.harrods.error.ErrorCode
import com.musinsa.harrods.utils.validator.TemplateUtils
import org.springframework.stereotype.Component

const val LIST_SEPARATOR = ","
const val EMPTY_STRING = ""

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
            query = query.replace(TemplateUtils.wrapCurlyBraces(key), convertToString(value))
        }
        return query
    }

    /**
     * 각 타입을 문자로 변경
     * - 숫자 -> 숫자
     * - 문자 -> '문자'
     * - 숫자 리스트 -> 숫자,숫자
     * - 문자 리스트 -> '문자','문자'
     */
    private fun convertToString(value: Any): String {
        when (value) {
            is String -> return TemplateUtils.wrapQuotations(value)
            is Number -> return value.toString()
            is List<*> -> return convertListToString(value)
            else -> ErrorCode.UNSUPPORTED_PARAMETER_TYPE.throwMe()
        }
    }

    /**
     * 리스트 타입을 문자로 변경
     */
    private fun convertListToString(value: List<*>): String {
        return value.map { v: Any? -> convertToString(v ?: EMPTY_STRING) }
            .joinToString(LIST_SEPARATOR)
    }
}
