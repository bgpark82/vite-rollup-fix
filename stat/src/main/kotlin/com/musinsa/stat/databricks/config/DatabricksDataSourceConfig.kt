package com.musinsa.stat.databricks.config

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties
import org.springframework.boot.context.properties.ConfigurationProperties
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

    /**
     * databricks connection config Bean 생성
     */
    @Bean
    @ConfigurationProperties("spring.datasource.databricks")
    fun databricksDataSourceProperties(): DataSourceProperties {
        return DataSourceProperties()
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