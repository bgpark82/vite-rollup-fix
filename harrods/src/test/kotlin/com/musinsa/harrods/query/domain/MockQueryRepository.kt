package com.musinsa.harrods.query.domain

import org.springframework.data.domain.Example
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.repository.query.FluentQuery
import java.util.*
import java.util.function.Function

class MockQueryRepository : QueryRepository {

    val db = mutableMapOf<Long, Query>()
    var id = 0L

    override fun <S : Query?> save(entity: S & Any): S & Any {
        entity.id = ++id
        db.put(id, entity)
        return entity
    }

    override fun <S : Query?> saveAll(entities: MutableIterable<S>): MutableList<S> {
        val list = mutableListOf<S>()
        for (e in entities) {
            list.add(save(e!!))
        }
        return list
    }

    override fun findById(id: Long): Optional<Query> {
        TODO("Not yet implemented")
    }

    override fun existsById(id: Long): Boolean {
        TODO("Not yet implemented")
    }

    override fun <S : Query?> findAll(example: Example<S>): MutableList<S> {
        TODO("Not yet implemented")
    }

    override fun <S : Query?> findAll(
        example: Example<S>,
        sort: Sort
    ): MutableList<S> {
        TODO("Not yet implemented")
    }

    override fun findAll(): MutableList<Query> {
        TODO("Not yet implemented")
    }

    override fun findAll(sort: Sort): MutableList<Query> {
        TODO("Not yet implemented")
    }

    override fun findAll(pageable: Pageable): Page<Query> {
        TODO("Not yet implemented")
    }

    override fun <S : Query?> findAll(
        example: Example<S>,
        pageable: Pageable
    ): Page<S> {
        TODO("Not yet implemented")
    }

    override fun findAllById(ids: MutableIterable<Long>): MutableList<Query> {
        TODO("Not yet implemented")
    }

    override fun count(): Long {
        TODO("Not yet implemented")
    }

    override fun <S : Query?> count(example: Example<S>): Long {
        TODO("Not yet implemented")
    }

    override fun deleteById(id: Long) {
        TODO("Not yet implemented")
    }

    override fun delete(entity: Query) {
        TODO("Not yet implemented")
    }

    override fun deleteAllById(ids: MutableIterable<Long>) {
        TODO("Not yet implemented")
    }

    override fun deleteAll(entities: MutableIterable<Query>) {
        TODO("Not yet implemented")
    }

    override fun deleteAll() {
        TODO("Not yet implemented")
    }

    override fun <S : Query?> findOne(example: Example<S>): Optional<S> {
        TODO("Not yet implemented")
    }

    override fun <S : Query?> exists(example: Example<S>): Boolean {
        TODO("Not yet implemented")
    }

    override fun <S : Query?, R : Any?> findBy(
        example: Example<S>,
        queryFunction: Function<FluentQuery.FetchableFluentQuery<S>, R>
    ): R & Any {
        TODO("Not yet implemented")
    }

    override fun flush() {
        TODO("Not yet implemented")
    }

    override fun <S : Query?> saveAndFlush(entity: S & Any): S & Any {
        TODO("Not yet implemented")
    }

    override fun <S : Query?> saveAllAndFlush(entities: MutableIterable<S>): MutableList<S> {
        TODO("Not yet implemented")
    }

    override fun deleteAllInBatch(entities: MutableIterable<Query>) {
        TODO("Not yet implemented")
    }

    override fun deleteAllInBatch() {
        TODO("Not yet implemented")
    }

    override fun deleteAllByIdInBatch(ids: MutableIterable<Long>) {
        TODO("Not yet implemented")
    }

    override fun getOne(id: Long): Query {
        TODO("Not yet implemented")
    }

    override fun getById(id: Long): Query {
        TODO("Not yet implemented")
    }

    override fun getReferenceById(id: Long): Query {
        TODO("Not yet implemented")
    }
}
