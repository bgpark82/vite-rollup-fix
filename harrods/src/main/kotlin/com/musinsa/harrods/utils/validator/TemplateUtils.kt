package com.musinsa.harrods.utils.validator

const val OPEN_DOUBLE_CURLY_BRACE = "{{"
const val CLOSE_DOUBLE_CURLY_BRACE = "}}"
const val QUOTATION_MARK = "'"

object TemplateUtils {

    fun wrapCurlyBraces(value: String): String {
        return OPEN_DOUBLE_CURLY_BRACE + value + CLOSE_DOUBLE_CURLY_BRACE
    }

    fun wrapQuotations(value: String): String {
        return QUOTATION_MARK + value + QUOTATION_MARK
    }
}
