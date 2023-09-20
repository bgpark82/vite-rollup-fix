package com.musinsa.harrods.query.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.musinsa.harrods.query.service.QueryService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest
class QueryControllerTest @Autowired constructor(
    val mvc: MockMvc,
    val mapper: ObjectMapper,
    @MockBean val queryService: QueryService
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
            post("/queries")
                .contentType(MediaType.APPLICATION_JSON)
                .content(template이_빈문자열인_요청)
        )
            .andDo(print())
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.errorCode").value("INVALID_REQUEST_VALUE"))
            .andExpect(jsonPath("$.invalidField").value("template"))
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
            post("/queries")
                .contentType(MediaType.APPLICATION_JSON)
                .content(template이_없는_요청)
        )
            .andDo(print())
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.errorCode").value("INVALID_REQUEST_VALUE"))
            .andExpect(jsonPath("$.invalidField").value("template"))
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
            post("/queries")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asterisk을_포함한_요청)
        )
            .andDo(print())
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.errorCode").value("INVALID_REQUEST_VALUE"))
            .andExpect(jsonPath("$.invalidField").value("template"))
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

        mvc.perform(
            post("/queries")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ttl이_없는_요청)
        )
            .andDo(print())
            .andExpect(status().isOk)
    }

    @Test
    fun `ttl은 최소값이 1이상이다`() {
        val request = MockQueryRequest(template = "SELECT user.brand as brand FROM user", params = mapOf(), ttl = -1L, interval = "* * * * *", userId = "peter.park", alias = listOf("brand"))

        mvc.perform(
            post("/queries")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(request))
        )
            .andDo(print())
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.errorCode").value("INVALID_REQUEST_VALUE"))
            .andExpect(jsonPath("$.invalidField").value("ttl"))
    }

    @Test
    fun `ttl은 최대값은 9_223_370_000_000_000 이하이다`() {
        val ttlMax = 9_223_370_000_000_000L + 1L
        val request = MockQueryRequest(template = "SELECT user.brand as brand FROM user", params = mapOf(), ttl = ttlMax, interval = "* * * * *", userId = "peter.park", alias = listOf("brand"))

        mvc.perform(
            post("/queries")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(request))
        )
            .andDo(print())
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.errorCode").value("INVALID_REQUEST_VALUE"))
            .andExpect(jsonPath("$.invalidField").value("ttl"))
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
            post("/queries")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userId가_없는_요청)
        )
            .andDo(print())
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.errorCode").value("INVALID_REQUEST_VALUE"))
            .andExpect(jsonPath("$.invalidField").value("userId"))
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
            post("/queries")
                .contentType(MediaType.APPLICATION_JSON)
                .content(유효하지_않은_interval)
        )
            .andDo(print())
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.errorCode").value("INVALID_REQUEST_VALUE"))
            .andExpect(jsonPath("$.invalidField").value("interval"))
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
            post("/queries")
                .contentType(MediaType.APPLICATION_JSON)
                .content(interval이_없는_요청)
        )
            .andDo(print())
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.errorCode").value("INVALID_REQUEST_VALUE"))
            .andExpect(jsonPath("$.invalidField").value("interval"))
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
            post("/queries")
                .contentType(MediaType.APPLICATION_JSON)
                .content(alias_없는_요청)
        )
            .andDo(print())
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.errorCode").value("INVALID_REQUEST_VALUE"))
            .andExpect(jsonPath("$.invalidField").value("alias"))
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
            post("/queries")
                .contentType(MediaType.APPLICATION_JSON)
                .content(빈_alias_요청)
        )
            .andDo(print())
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.errorCode").value("INVALID_REQUEST_VALUE"))
            .andExpect(jsonPath("$.invalidField").value("alias"))
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
            post("/queries")
                .contentType(MediaType.APPLICATION_JSON)
                .content(alias_6개_요청)
        )
            .andDo(print())
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.errorCode").value("INVALID_REQUEST_VALUE"))
            .andExpect(jsonPath("$.invalidField").value("alias"))
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
            post("/queries")
                .contentType(MediaType.APPLICATION_JSON)
                .content(중복_alias_요청)
        )
            .andDo(print())
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.errorCode").value("INVALID_REQUEST_VALUE"))
            .andExpect(jsonPath("$.invalidField").value("alias"))
    }

    data class MockQueryRequest(
        val template: String? = null,
        val params: Map<String, Any>? = null,
        val ttl: Long? = null,
        val interval: String? = null,
        val userId: String? = null,
        val alias: List<String>? = null
    )
}
