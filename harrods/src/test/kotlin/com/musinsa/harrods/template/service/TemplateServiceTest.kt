package com.musinsa.harrods.template.service

import com.musinsa.harrods.query.domain.MockTemplateRepository
import com.musinsa.harrods.query.service.QueryService
import com.musinsa.harrods.query.service.TemplateFormatter
import com.musinsa.harrods.template.dto.TemplateRequest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock

class TemplateServiceTest {

    private val templateFormatter = TemplateFormatter()
    private val templateRepository = MockTemplateRepository()
    private val queryService = mock<QueryService>()
    private val templateService = TemplateService(templateFormatter, templateRepository, queryService)

    @Test
    fun `템플릿을 생성한다`() {
        val request = TemplateRequest(
            template = "SELECT * FROM user",
            params = null,
            ttl = 300L,
            interval = "* * * * *",
            userId = "peter.park",
            alias = listOf("brand")
        )

        templateService.create(request)

        val savedTemplate = templateRepository.findAll()
        assertThat(savedTemplate.size).isEqualTo(1)
        assertThat(savedTemplate[0].userId).isEqualTo("peter.park")
    }
}
