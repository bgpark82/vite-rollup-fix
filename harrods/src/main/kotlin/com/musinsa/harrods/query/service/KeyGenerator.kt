package com.musinsa.harrods.query.service

import org.springframework.stereotype.Component

const val SERVICE_NAME = "harrods"
const val COLON = ":"

@Component
class KeyGenerator {

    /**
     * 쿼리와 아이디의 조합으로 캐시 키를 생성한다
     *
     * [service name]:[user Id]:[query hash]
     * harrods:peter.park:1802293498
     *
     * @param query 생성된 쿼리
     * @param userId 등록자 아이디
     *
     * @return 생성된 키
     */
    fun generate(query: String, userId: String): String {
        return StringBuilder()
            .append(SERVICE_NAME)
            .append(COLON)
            .append(userId)
            .append(COLON)
            .append(query.hashCode())
            .toString()
    }
}
