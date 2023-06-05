package com.musinsa.stat.databricks.error

import com.musinsa.stat.error.CodeAwareException
import com.musinsa.stat.error.Error
import org.springframework.http.HttpStatus

enum class DatabricksError(
    override val httpStatus: HttpStatus,
    override val message: String
) : Error {
    FAIL_TO_RETRIEVE_DATABRICKS_QUERY(
        HttpStatus.BAD_REQUEST,
        "데이터브릭스 쿼리 가져오기 실패. 설정값 확인 필요"
    );

    override fun <T> throwMe(): T {
        throw CodeAwareException(this)
    }

    override fun create(): CodeAwareException {
        return CodeAwareException(this)
    }
}