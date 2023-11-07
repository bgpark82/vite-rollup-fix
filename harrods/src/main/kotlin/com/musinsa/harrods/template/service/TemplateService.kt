package com.musinsa.harrods.template.service

import com.musinsa.harrods.error.ErrorCode
import com.musinsa.harrods.query.service.QueryService
import com.musinsa.harrods.template.domain.Template
import com.musinsa.harrods.template.domain.TemplateRepository
import com.musinsa.harrods.template.dto.TemplateRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TemplateService(
    private val templateFormatter: TemplateFormatter,
    private val templateRepository: TemplateRepository,
    private val queryService: QueryService
) {

    /**
     * 템플릿과 파라미터로 쿼리를 생성한다
     *
     * @param request 쿼리 생성 요청
     * @return 생성된 쿼리 리스트
     */
    @Transactional
    fun create(request: TemplateRequest): Template {
        val formattedTemplate = templateFormatter.format(request.template)
        val queries = queryService.generate(formattedTemplate, request)

        return templateRepository.save(
            Template.create(
                name = request.name,
                userId = request.userId,
                queries = queries
            )
        )
    }

    @Transactional(readOnly = true)
    fun findAll(): List<Template> {
        return templateRepository.findAll()
    }

    @Transactional(readOnly = true)
    fun findById(id: Long): Template {
        return templateRepository.findByIdOrNull(id) ?: ErrorCode.QUERY_NOT_FOUND.throwMe()
    }
}
