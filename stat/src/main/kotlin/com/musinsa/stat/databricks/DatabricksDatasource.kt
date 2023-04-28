package com.musinsa.stat.databricks

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.JdbcTemplate
import javax.sql.DataSource

@Configuration
class DatabricksDatasource {
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

    @Bean
    fun databricksJdbcTemplate(@Qualifier("databricksDataSource") databricksDataSource: DataSource): JdbcTemplate {
        return JdbcTemplate(databricksDataSource)
    }
}