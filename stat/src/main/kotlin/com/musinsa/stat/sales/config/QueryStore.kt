package com.musinsa.stat.sales.config

import org.springframework.boot.context.properties.ConfigurationProperties


/**
 * 브릭레인 Query Id 저장소
 */
@ConfigurationProperties(prefix = "custom.databricks.stats-query-id")
data class QueryStore(val daily: String)