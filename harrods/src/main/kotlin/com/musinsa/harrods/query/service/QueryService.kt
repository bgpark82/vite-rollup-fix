package com.musinsa.harrods.query.service

import com.musinsa.harrods.error.ErrorCode
import com.musinsa.harrods.query.domain.Query
import com.musinsa.harrods.query.domain.QueryRepository
import com.musinsa.harrods.query.dto.QueryRequest
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

const val OPEN_DOUBLE_CURLY_BRACE = "{{"
const val CLOSE_DOUBLE_CURLY_BRACE = "}}"

@Service
class QueryService(
    private val paramCombinator: ParamCombinator,
    private val keyGenerator: KeyGenerator,
    private val queryRepository: QueryRepository
) {

    /**
     * 템플릿과 파라미터로 쿼리를 생성한다
     *
     * @param request 쿼리 생성 요청
     * @return 생성된 쿼리 리스트
     */
    @Transactional
    fun create(request: QueryRequest): List<Query> {
        val (template, params) = request
        val queries = mutableListOf<Query>()

        for (param in paramCombinator.generate(params)) {
            var query = template
            for ((key, value) in param) {
                query = query.replace(wrapCurlyBraces(key), convertToString(value))
            }
            queries.add(
                Query.create(
                    ttl = request.ttl,
                    queries = query,
                    cacheKey = keyGenerator.generate(query, param),
                    scheduleInterval = request.interval
                )
            )
        }

        queryRepository.saveAll(queries)

        return queries
    }

    private fun wrapCurlyBraces(value: String): String {
        return OPEN_DOUBLE_CURLY_BRACE + value + CLOSE_DOUBLE_CURLY_BRACE
    }

    private fun convertToString(value: Any): String {
        when (value) {
            is String -> return value
            is Number -> return value.toString()
            else -> ErrorCode.INVALID_TYPE.throwMe()
        }
    }
}
