package com.musinsa.common.error

/**
 * 의도한 예외처리
 */
data class CodeAwareException(val error: Error) :
    RuntimeException(StringBuilder().append(error.message).toString())