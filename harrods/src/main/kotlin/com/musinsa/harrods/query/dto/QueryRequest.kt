package com.musinsa.harrods.query.dto

import com.musinsa.harrods.utils.validator.Cron
import com.musinsa.harrods.utils.validator.Template
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size
import org.hibernate.validator.constraints.UniqueElements

const val TTL_DEFAULT: Long = 3 * 24 * 60 * 60
const val TTL_MAX: Long = 9_223_370_000_000_000L
const val TTL_MIN: Long = 1L

data class QueryRequest(

    /**
     * 쿼리 템플릿
     */
    @field:NotBlank(message = "템플릿은 필수값입니다")
    @field:Template(message = "유효하지 않은 템플릿입니다")
    val template: String,

    /**
     * 쿼리 파라미터
     */
    val params: Map<String, Any>?,

    /**
     * 캐시 만료 시간 (단위: 초)
     */
    @field:Min(value = TTL_MIN, message = "TTL의 최소값은 $TTL_MIN 이상 입니다")
    @field:Max(value = TTL_MAX, message = "TTL의 최대값은 $TTL_MAX 이하 입니다")
    val ttl: Long = TTL_DEFAULT,

    /**
     * cron 표현식
     */
    @field:NotBlank(message = "interval은 필수값입니다")
    @field:Cron(message = "유효하지 않은 cron 표현식 입니다")
    val interval: String,

    /**
     * 등록자 아이디
     */
    @field:NotBlank(message = "등록자 아이디는 null이거나 빈 문자열이 아니어야 합니다")
    val userId: String,

    /**
     * 별칭 (cacheKey 생성에 사용, cachekeySuffix로 테이블에 저장)
     */
    @field:NotEmpty(message = "별칭은 필수값입니다")
    @field:Size(min = 1, max = 5, message = "별칭은 최대 5개만 등록 가능합니다")
    @field:UniqueElements(message = "별칭은 중복될 수 없습니다")
    val alias: List<String>
)
