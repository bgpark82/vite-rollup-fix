package com.musinsa.harrods.query.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.musinsa.common.redis.config.LocalRedisServer
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
    @MockBean val queryService: QueryService,
    @MockBean val redisServer: LocalRedisServer
) {
    @Test
    fun `template은 빈 문자열이 아니다`() {
        val request = MockQueryRequest(template = "", params = mapOf(), ttl = 10L, interval = "* * * * *")

        mvc.perform(
            post("/queries")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(request))
        )
            .andDo(print())
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.errorCode").value("INVALID_REQUEST_VALUE"))
            .andExpect(jsonPath("$.invalidField").value("template"))
    }

    @Test
    fun `template은 null이 아니다`() {
        val request = MockQueryRequest(template = null, params = mapOf(), ttl = 10L, interval = "* * * * *")

        mvc.perform(
            post("/queries")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(request))
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
              "template": "SELECT * FROM user",
              "interval": "* * * *"
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
        val request = MockQueryRequest(template = "SELECT * FROM user", params = mapOf(), ttl = -1L, interval = "* * * * *")

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
        val request = MockQueryRequest(template = "SELECT * FROM user", params = mapOf(), ttl = ttlMax, interval = "* * * * *")

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

    data class MockQueryRequest(
        val template: String? = null,
        val params: Map<String, Any>? = null,
        val ttl: Long? = null,
        val interval: String? = null
    )
}
