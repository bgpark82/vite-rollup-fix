package com.musinsa.harrods.query.service

import org.springframework.stereotype.Component

const val SERVICE_NAME = "harrods"
const val COLON = ":"

@Component
class KeyGenerator {

    /**
     * 쿼리와 파라미터의 조합으로 캐시 키를 생성한다
     * harrods:1802293498:name:peter:age:30
     *
     * @param query 생성된 쿼리
     * @param params 파라미터 조합
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
                .append(value)
        }
        return key.toString()
    }
}
