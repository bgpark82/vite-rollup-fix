package com.musinsa.harrodsclient.redis.controller

import com.musinsa.common.devstandard.SuccessResponse
import com.musinsa.harrodsclient.redis.dto.Search
import com.musinsa.harrodsclient.redis.service.RedisClient
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/cache")
class RedisController(private val redisClient: RedisClient) {
    @PostMapping
    suspend fun getAll(
        @Valid @RequestBody
        search: Search
    ): SuccessResponse {
        return SuccessResponse(data = redisClient.getAll(search))
    }
}
