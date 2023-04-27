package com.musinsa.stat.databricks

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment


@SpringBootTest(webEnvironment = WebEnvironment.NONE)
internal class DatabricksResolverTest {
    @Autowired
    private lateinit var databricksResolver: DatabricksResolver

    @Test
    fun 데이터브릭스_JDBC연결_정보를_가져온다() {
        assertAll({
            assertThat(databricksResolver.hostname).isEqualTo("musinsa-data-ws.cloud.databricks.com")
            assertThat(databricksResolver.port).isEqualTo("443")
            assertThat(databricksResolver.httpPath).isEqualTo(";httpPath=/sql/1.0/warehouses/c0ee970a9c3ed562")
//            assertThat(databricksResolver.token).isEqualTo("")
            assertThat(databricksResolver.jdbcConnection).isEqualTo("jdbc:databricks://")
            assertThat(databricksResolver.connCatalog).isEqualTo(";ConnCatalog=datamart")
            assertThat(databricksResolver.connSchema).isEqualTo(";ConnSchema=datamart")
        })
    }

}