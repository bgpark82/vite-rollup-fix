package com.musinsa.harrods.template.service

import com.musinsa.harrods.error.ErrorCode
import com.musinsa.harrods.template.domain.Template
import com.musinsa.harrods.template.domain.TemplateRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TemplateService(
    private val templateRepository: TemplateRepository
) {

    @Transactional(readOnly = true)
    fun findAll(): List<Template> {
        return templateRepository.findAll()
    }

    @Transactional(readOnly = true)
    fun findById(id: Long): Template {
        return templateRepository.findByIdOrNull(id) ?: ErrorCode.QUERY_NOT_FOUND.throwMe()
    }
}
