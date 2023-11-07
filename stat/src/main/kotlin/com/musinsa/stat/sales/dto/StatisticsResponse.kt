package com.musinsa.stat.sales.dto

import com.musinsa.stat.sales.service.PREFIX_ANNOTATION
import java.util.stream.Stream

/**
 * 매출통계 응답값 상위 클래스
 */
abstract class StatisticsResponse(
    originSql: String
) {
    // 합계
    abstract val sum: SalesStatisticsMetric

    // 평균
    abstract val average: SalesStatisticsMetric

    // 모든 페이지 갯수
    abstract val totalPages: Long

    // 결과값
    abstract val content: List<SalesStatisticsMetric>

    // 모든 아이템 갯수
    abstract val totalItems: Long

    // SQL
    val sql: String

    /**
     * SQL 에서 주석을 포함한 라인을 제거한다.
     * 개행문자는 공백으로 치환한다.
     */
    init {
        sql = originSql.lines().filterNot { it.contains(PREFIX_ANNOTATION) }
            .toList()
            .joinToString(separator = "\n").trimIndent().replace("\n", " ")
    }

    /**
     * 합계와 평균을 구한다.
     */
    @JvmName("calculateSumAndAverageLong")
    fun calculateSumAndAverage(stream: Stream<Long>): Pair<Long, Long> {
        val list = stream.toList()
        return Pair(list.sum(), list.average().toLong())
    }

    /**
     * 합계는 0으로 표기하며, 평균을 소수점 2자리까지 구한다.
     */
    @JvmName("calculateSumAndAverageDouble")
    fun calculateSumAndAverage(stream: Stream<Double>): Pair<Double, Double> {
        val list = stream.toList()
        return Pair(0.0, String.format("%.2f", list.average()).toDouble())
    }
}
