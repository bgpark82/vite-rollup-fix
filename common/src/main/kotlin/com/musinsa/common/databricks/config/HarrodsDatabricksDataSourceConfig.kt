package com.musinsa.common.databricks.config

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.jdbc.core.JdbcTemplate
import javax.sql.DataSource

class HarrodsDatabricksDataSourceConfig: DatabricksDataSourceConfig() {

    /**
     * 해롯 클러스터
     * @see https://docs.databricks.com/integrations/jdbc-odbc-bi.html#configure-the-databricks-odbc-and-jdbc-drivers
     */
    override val URL = "jdbc:databricks://musinsa-data-ws.cloud.databricks.com;Port=443;TransportMode=HTTP;HTTPPath=/sql/1.0/warehouses/860d8ab96c5e34fd;PWD=dapiac03c1cb4ac710049a572888106b65e3;UseNativeQuery=1;EnableArrow=0;"

    @Bean
    fun harrodsDatabricksDataSource(): DataSource {
        return this.databricksDataSourceProperties()
            .initializeDataSourceBuilder()
            .build()
    }

    @Bean
    fun harrodsDatabricksJdbcTemplate(@Qualifier("harrodsDatabricksDataSource") dataSource: DataSource): JdbcTemplate {
        val jdbcTemplate = JdbcTemplate(dataSource)
        jdbcTemplate.queryTimeout = TIMEOUT
        return jdbcTemplate
    }
}
