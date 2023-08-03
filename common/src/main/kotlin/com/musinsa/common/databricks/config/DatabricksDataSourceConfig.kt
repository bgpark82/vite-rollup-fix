package com.musinsa.common.databricks.config

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties

abstract class DatabricksDataSourceConfig {
    /**
     * JDBC 기본 드라이버
     */
    @Suppress("PrivatePropertyName")
    private val DRIVER_CLASS_NAME = "com.databricks.client.jdbc.Driver"

    @Suppress("PropertyName")
    val TIMEOUT = 60 * 5 // 타임아웃 5분

    // @see https://docs.databricks.com/integrations/jdbc-odbc-bi.html#configure-the-databricks-odbc-and-jdbc-drivers
    /**
     * 데이터브릭스 클러스터 주소
     */
    @Suppress("PropertyName")
    abstract val URL: String

    /**
     * databricks connection config Bean 생성
     */
    fun databricksDataSourceProperties(): DataSourceProperties {
        val dataSourceProperties = DataSourceProperties()
        dataSourceProperties.driverClassName = DRIVER_CLASS_NAME
        dataSourceProperties.url = URL
        return dataSourceProperties
    }
}
