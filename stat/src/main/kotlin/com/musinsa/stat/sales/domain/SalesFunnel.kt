package com.musinsa.stat.sales.domain

/**
 * 판매 경로
 */
enum class SalesFunnel(val description: String) {
    DEFAULT("기본값. 선택 없음(defaultValue)"),
    MOBILE_WEB("모바일 웹"),
    MOBILE_APP("모바일 앱"),
    MOBILE_ALL("모바일 웹+앱")
}
