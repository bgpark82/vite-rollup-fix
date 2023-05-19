package com.musinsa.stat.error

import jakarta.validation.ConstraintViolationException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException

/**
 * 에러 노출 형식
 */
data class ErrorResponse private constructor(
    /**
     * 에러코드
     */
    val errorCode: String,

    /**
     * 예외종류
     */
    val exception: String,

    /**
     * 입력오류 필드
     * default: Empty
     */
    val invalidField: String = String(),

    /**
     * 입력오류 값
     * default: Empty
     */
    val invalidValue: String = String(),

    /**
     * 예외 메시지
     */
    val message: String
) {
    /**
     * 의도된 에러
     */
    constructor(exception: IntentionalRuntimeException) : this(
        errorCode = exception.error.toString(),
        exception = exception.javaClass.name,
        message = exception.error.message
    )

    /**
     * 유효하지 않은 요청값(DataSource)
     */
    constructor(exception: ConstraintViolationException) : this(
        errorCode = "CONSTRAINT_VIOLATED_VALUE",
        exception = exception.javaClass.name,
        invalidField = exception.constraintViolations.toList()[0].propertyPath.toString(),
        invalidValue = exception.constraintViolations.toList()[0].invalidValue.toString(),
        message = exception.constraintViolations.toList()[0].message
    )

    /**
     * 유효하지 않은 요청값(Method Call)
     */
    constructor(exception: MethodArgumentNotValidException) : this(
        errorCode = "INVALID_REQUEST_VALUE",
        exception = exception.javaClass.name,
        invalidField = exception.bindingResult.fieldError!!.field,
        invalidValue = exception.bindingResult.fieldError!!.rejectedValue.toString(),
        message = exception.message
    )

    /**
     * 유효하지 않은 요청값(Method Call)
     */
    constructor(exception: MethodArgumentTypeMismatchException) : this(
        errorCode = "INVALID_REQUEST_VALUE",
        exception = exception.javaClass.name,
        invalidField = exception.parameter.parameterName.toString(),
        invalidValue = exception.value.toString(),
        message = exception.message.toString()
    )

    /**
     * 모든 유형의 에러
     */
    constructor(exception: Exception) : this(
        errorCode = "UNKNOWN_ERROR",
        exception = exception.javaClass.name,
        message = exception.message.toString()
    )
}