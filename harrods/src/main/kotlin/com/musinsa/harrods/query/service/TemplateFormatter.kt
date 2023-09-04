package com.musinsa.harrods.query.service

import com.musinsa.harrods.utils.validator.TemplateUtils
import java.lang.StringBuilder

const val TEXT_BETWEEN_CURLY_BRACES_PATTERN = "\\{\\{\\s*(.*?)\\s*\\}\\}"

class TemplateFormatter {

    /**
     * 템플릿을 포메팅한다
     *
     * @param template 템플릿
     * @return 포멧 완료된 템플릿
     */
    fun format(template: String): String {
        val sb = StringBuilder(template)

        removeSpaceInCurlyBraces(sb)

        return sb.toString()
    }

    /**
     * 중괄호 사이에 빈 공간을 제거한다
     */
    private fun removeSpaceInCurlyBraces(template: StringBuilder): StringBuilder {
        val regex = Regex(TEXT_BETWEEN_CURLY_BRACES_PATTERN)
        val matchResult = regex.findAll(template)
        for (match in matchResult) {
            template.replace(match.range.first, match.range.last + 1, TemplateUtils.wrapCurlyBraces(match.groupValues[1]))
        }
        return template
    }
}
