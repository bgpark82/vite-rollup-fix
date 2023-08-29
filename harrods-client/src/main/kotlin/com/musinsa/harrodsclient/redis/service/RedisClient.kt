package com.musinsa.harrodsclient.redis.service

import io.lettuce.core.api.sync.RedisCommands
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

@Service
class RedisClient(@Qualifier("redisCommands") private val redisCommands: RedisCommands<String, String>)
