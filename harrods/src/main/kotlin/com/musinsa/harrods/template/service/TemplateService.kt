package com.musinsa.harrods.template.service

import com.musinsa.harrods.template.domain.Template
import com.musinsa.harrods.template.domain.TemplateRepository
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
}
