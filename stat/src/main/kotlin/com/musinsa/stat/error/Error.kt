package com.musinsa.stat.error

import org.springframework.http.HttpStatus

interface Error {
    /**
     * Http 상태코드
     */
    val httpStatus: HttpStatus

    /**
     * 에러 메시지
     */
    val message: String

    /**
     * 예외 throw
     */
    fun <T> throwMe(): T

    /**
     * 예외 생성
     */
    fun create(): CodeAwareException
}
