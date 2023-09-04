package com.musinsa.harrods.query.service

import org.springframework.stereotype.Component

@Component
class ParamCombinator {

    /**
     * 파라미터로 파라미터들의 조합을 생성한다
     *
     * @param params 템플릿에 사용될 파라미터
     * @return 파라미터들의 조합
     */
    fun generate(params: Map<String, Any>?): List<Map<String, Any>> {
        // 빈 map을 가진 list 반환
        if (params.isNullOrEmpty()) {
            return listOf(mapOf())
        }

        val keys = params.keys.toList()
        val values = params.values.toList()

        val combinations = mutableListOf<Map<String, Any>>()

        fun generateCombination(index: Int, currentCombination: MutableMap<String, Any>) {
            if (index == keys.size) {
                combinations.add(currentCombination.toMap())
                return
            }

            val name = keys[index]
            val value = values[index]

            // 리스트인 경우
            if (value is List<*>) {
                for (value in values[index] as List<*>) {
                    currentCombination[name] = value as Any
                    generateCombination(index + 1, currentCombination)
                }
            }

            // 문자, 숫자인 경우
            if (value is String || value is Number) {
                currentCombination[name] = value
                generateCombination(index + 1, currentCombination)
            }
        }

        generateCombination(0, mutableMapOf())
        return combinations
    }
}
