package com.musinsa.common.databricks.config

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.JdbcTemplate
import javax.sql.DataSource

/**
 * Databricks JDBC Template 생성
 *
 */
@Configuration
class DatabricksDataSourceConfig {
    @Suppress("PropertyName")
    val DRIVER_CLASS_NAME = "com.databricks.client.jdbc.Driver"

    // @see https://docs.databricks.com/integrations/jdbc-odbc-bi.html#configure-the-databricks-odbc-and-jdbc-drivers
    /**
     * URL, 브릭레인 클러스터
     */
    @Suppress("PropertyName")
    val URL =
        "jdbc:databricks://musinsa-data-ws.cloud.databricks.com;Port=443;TransportMode=HTTP;HTTPPath=/sql/1.0/warehouses/860d8ab96c5e34fd;PWD=dapiac03c1cb4ac710049a572888106b65e3;UseNativeQuery=1;EnableArrow=0;"

    /**
     * databricks connection config Bean 생성
     */
    @Bean
    fun databricksDataSourceProperties(): DataSourceProperties {
        val dataSourceProperties = DataSourceProperties()
        dataSourceProperties.driverClassName = DRIVER_CLASS_NAME
        dataSourceProperties.url = URL
        return dataSourceProperties
    }

    @Bean
    fun databricksDataSource(): DataSource {
        return databricksDataSourceProperties()
            .initializeDataSourceBuilder()
            .build()
    }

    /**
     * JDBC Template 생성
     */
    @Bean
    fun databricksJdbcTemplate(@Qualifier("databricksDataSource") databricksDataSource: DataSource): JdbcTemplate {
        val jdbcTemplate = JdbcTemplate(databricksDataSource)

        // 타임아웃 5분 설정
        jdbcTemplate.queryTimeout = 60 * 5
        return jdbcTemplate
    }
}