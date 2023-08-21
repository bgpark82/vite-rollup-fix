package com.musinsa.harrods.query.service

class ParamCombinator {

    fun generate(params: Map<String, Any>): List<Map<String, Any>> {
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

            // 문자인 경우
            if (value is String) {
                currentCombination[name] = value
                generateCombination(index + 1, currentCombination)
            }
        }

        generateCombination(0, mutableMapOf())
        return combinations
    }
}
