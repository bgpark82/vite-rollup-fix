package com.musinsa.stat.util

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

/**
 * ObjectMapper
 */
object ObjectMapperFactory {
    private val mapper = jacksonObjectMapper()

    /**
     * JSON String을 valueType 객체로 생성
     *
     * @param jsonString 문자열
     * @param valueType 클래스타입
     *
     * @return valueType 객체
     */
    fun <T> readValue(jsonString: String, valueType: Class<T>): T {
        try {
            return mapper.readValue(jsonString, valueType)
        } catch (e: Exception) {
            // TODO 예외처리 추가
            throw Exception(e)
        }
    }
}