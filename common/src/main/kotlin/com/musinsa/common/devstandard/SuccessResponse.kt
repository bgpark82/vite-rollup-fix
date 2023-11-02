package com.musinsa.common.devstandard

/**
 * 무신사 성공 응답 표준 Format
 */
data class SuccessResponse(
    val code: String = "OK",
    val message: String = "요청이 성공하였습니다.",

    /**
     * 실제 응답값이 담기는 필드
     */
    val data: Any
)
