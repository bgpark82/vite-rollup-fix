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
        val request = MockQueryRequest(template = "", params = mapOf(), ttl = 10, interval = "* * * * *")

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
        val request = MockQueryRequest(template = null, params = mapOf(), ttl = 10, interval = "* * * * *")

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

    data class MockQueryRequest(
        val template: String? = null,
        val params: Map<String, Any>? = null,
        val ttl: Int? = null,
        val interval: String? = null
    )
}
