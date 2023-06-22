package com.musinsa.common.databricks.service

import com.musinsa.common.util.HttpClient
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class DatabricksClientTest {
    private val httpClient: HttpClient = mock()

    @Test
    fun 데이터브릭스에_저장된_쿼리를_가져온다() {
        // given
        val databricksClient = DatabricksClient(httpClient)
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