package com.musinsa.stat.restdoc

import com.musinsa.common.restdoc.buildMockMvc
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.RestDocumentationExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.web.context.WebApplicationContext

/**
 * Spring REST Docs 사용을 위한 공통 선언 부분
 */
@Suppress("JUnitMalformedDeclaration", "SpringJavaAutowiredMembersInspection")
@ExtendWith(RestDocumentationExtension::class)
open class RestDocsControllerHelper {
    @Autowired
    lateinit var mockMvc: MockMvc

    @BeforeEach
    fun setUp(
        webApplicationContext: WebApplicationContext,
        restDocumentationContextProvider: RestDocumentationContextProvider
    ) {
        this.mockMvc = buildMockMvc(
            webApplicationContext,
            restDocumentationContextProvider
        )
    }
}