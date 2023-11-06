package com.musinsa.harrods.query.service

import com.musinsa.harrods.error.ErrorCode
import com.musinsa.harrods.query.domain.Query
import com.musinsa.harrods.query.domain.QueryRepository
import com.musinsa.harrods.template.dto.TemplateRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

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
     * @param template 검증된 템플릿
     * @param request 요청 파라미터
     *
     * @return 생성된 쿼리 리스트
     */
    @Transactional
    fun generate(
        template: String,
        request: TemplateRequest
    ): MutableList<Query> {
        val queries = mutableListOf<Query>()

        for (param in paramCombinator.generate(request.params)) {
            val query = queryGenerator.generate(template, param)

            queries.add(
                Query.create(
                    ttl = request.ttl,
                    queries = query,
                    cacheKey = keyGenerator.generate(query, request.userId),
                    scheduleInterval = request.interval,
                    userId = request.userId,
                    cacheKeySuffix = request.alias
                )
            )
        }

        validateKeyExist(queries)
        return queries
    }

    @Transactional(readOnly = true)
    fun findAll(): List<Query> {
        return queryRepository.findAll()
    }

    @Transactional(readOnly = true)
    fun findById(id: Long): Query {
        return queryRepository.findByIdOrNull(id) ?: ErrorCode.QUERY_NOT_FOUND.throwMe()
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
