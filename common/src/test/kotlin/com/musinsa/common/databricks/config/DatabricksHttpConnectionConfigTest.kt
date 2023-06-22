package com.musinsa.common.databricks.config

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

private class DatabricksHttpConnectionConfigTest {
    private val config = DatabricksHttpConnectionConfig()

    @Test
    fun 타임아웃은_10초_이다() {
        assertThat(config.TIMEOUT).isEqualTo(10)
    }

    @Test
    fun 무신사_데이터_도메인을_설정한다() {
        assertThat(config.MUSINSA_DATA_WS_DOMAIN).isEqualTo("https://musinsa-data-ws.cloud.databricks.com")
    }

    @Test
    fun 쿼리_가져오기_API_주소를_설정한다() {
        assertThat(config.RETRIEVE_API_PATH).isEqualTo("/api/2.0/preview/sql/queries")
    }

    @Test
    fun 인증토큰은_NULL_값이_아니다() {
        // 인증토큰의 경우 보안을 위해 실제값 테스트를 하지 않는다.
        assertThat(config.AUTHORIZATION_TOKEN).isNotBlank
    }
}