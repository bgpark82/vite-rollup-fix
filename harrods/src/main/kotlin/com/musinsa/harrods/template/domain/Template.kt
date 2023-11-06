package com.musinsa.harrods.template.domain

import com.musinsa.harrods.query.domain.Query
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import java.time.LocalDateTime

@Table(name = "template")
@Entity
class Template(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    /**
     * 템플릿 명
     */
    var name: String,

    /**
     * 등록자 아이디
     */
    @Column(name = "user_id", nullable = false)
    var userId: String,

    /**
     * 템플릿 등록 시간
     */
    @Column(name = "created_date_time", nullable = false)
    var createdDateTime: LocalDateTime,

    /**
     * 템플릿 수정 시간
     */
    @Column(name = "modified_date_time", nullable = false)
    var modifiedDateTime: LocalDateTime,

    /**
     * 생성된 쿼리
     */
    @OneToMany(cascade = [CascadeType.ALL])
    @JoinColumn(name = "template_id")
    var queries: List<Query> = mutableListOf()
) {

    // TODO: jpa-plugin 이슈 해결
    constructor() : this(null, "", "", LocalDateTime.now(), LocalDateTime.now(), emptyList())

    companion object {
        fun create(name: String, userId: String, queries: List<Query>): Template {
            return Template(
                id = null,
                name = name,
                userId = userId,
                createdDateTime = LocalDateTime.now(),
                modifiedDateTime = LocalDateTime.now(),
                queries = queries
            )
        }
    }
}
