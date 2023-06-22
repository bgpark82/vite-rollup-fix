package com.musinsa.stat.databricks.error

import com.musinsa.common.error.CodeAwareException
import com.musinsa.common.error.Error
import org.springframework.http.HttpStatus

enum class DatabricksError(
    override val httpStatus: HttpStatus,
    override val message: String
) : Error {
    FAIL_RETRIEVE_DATABRICKS_QUERY_RESULT(
        HttpStatus.BAD_REQUEST,
        "데이터브릭스 쿼리 실행 실패. 검색 파라미터 확인 필요. 혹은 데이터브릭스 설정값 누락"
    );

    override fun <T> throwMe(): T {
        throw CodeAwareException(this)
    }

    override fun create(): CodeAwareException {
        return CodeAwareException(this)
    }
}