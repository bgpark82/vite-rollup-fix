package com.musinsa.harrods.query.service

import com.musinsa.harrods.error.ErrorCode
import com.musinsa.harrods.query.domain.Query
import com.musinsa.harrods.query.domain.QueryRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QueryService(
    private val queryRepository: QueryRepository,
) {
    @Transactional(readOnly = true)
    fun findAll(): List<Query> {
        return queryRepository.findAll()
    }

    @Transactional(readOnly = true)
    fun findById(id: Long): Query {
        return queryRepository.findByIdOrNull(id) ?: ErrorCode.QUERY_NOT_FOUND.throwMe()
    }
}
