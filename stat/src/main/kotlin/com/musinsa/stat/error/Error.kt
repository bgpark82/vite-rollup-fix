package com.musinsa.stat.error

import org.springframework.http.HttpStatus

interface Error {
    /**
     * Http 상태코드
     */
    open fun getHttpStatus(): HttpStatus

    /**
     * 에러 메시지
     */
    open fun getMessage(): String

    /**
     * 예외 throw
     */
    open fun <T> throwMe(): T

    /**
     * 예외 생성
     */
    open fun create(): IntentionalRuntimeException
}
