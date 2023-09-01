package com.musinsa.harrodsclient.redis.dto

import jakarta.validation.constraints.Size

/**
 * Redis 에서 값을 가져올때의 요청서
 */
class Search(
    @field:Size(min = 1, max = 1000)
    val keys: Array<String>
)
