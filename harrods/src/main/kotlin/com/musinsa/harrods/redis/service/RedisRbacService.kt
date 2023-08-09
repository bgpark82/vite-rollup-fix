package com.musinsa.harrods.redis.service

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service

@Service
class RedisRbacService(
    private val redisTemplate: RedisTemplate<String, String>
)
