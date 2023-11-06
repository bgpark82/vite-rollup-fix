package com.musinsa.harrods.query.service

import com.musinsa.harrods.error.ErrorCode
import com.musinsa.harrods.query.domain.Query
import com.musinsa.harrods.query.domain.QueryRepository
import com.musinsa.harrods.query.dto.QueryRequest
import com.musinsa.harrods.template.domain.Template
import com.musinsa.harrods.template.domain.TemplateRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QueryService(
    private val paramCombinator: ParamCombinator,
    private val keyGenerator: KeyGenerator,
    private val queryGenerator: QueryGenerator,
    private val queryRepository: QueryRepository,
    private val templateFormatter: TemplateFormatter,
    private val templateRepository: TemplateRepository
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

        val savedTemplate = templateRepository.save(
            Template.create(
                // TODO: Template 도메인으로 이동 및 템플릿 이름 추가
                name = "브랜드별 통계",
                userId = userId,
                queries = queries
            )
        )

        return savedTemplate.queries
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
