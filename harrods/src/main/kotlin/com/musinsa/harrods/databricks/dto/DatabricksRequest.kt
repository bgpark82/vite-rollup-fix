package com.musinsa.harrods.databricks.dto

import java.io.Serializable

data class DatabricksRequest(
    val query: String
): Serializable
