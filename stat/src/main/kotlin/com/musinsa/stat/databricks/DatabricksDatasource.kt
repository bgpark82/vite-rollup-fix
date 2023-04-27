package com.musinsa.stat.databricks

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource

@Configuration
class DatabricksDatasource {
    @ConfigurationProperties("spring.datasource.databricks")
    fun databricksDataSourceProperties(): DataSourceProperties {
        return DataSourceProperties()
    }

    @Bean(name = ["databricksDataSource"])
    fun databricksDataSource(): DataSource {
        return databricksDataSourceProperties()
            .initializeDataSourceBuilder()
            .build()
    }
}