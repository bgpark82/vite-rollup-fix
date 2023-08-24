package com.musinsa.harrods.query.service

const val SERVICE_NAME = "harrods"
const val COLON = ":"
class KeyCreator {

    /**
     * 쿼리와 파라미터의 조합으로 캐시 키를 생성한다
     * harrods:1802293498:name:peter:age:30
     *
     * @param query 생성된 쿼리
     * @param params 파라미터 조합
     * @return 생성된 키
     */
    fun create(query: String, params: Map<String, Any>): String {
        val key = StringBuilder()
            .append(SERVICE_NAME)
            .append(COLON)
            .append(query.hashCode()) // TODO: 사용자 아이디 혹은 부서명으로 구분

        for ((name, value) in params) {
            key.append(COLON)
                .append(name)
                .append(COLON)
                .append(value)
        }
        return key.toString()
    }
}
