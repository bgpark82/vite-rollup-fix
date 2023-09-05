package com.musinsa.harrods.query.service

import com.musinsa.harrods.error.ErrorCode
import com.musinsa.harrods.utils.validator.TemplateUtils
import org.springframework.stereotype.Component

const val LIST_SEPARATOR = ","

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

    private fun convertToString(value: Any): String {
        when (value) {
            is String -> return TemplateUtils.wrapQuotations(value)
            is Number -> return value.toString()
            is List<*> -> return value.joinToString(LIST_SEPARATOR)
            else -> ErrorCode.UNSUPPORTED_PARAMETER_TYPE.throwMe()
        }
    }
}
