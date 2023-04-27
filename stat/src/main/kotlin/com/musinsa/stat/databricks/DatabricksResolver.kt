package com.musinsa.stat.databricks

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import java.sql.Connection
import java.sql.DriverManager
import java.util.*


/**
 * Databricks 연결 후 값을 가져온다.
 */
@ConstructorBinding
@ConfigurationProperties(prefix = "databricks")
class DatabricksResolver(
    private val hostname: String, private val port:
    String, private val httpPath: String
) {
    fun getSQLWarehouseConnection(): Connection {
        // TODO 기본 카탈로그, 기본 스키마 지정
        // 어플리케이션에서 SQL조립 시, UseNativeQuery=1 설정
        val jdbcURL =
            StringBuilder("jdbc:databricks://").append(hostname).append(":")
                .append(port)
                .append("/default;transportMode=http;ssl=1;AuthMech=3;UID=token;UseNativeQuery=1;httpPath=")
                .append(httpPath).toString()
        val p = Properties()
        p["PWD"] = "dapi1344090d9b6aa8888cd6a05e3534dd15"


        return DriverManager.getConnection(jdbcURL, p)
    }
}