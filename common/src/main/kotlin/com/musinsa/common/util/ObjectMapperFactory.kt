package com.musinsa.common.util

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.musinsa.common.error.CommonError

/**
 * ObjectMapper
 */
object ObjectMapperFactory {
    private val mapper = jacksonObjectMapper()
    val typeRefListMapAny =
        object : TypeReference<List<Map<String, Any>>>() {}


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
            CommonError.FAIL_CONVERT_STRING_TO_JSON.throwMe()
        }
    }

    /**
     * String to JSON List 변환
     *
     * @param jsonString 문자열
     * @param valueType 클래스타입
     *
     * @return valueType 객체
     */
    fun <T> readValues(
        jsonString: String,
        valueType: Class<Array<T>>
    ): List<T> {
        return try {
            mapper.readValue(jsonString, valueType).toList()
        } catch (e: Exception) {
            CommonError.FAIL_CONVERT_STRING_TO_JSON.throwMe()
        }
    }

    /**
     * String to JSON List 변환
     *
     * @param jsonString 문자열
     * @param typeRef TypeReference
     *
     * @return List 객체
     */
    fun <T> readValues(
        jsonString: String,
        typeRef: TypeReference<List<T>>
    ): List<T> {
        return try {
            mapper.readValue(jsonString, typeRef)
        } catch (e: Exception) {
            CommonError.FAIL_CONVERT_STRING_TO_JSON.throwMe()
        }
    }

    /**
     * JSON to String 변환
     *
     * @param o 객체
     *
     * @return String 변환된 객체
     */
    fun writeValueAsString(o: Any): String {
        return try {
            mapper.writeValueAsString(o)
        } catch (e: Exception) {
            CommonError.FAIL_CONVERT_JSON_TO_STRING.throwMe()
        }
    }
}
