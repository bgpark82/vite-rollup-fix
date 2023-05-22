package com.musinsa.stat.util

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.musinsa.stat.error.CommonError

/**
 * ObjectMapper
 */
object ObjectMapperFactory {
    private val mapper = jacksonObjectMapper()

    /**
     * String to JSON 변환
     *
     * @param jsonString 문자열
     * @param valueType 클래스타입
     *
     * @return valueType 객체
     */
    fun <T> readValue(jsonString: String, valueType: Class<T>): T {
        return try {
            mapper.readValue(jsonString, valueType)
        } catch (e: Exception) {
            CommonError.FAIL_STRING_TO_JSON.throwMe()
        }
    }
}