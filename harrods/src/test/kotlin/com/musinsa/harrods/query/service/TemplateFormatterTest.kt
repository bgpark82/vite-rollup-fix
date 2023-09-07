package com.musinsa.harrods.query.service

import com.musinsa.common.error.CodeAwareException
import com.musinsa.harrods.error.ErrorCode
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class TemplateFormatterTest {

    private val templateFormatter = TemplateFormatter()

    @Test
    fun `중괄호 사이의 공간을 제거한다`() {
        val query = "SELECT * FROM user WHERE name = {{ name}} and age = {{age }} and gender = {{ gender }} and date = {{date   }}"

        val template = templateFormatter.format(query)

        assertThat(template).isEqualTo("SELECT * FROM user WHERE name = {{name}} and age = {{age}} and gender = {{gender}} and date = {{date}}")
    }

    @Test
    fun `중괄호가 없으면 그대로 반환한다`() {
        val query = "SELECT * FROM user"

        val template = templateFormatter.format(query)

        assertThat(template).isEqualTo("SELECT * FROM user")
    }

    @Test
    fun `템플릿에 코멘트는 허용하지 않는다`() {
        val query = "SELECT * FROM user -- 사용자 테이블"

        val result = assertThrows<CodeAwareException> {
            templateFormatter.format(query)
        }

        assertThat(result.error).isEqualTo(ErrorCode.COMMENT_NOT_ALLOWED)
    }
}
