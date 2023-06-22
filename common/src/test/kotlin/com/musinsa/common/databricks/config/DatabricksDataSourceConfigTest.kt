package com.musinsa.common.databricks.config

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.jdbc.core.JdbcTemplate

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
internal class DatabricksDataSourceConfigTest {

    @Autowired
    @Qualifier("databricksJdbcTemplate")
    private lateinit var jdbcTemplate: JdbcTemplate

    @Test
    fun 데이터브릭스_JDBC_TEMPLATE_생성() {
        assertThat(jdbcTemplate).isInstanceOf(JdbcTemplate::class.java)
    }
}