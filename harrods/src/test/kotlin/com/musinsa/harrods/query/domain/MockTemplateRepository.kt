package com.musinsa.harrods.query.domain

import com.musinsa.harrods.template.domain.Template
import com.musinsa.harrods.template.domain.TemplateRepository
import org.springframework.data.domain.Example
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.repository.query.FluentQuery
import java.util.*
import java.util.function.Function

class MockTemplateRepository : TemplateRepository {

    val db = mutableMapOf<Long, Template>()
    var id = 0L

    override fun <S : Template?> save(entity: S & Any): S & Any {
        entity.id = ++id
        db.put(id, entity)
        return entity
    }

    override fun <S : Template?> saveAll(entities: MutableIterable<S>): MutableList<S> {
        TODO("Not yet implemented")
    }

    override fun findById(id: Long): Optional<Template> {
        TODO("Not yet implemented")
    }

    override fun existsById(id: Long): Boolean {
        TODO("Not yet implemented")
    }

    override fun <S : Template?> findAll(example: Example<S>): MutableList<S> {
        TODO("Not yet implemented")
    }

    override fun <S : Template?> findAll(
        example: Example<S>,
        sort: Sort
    ): MutableList<S> {
        TODO("Not yet implemented")
    }

    override fun findAll(): MutableList<Template> {
        return db.values.toMutableList()
    }

    override fun findAll(sort: Sort): MutableList<Template> {
        TODO("Not yet implemented")
    }

    override fun findAll(pageable: Pageable): Page<Template> {
        TODO("Not yet implemented")
    }

    override fun <S : Template?> findAll(
        example: Example<S>,
        pageable: Pageable
    ): Page<S> {
        TODO("Not yet implemented")
    }

    override fun findAllById(ids: MutableIterable<Long>): MutableList<Template> {
        TODO("Not yet implemented")
    }

    override fun count(): Long {
        TODO("Not yet implemented")
    }

    override fun <S : Template?> count(example: Example<S>): Long {
        TODO("Not yet implemented")
    }

    override fun deleteById(id: Long) {
        TODO("Not yet implemented")
    }

    override fun delete(entity: Template) {
        TODO("Not yet implemented")
    }

    override fun deleteAllById(ids: MutableIterable<Long>) {
        TODO("Not yet implemented")
    }

    override fun deleteAll(entities: MutableIterable<Template>) {
        TODO("Not yet implemented")
    }

    override fun deleteAll() {
        TODO("Not yet implemented")
    }

    override fun <S : Template?> findOne(example: Example<S>): Optional<S> {
        TODO("Not yet implemented")
    }

    override fun <S : Template?> exists(example: Example<S>): Boolean {
        TODO("Not yet implemented")
    }

    override fun <S : Template?, R : Any?> findBy(
        example: Example<S>,
        queryFunction: Function<FluentQuery.FetchableFluentQuery<S>, R>
    ): R & Any {
        TODO("Not yet implemented")
    }

    override fun flush() {
        TODO("Not yet implemented")
    }

    override fun <S : Template?> saveAndFlush(entity: S & Any): S & Any {
        TODO("Not yet implemented")
    }

    override fun <S : Template?> saveAllAndFlush(entities: MutableIterable<S>): MutableList<S> {
        TODO("Not yet implemented")
    }

    override fun deleteAllInBatch(entities: MutableIterable<Template>) {
        TODO("Not yet implemented")
    }

    override fun deleteAllInBatch() {
        TODO("Not yet implemented")
    }

    override fun deleteAllByIdInBatch(ids: MutableIterable<Long>) {
        TODO("Not yet implemented")
    }

    override fun getOne(id: Long): Template {
        TODO("Not yet implemented")
    }

    override fun getById(id: Long): Template {
        TODO("Not yet implemented")
    }

    override fun getReferenceById(id: Long): Template {
        TODO("Not yet implemented")
    }
}
