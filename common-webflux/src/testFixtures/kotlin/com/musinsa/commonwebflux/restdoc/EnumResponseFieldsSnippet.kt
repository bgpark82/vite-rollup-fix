package com.musinsa.commonwebflux.restdoc

import org.springframework.http.MediaType
import org.springframework.restdocs.operation.Operation
import org.springframework.restdocs.payload.AbstractFieldsSnippet
import org.springframework.restdocs.payload.FieldDescriptor
import org.springframework.restdocs.payload.PayloadSubsectionExtractor

/**
 * Enum 값을 아스키독에 노출하기 위한 스니펫
 */
class EnumResponseFieldsSnippet(
    type: String?,
    descriptors: List<FieldDescriptor>?,
    attributes: Map<String, Any>?,
    ignoreUndocumentedFields: Boolean = true,
    subsectionExtractor: PayloadSubsectionExtractor<*>?
) : AbstractFieldsSnippet(
    type,
    descriptors,
    attributes,
    ignoreUndocumentedFields,
    subsectionExtractor
) {
    override fun getContentType(operation: Operation): MediaType? {
        return operation.response.headers.contentType
    }

    override fun getContent(operation: Operation): ByteArray {
        return operation.response.content
    }
}
