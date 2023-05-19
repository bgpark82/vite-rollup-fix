package com.musinsa.stat.error

/**
 * 의도한 예외처리
 */
class CodeAwareException(error: Error) :
    RuntimeException(StringBuilder().append(error.message).toString())