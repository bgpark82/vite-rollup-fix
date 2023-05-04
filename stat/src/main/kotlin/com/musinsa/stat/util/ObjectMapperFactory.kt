package com.musinsa.stat.util

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

/**
 * ObjectMapper
 *
 * @see "https://www.baeldung.com/kotlin/jackson-kotlin"
 */
object ObjectMapperFactory {
    private val objectMapper = ObjectMapper()
    private val mapper = jacksonObjectMapper()

    fun <T> readValue(str: String, valueType: Class<T>): T {
        try {
            return objectMapper.readValue(str, valueType)
        } catch (e: Exception) {
            // TODO 예외처리 추가
            throw Exception(e)
        }
    }

    fun <T> readValue(jsonString: String, valueType: TypeReference<T>): T {
        try {
            return mapper.readValue(jsonString, valueType)
        } catch (e: Exception) {
            // TODO 예외처리 추가
            throw Exception(e)
        }
    }
}