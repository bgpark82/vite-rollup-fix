package com.musinsa.harrodsclient.redis.controller

import com.musinsa.harrodsclient.redis.dto.Search
import com.musinsa.harrodsclient.redis.service.RedisClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

// TODO Rest Docs 생성
@RestController
@RequestMapping("/cache")
class RedisController(private val redisClient: RedisClient) {
    // TODO Keys 1-1000 개 Valid Test
    @PostMapping
    fun getAll(@RequestBody search: Search): List<Map<String, Any>> {
        return redisClient.getAll(search)
    }
}
