package com.musinsa.harrods.query.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.musinsa.common.redis.config.LocalRedisServer
import com.musinsa.harrods.query.service.QueryService
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.NullAndEmptySource
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
    @ParameterizedTest
    @NullAndEmptySource
    fun `template은 null이나 빈 문자열이 아니다`(template: String?) {
        val request = MockQueryRequest(template = template, params = mapOf(), ttl = 10, interval = "* * * * *")

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
