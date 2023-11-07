package com.musinsa.harrods.template.service

import com.musinsa.harrods.error.ErrorCode
import com.musinsa.harrods.utils.validator.TemplateUtils
import org.springframework.stereotype.Component
import java.lang.StringBuilder

const val TEXT_BETWEEN_CURLY_BRACES_PATTERN = "\\{\\{\\s*(.*?)\\s*\\}\\}"

@Component
class TemplateFormatter {

    /**
     * 템플릿을 포메팅한다
     *
     * @param template 템플릿
     * @return 포멧 완료된 템플릿
     */
    fun format(template: String): String {
        val sb = StringBuilder(template)

        validateCommentExist(sb)
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

    /**
     * 템플릿에 코멘트 존재 여부 확인
     */
    private fun validateCommentExist(template: StringBuilder) {
        if (template.contains("--")) {
            return ErrorCode.COMMENT_NOT_ALLOWED.throwMe()
        }
    }
}
