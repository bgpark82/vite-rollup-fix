package com.musinsa.commonwebflux.restdoc

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.RestDocumentationExtension
import org.springframework.test.web.reactive.server.WebTestClient

/**
 * Spring REST Docs Enum 노출을 위한 공통 선언 부분
 */
@Suppress("JUnitMalformedDeclaration", "SpringJavaAutowiredMembersInspection")
@ExtendWith(RestDocumentationExtension::class)
open class WebFluxRestDocsEnumControllerHelper {
    @Autowired
    lateinit var webTestClient: WebTestClient

    @BeforeEach
    fun setUp(
        applicationContext: ApplicationContext,
        restDocumentation: RestDocumentationContextProvider
    ) {
        this.webTestClient = buildEnumWebTestClient(
            applicationContext,
            restDocumentation
        )
    }
}
