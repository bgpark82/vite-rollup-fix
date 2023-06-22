package com.musinsa.common.databricks.service

import com.musinsa.common.databricks.config.DatabricksHttpConnectionConfig
import com.musinsa.common.databricks.error.DatabricksError
import com.musinsa.common.error.CodeAwareException
import com.musinsa.common.util.HttpClient
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class DatabricksClientTest {
    private val httpClient: HttpClient = mock()
    private val config = DatabricksHttpConnectionConfig(
        mapOf(
            "timeout" to "10",
            "musinsa-data-ws-domain" to "https://musinsa-data-ws.cloud.databricks.com",
            "retrieve-api-path" to "/api/2.0/preview/sql/queries",
            "authorization-token" to "token"
        )
    )

    @Test
    fun 데이터브릭스에_저장된_쿼리를_가져온다() {
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

    @Test
    fun 설정값을_주입받지_못하면_에러_발생() {
        // given
        val 누락된_설정값 = DatabricksHttpConnectionConfig(mapOf())
        val databricksClient = DatabricksClient(누락된_설정값, httpClient)

        // when
        val 에러 = assertThrows<CodeAwareException> {
            databricksClient.getDatabricksQuery("Test queryId")
        }

        // then
        assertThat(에러.error).isEqualTo(DatabricksError.FAIL_RETRIEVE_DATABRICKS_QUERY_RESULT)
    }
}