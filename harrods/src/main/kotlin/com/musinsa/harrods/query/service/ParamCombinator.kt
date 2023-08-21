package com.musinsa.harrods.query.service

class ParamCombinator {

    fun generate(params: Map<String, List<String>>): List<Map<String, Any>> {
        val keys = params.keys.toList()
        val values = params.values.toList()

        val combinations = mutableListOf<Map<String, String>>()

        fun generateCombination(index: Int, currentCombination: MutableMap<String, String>) {
            if (index == keys.size) {
                combinations.add(currentCombination.toMap())
                return
            }

            val paramName = keys[index]
            for (value in values[index]) {
                currentCombination[paramName] = value
                generateCombination(index + 1, currentCombination)
            }
        }

        generateCombination(0, mutableMapOf())
        return combinations
    }
}
