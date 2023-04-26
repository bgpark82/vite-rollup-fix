package com.musinsa.stat.databricks

import java.sql.Connection
import java.sql.DriverManager
import java.util.*


/**
 * Databricks 연결 후 값을 가져온다.
 */
class DatabricksResolver {
    fun getSQLWarehouseConnection(): Connection {
        val hostname = "musinsa-data-ws.cloud.databricks.com"
        val port = 443
        val httpPath = "/sql/1.0/warehouses/c0ee970a9c3ed562"

        // TODO 기본 카탈로그, 기본 스키마 지정
        // 어플리케이션에서 SQL조립 시, UseNativeQuery=1 설정
        val jdbcURL =StringBuilder("jdbc:databricks://").append(hostname).append(":").append(port).append("/default;transportMode=http;ssl=1;AuthMech=3;UID=token;UseNativeQuery=1;httpPath=").append(httpPath).toString()
//        val url = "jdbc:databricks://musinsa-data-ws.cloud.databricks.com:443/default;transportMode=http;ssl=1;httpPath=sql/protocolv1/o/3626753574208338/1011-094951-kt523si5;AuthMech=3;UID=token"
//        val jdbcURL = "jdbc:spark://musinsa-data-ws.cloud.databricks.com:443/default;transportMode=http;ssl=1;AuthMech=3;httpPath=/sql/1.0/warehouses/c0ee970a9c3ed562;"
        val p = Properties()
        p["PWD"] = "dapi1344090d9b6aa8888cd6a05e3534dd15"


        return DriverManager.getConnection(jdbcURL, p)
    }
}