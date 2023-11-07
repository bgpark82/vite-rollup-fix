package com.musinsa.harrods.template.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.musinsa.harrods.template.domain.Template
import com.musinsa.harrods.template.service.TemplateService
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.time.LocalDateTime

private val TEMPLATE_PATH = "/templates"

@WebMvcTest(value = [TemplateController::class])
class TemplateControllerTest @Autowired constructor(
    val mvc: MockMvc,
    val mapper: ObjectMapper,
    @MockBean val templateService: TemplateService
) {
    @Test
    fun `template은 빈 문자열이 아니다`() {
        val template이_빈문자열인_요청 = """
            {
                "template": "",
                "interval": "* * * * *",
                "userId": "peter.park",
                "alias": ["brand"]
            }
        """.trimIndent()

        mvc.perform(
            RestDocumentationRequestBuilders.post(TEMPLATE_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(template이_빈문자열인_요청)
        )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers.jsonPath("$.errorCode").value("INVALID_REQUEST_VALUE"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.invalidField").value("template"))
    }

    @Test
    fun `template은 null이 아니다`() {
        val template이_없는_요청 = """
            {
                "interval": "* * * * *",
                "userId": "peter.park",
                "alias": ["brand"]
            }
        """.trimIndent()

        mvc.perform(
            RestDocumentationRequestBuilders.post(TEMPLATE_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(template이_없는_요청)
        )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers.jsonPath("$.errorCode").value("INVALID_REQUEST_VALUE"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.invalidField").value("template"))
    }

    @Test
    fun `template의 select절에 *를 포함할 수 없다`() {
        val asterisk을_포함한_요청 = """
            {
                "template": "SELECT * FROM user",
                "interval": "* * * * *",
                "userId": "peter.park",
                "alias": ["brand"]
            }
        """.trimIndent()

        mvc.perform(
            RestDocumentationRequestBuilders.post(TEMPLATE_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asterisk을_포함한_요청)
        )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers.jsonPath("$.errorCode").value("INVALID_REQUEST_VALUE"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.invalidField").value("template"))
    }

    @Test
    fun `ttl은 기본값은 3일이다`() {
        val ttl이_없는_요청 = """
            {
              "template": "SELECT user.brand as brand FROM user",
              "interval": "* * * * *",
              "userId": "peter.park",
              "alias": ["brand"]
            }
        """.trimIndent()

        given(templateService.create(any()))
            .willReturn(Template(1L, "name", "peter.park", LocalDateTime.now(), LocalDateTime.now(), emptyList()))

        mvc.perform(
            RestDocumentationRequestBuilders.post(TEMPLATE_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(ttl이_없는_요청)
        )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    fun `ttl은 최소값이 1이상이다`() {
        val request = MockTemplateRequest(template = "SELECT user.brand as brand FROM user", params = mapOf(), ttl = -1L, interval = "* * * * *", userId = "peter.park", alias = listOf("brand"))

        mvc.perform(
            RestDocumentationRequestBuilders.post(TEMPLATE_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(request))
        )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers.jsonPath("$.errorCode").value("INVALID_REQUEST_VALUE"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.invalidField").value("ttl"))
    }

    @Test
    fun `ttl은 최대값은 9_223_370_000_000_000 이하이다`() {
        val ttlMax = 9_223_370_000_000_000L + 1L
        val request = MockTemplateRequest(template = "SELECT user.brand as brand FROM user", params = mapOf(), ttl = ttlMax, interval = "* * * * *", userId = "peter.park", alias = listOf("brand"))

        mvc.perform(
            RestDocumentationRequestBuilders.post(TEMPLATE_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(request))
        )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers.jsonPath("$.errorCode").value("INVALID_REQUEST_VALUE"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.invalidField").value("ttl"))
    }

    @Test
    fun `등록자 아이디는 필수값이다`() {
        val userId가_없는_요청 = """
            {
              "template": "SELECT user.brand as brand FROM user",
              "interval": "* * * * *",
              "alias": ["brand"]
            }
        """.trimIndent()

        mvc.perform(
            RestDocumentationRequestBuilders.post(TEMPLATE_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(userId가_없는_요청)
        )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers.jsonPath("$.errorCode").value("INVALID_REQUEST_VALUE"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.invalidField").value("userId"))
    }

    @Test
    fun `유효하지 않은 interval`() {
        val 유효하지_않은_interval = """
            {
              "template": "SELECT user.brand as brand FROM user",
              "interval": "* * * * * *",
              "userId": "peter.park",
              "alias": ["brand"]
            }
        """.trimIndent()

        mvc.perform(
            RestDocumentationRequestBuilders.post(TEMPLATE_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(유효하지_않은_interval)
        )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers.jsonPath("$.errorCode").value("INVALID_REQUEST_VALUE"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.invalidField").value("interval"))
    }

    @Test
    fun `interval은 필수값이다`() {
        val interval이_없는_요청 = """
            {
              "template": "SELECT user.brand as brand FROM user",
              "userId": "peter.park",
              "alias": ["brand"]
            }
        """.trimIndent()

        mvc.perform(
            RestDocumentationRequestBuilders.post(TEMPLATE_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(interval이_없는_요청)
        )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers.jsonPath("$.errorCode").value("INVALID_REQUEST_VALUE"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.invalidField").value("interval"))
    }

    @Test
    fun `alias는 필수값이다`() {
        val alias_없는_요청 = """
            {
              "template": "SELECT user.brand as brand FROM user",
              "interval": "* * * * *",
              "userId": "peter.park"
            }
        """.trimIndent()

        mvc.perform(
            RestDocumentationRequestBuilders.post(TEMPLATE_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(alias_없는_요청)
        )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers.jsonPath("$.errorCode").value("INVALID_REQUEST_VALUE"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.invalidField").value("alias"))
    }

    @Test
    fun `alias는 빈값이 아니다`() {
        val 빈_alias_요청 = """
            {
              "template": "SELECT user.brand as brand FROM user",
              "interval": "* * * * *",
              "userId": "peter.park",
              "alias": []
            }
        """.trimIndent()

        mvc.perform(
            RestDocumentationRequestBuilders.post(TEMPLATE_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(빈_alias_요청)
        )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers.jsonPath("$.errorCode").value("INVALID_REQUEST_VALUE"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.invalidField").value("alias"))
    }

    @Test
    fun `alias는 최대 5개가 가능하다`() {
        val alias_6개_요청 = """
            {
              "template": "SELECT user.brand as brand FROM user",
              "interval": "* * * * *",
              "userId": "peter.park",
              "alias": ["brand","age","name","gender","mobile","address"]
            }
        """.trimIndent()

        mvc.perform(
            RestDocumentationRequestBuilders.post(TEMPLATE_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(alias_6개_요청)
        )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers.jsonPath("$.errorCode").value("INVALID_REQUEST_VALUE"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.invalidField").value("alias"))
    }

    @Test
    fun `alias는 중복 불가능하다`() {
        val 중복_alias_요청 = """
            {
              "template": "SELECT user.brand as brand FROM user",
              "interval": "* * * * *",
              "userId": "peter.park",
              "alias": ["brand","brand"]
            }
        """.trimIndent()

        mvc.perform(
            RestDocumentationRequestBuilders.post(TEMPLATE_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(중복_alias_요청)
        )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers.jsonPath("$.errorCode").value("INVALID_REQUEST_VALUE"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.invalidField").value("alias"))
    }

    data class MockTemplateRequest(
        val template: String? = null,
        val params: Map<String, Any>? = null,
        val ttl: Long? = null,
        val interval: String? = null,
        val userId: String? = null,
        val alias: List<String>? = null
    )
}
