package com.musinsa.harrods.template.dto

import com.fasterxml.jackson.annotation.JsonFormat
import com.musinsa.harrods.query.dto.QueryResponse
import com.musinsa.harrods.template.domain.Template
import java.time.LocalDateTime

data class TemplateResponse(

    val id: Long?,

    val name: String?,

    val userId: String,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    val createDateTime: LocalDateTime,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    val modifiedDateTime: LocalDateTime,

    val queries: List<QueryResponse>
) {
    companion object {
        fun of(template: Template): TemplateResponse {
            return TemplateResponse(
                id = template.id!!,
                name = template.name,
                userId = template.userId,
                createDateTime = template.createdDateTime,
                modifiedDateTime = template.modifiedDateTime,
                queries = template.queries.map(QueryResponse::of)
            )
        }
    }
}
