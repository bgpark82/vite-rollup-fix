package com.musinsa.commonwebflux.restdoc

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.RestDocumentationExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.web.context.WebApplicationContext

/**
 * Spring REST Docs Enum 노출을 위한 공통 선언 부분
 */
@Suppress("JUnitMalformedDeclaration", "SpringJavaAutowiredMembersInspection")
@ExtendWith(RestDocumentationExtension::class)
open class RestDocsEnumControllerHelper {
    @Autowired
    lateinit var mockMvc: MockMvc

    @BeforeEach
    fun setUp(
        webApplicationContext: WebApplicationContext,
        restDocumentationContextProvider: RestDocumentationContextProvider
    ) {
        this.mockMvc = buildEnumMockMvc(
            webApplicationContext,
            restDocumentationContextProvider
        )
    }
}
