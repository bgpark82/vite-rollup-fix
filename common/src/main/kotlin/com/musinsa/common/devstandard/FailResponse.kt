package com.musinsa.common.devstandard

/**
 * 무신사 실패 응답 표준 Format
 */
class FailResponse(val error: Error) {
    data class Error(
        /**
         * 에러 코드값
         */
        val code: String,

        /**
         * 에러 상세 메시지
         */
        val message: String,

        /**
         * 사용자에게 노출할 에러 내용
         */
        val usermessage: String
    )
}


