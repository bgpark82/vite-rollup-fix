package com.musinsa.harrods.query.service

import com.musinsa.harrods.error.ErrorCode
import com.musinsa.harrods.query.domain.Query
import com.musinsa.harrods.query.domain.QueryRepository
import com.musinsa.harrods.query.dto.QueryRequest
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class QueryService(
    private val paramCombinator: ParamCombinator,
    private val keyGenerator: KeyGenerator,
    private val queryGenerator: QueryGenerator,
    private val queryRepository: QueryRepository,
    private val templateFormatter: TemplateFormatter
) {

    /**
     * 템플릿과 파라미터로 쿼리를 생성한다
     *
     * @param request 쿼리 생성 요청
     * @return 생성된 쿼리 리스트
     */
    @Transactional
    fun create(request: QueryRequest): List<Query> {
        val (template, params, ttl, interval, userId, alias) = request
        val queries = mutableListOf<Query>()

        val formattedTemplate = templateFormatter.format(template)

        for (param in paramCombinator.generate(params)) {
            val query = queryGenerator.generate(formattedTemplate, param)

            queries.add(
                Query.create(
                    ttl = ttl,
                    queries = query,
                    cacheKey = keyGenerator.generate(query, userId),
                    scheduleInterval = interval,
                    userId = userId,
                    cacheKeySuffix = alias
                )
            )
        }

        validateKeyExist(queries)

        return queryRepository.saveAll(queries)
    }

    /**
     * 기존에 등록된 쿼리 여부 확인
     */
    private fun validateKeyExist(queries: List<Query>) {
        val keys = queries.map(Query::cacheKey)

        if (queryRepository.existsByCacheKeyIn(keys)) {
            return ErrorCode.QUERY_ALREADY_EXIST.throwMe()
        }
    }
}
