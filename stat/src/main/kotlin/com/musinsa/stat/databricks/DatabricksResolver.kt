package com.musinsa.stat.databricks

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import java.sql.Connection
import java.sql.DriverManager
import java.util.*


/**
 * Databricks 연결 후 값을 가져온다.
 * @see https://docs.databricks.com/integrations/jdbc-odbc-bi.html#configure-the-databricks-odbc-and-jdbc-drivers
 */
@ConstructorBinding
@ConfigurationProperties(prefix = "databricks")
class DatabricksResolver(
    internal val hostname: String,
    internal val port: String,
    internal val httpPath: String,
    internal val token: String,
    internal val jdbcConnection: String,
    internal val connCatalog: String,
    internal val connSchema: String
) {
    /**
     * 데이터브릭스 JDBC 커넥션을 생성 후 가져온다.
     *
     * @return JDBC 커넥션
     */
    fun getSQLWarehouseConnection(): Connection {
        val properties = Properties()
        properties["PWD"] = token

        // TODO 커넥션 생성 실패 throw ~ catch Exception 추가
        return DriverManager.getConnection(
            StringBuilder(jdbcConnection).append(hostname).append(":")
                .append(port).append(httpPath).append(connCatalog)
                .append(connSchema)
                .toString(), properties
        )
    }
}