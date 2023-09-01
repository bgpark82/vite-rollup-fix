package com.musinsa.harrods.query.service

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
        val (template, params, ttl, interval, userId) = request
        val queries = mutableListOf<Query>()

        for (param in paramCombinator.generate(params)) {
            val query = queryGenerator.generate(template, param)

            queries.add(
                Query.create(
                    ttl = ttl,
                    queries = query,
                    cacheKey = keyGenerator.generate(query, param),
                    scheduleInterval = interval,
                    userId = userId
                )
            )
        }

        queryRepository.saveAll(queries)

        return queries
    }
}
