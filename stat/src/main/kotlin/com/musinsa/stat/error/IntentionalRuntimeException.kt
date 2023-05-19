package com.musinsa.stat.error

/**
 * 의도한 예외처리
 */
data class IntentionalRuntimeException(val error: Error) :
    RuntimeException(StringBuilder().append(error.getMessage()).toString())