package com.musinsa.stat.error

import jakarta.validation.ConstraintViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException

/**
 * RestController 발생 예외 처리
 */
@RestControllerAdvice
class RestControllerAdviceExceptionHandler {
    /**
     * 의도적으로 발생된 예외 처리
     */
    @ExceptionHandler(IntentionalRuntimeException::class)
    fun handleIntentionalRuntimeException(exception: IntentionalRuntimeException): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(exception.error.httpStatus)
            .body(ErrorResponse(exception))
    }

    /**
     * 제약조건 위반 데이터
     */
    @ExceptionHandler(ConstraintViolationException::class)
    fun handleConstraintViolationException(exception: ConstraintViolationException): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ErrorResponse(exception))
    }

    /**
     * 컨트롤러를 통해 입력된 데이터가 유효하지 않을 시(invalid), 예외처리
     */
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(exception: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ErrorResponse(exception))
    }

    /**
     * 컨트롤러를 통해 입력된 데이터가 유효하지 않을 시(invalid), 예외처리
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    fun handleMethodArgumentTypeMismatchException(exception: MethodArgumentTypeMismatchException): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ErrorResponse(exception))
    }

    /**
     * 모든 예외 Catch
     */
    @ExceptionHandler(Exception::class)
    fun handleAllException(exception: Exception): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ErrorResponse(exception))
    }
}