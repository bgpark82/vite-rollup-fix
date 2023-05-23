package com.musinsa.stat.sales.dto

import org.springframework.restdocs.payload.FieldDescriptor
import org.springframework.restdocs.payload.PayloadDocumentation.applyPathPrefix

/**
 * 최종 응답값 명세
 */
fun 매출통계_명세(명세: MutableList<FieldDescriptor>): MutableList<FieldDescriptor> {
    val 명세서: MutableList<FieldDescriptor> = ArrayList()
    명세서.addAll(
        applyPathPrefix("sum.", 명세)
    )
    명세서.addAll(
        applyPathPrefix("average.", 명세)
    )
    명세서.addAll(
        applyPathPrefix("content.[].", 명세)
    )
    return 명세서
}