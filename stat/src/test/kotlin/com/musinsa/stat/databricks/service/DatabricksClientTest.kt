package com.musinsa.stat.databricks.service

import com.musinsa.stat.databricks.config.DatabricksHttpConnectionConfig
import com.musinsa.stat.util.HttpClient
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class DatabricksClientTest {
    private val httpClient: HttpClient = mock()
    private val config = DatabricksHttpConnectionConfig()

    @Test
    fun test() {
        // given
        val databricksClient = DatabricksClient(config, httpClient)
        whenever(
            httpClient.getHttpResponse(
                anyOrNull(), anyOrNull(),
                anyOrNull()
            )
        ).thenReturn("{\"query_draft\": null, \"query\": \"쿼리 결과값\"}")

        // when, then
        assertThat(databricksClient.getDatabricksQuery("Test queryId")).isEqualTo(
            "쿼리 결과값"
        )
    }
}