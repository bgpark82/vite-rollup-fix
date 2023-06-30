package com.musinsa.stat.search.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "custom.databricks.search-query-id")
data class SearchQueryStore(
    val brand: String,
    val partner: String,
    val tag: String
)
