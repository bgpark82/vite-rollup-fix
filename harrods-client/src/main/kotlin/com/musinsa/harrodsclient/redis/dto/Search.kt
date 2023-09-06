package com.musinsa.harrodsclient.redis.dto

import jakarta.validation.constraints.Size

const val KEY_SIZE_MIN = 1
const val KEY_SIZE_MAX = 1000

/**
 * Redis 에서 값을 가져올때의 요청서
 */
class Search(
    @field:Size(min = KEY_SIZE_MIN, max = KEY_SIZE_MAX)
    val keys: Array<String>
)
