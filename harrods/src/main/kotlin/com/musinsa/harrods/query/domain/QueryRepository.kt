package com.musinsa.harrods.query.domain

import org.springframework.data.jpa.repository.JpaRepository

interface QueryRepository : JpaRepository<Query, Long> {

    fun existsByCacheKeyIn(keys: List<String>): Boolean
}
