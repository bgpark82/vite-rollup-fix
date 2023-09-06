package com.musinsa.harrods.query.service

import com.musinsa.harrods.error.ErrorCode
import org.springframework.stereotype.Component

const val SERVICE_NAME = "harrods"
const val COLON = ":"

@Component
class KeyGenerator {

    /**
     * 쿼리와 파라미터의 조합으로 캐시 키를 생성한다
     *
     * [service name]:[user Id]:[query hash]:[key:value]
     * harrods:peter.park:1802293498:name:peter:age:30
     *
     * @param query 생성된 쿼리
     * @param params 파라미터 조합
     * @param userId 등록자 아이디
     * @return 생성된 키
     */
    fun generate(query: String, params: Map<String, Any>, userId: String): String {
        val key = StringBuilder()
            .append(SERVICE_NAME)
            .append(COLON)
            .append(userId)
            .append(COLON)
            .append(query.hashCode())

        for ((name, value) in params) {
            key.append(COLON)
                .append(name)
                .append(COLON)
                .append(convertToString(value))
        }
        return key.toString()
    }

    /**
     * 각 타입을 문자로 변경
     * - 숫자 -> 숫자
     * - 문자 -> 문자
     * - 리스트 -> 문자,문자
     */
    private fun convertToString(value: Any): String {
        when (value) {
            is String -> return value
            is Number -> return value.toString()
            is List<*> -> return value.joinToString(separator = LIST_SEPARATOR)
            else -> return ErrorCode.UNSUPPORTED_PARAMETER_TYPE.throwMe()
        }
    }
}
