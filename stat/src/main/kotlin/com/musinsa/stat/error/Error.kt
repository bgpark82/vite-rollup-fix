package com.musinsa.stat.error

import org.springframework.http.HttpStatus

interface Error {
    /**
     * Http 상태코드
     *
     * @return
     */
    val httpStatus: HttpStatus

    /**
     * 에러 메시지
     *
     * @return
     */
    val message: String

    /**
     * 예외 throw
     *
     * @return RuntimeException.class
     */
    fun <T> throwMe(): T

    /**
     * 예외 생성
     *
     * @return
     */
    fun create(): CodeAwareException
}
