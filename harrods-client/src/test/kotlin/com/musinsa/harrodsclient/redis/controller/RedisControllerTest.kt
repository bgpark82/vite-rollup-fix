package com.musinsa.harrodsclient.redis.controller

import com.musinsa.common.restdoc.POST
import com.musinsa.common.restdoc.RestDocsControllerHelper
import com.musinsa.common.restdoc.유효하지_않은_요청값_검증
import com.musinsa.common.util.ObjectMapperFactory
import com.musinsa.harrodsclient.redis.dto.Search
import com.musinsa.harrodsclient.redis.service.RedisClient
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean

@WebMvcTest(controllers = [RedisController::class])
internal class RedisControllerTest : RestDocsControllerHelper() {
    @MockBean
    lateinit var redisClient: RedisClient

    /**
     * 아래 테스트로 대체한다.
     *
     * @see com.musinsa.harrodsclient.redis.service.RedisClientTest.모든_키_아이템을_가져온다
     */
//    @Test
    fun 모든_키_아이템을_가져온다() {
        // doNothing
    }

    @Test
    fun 캐시키_파라미터는_적어도_1개_존재해야_한다() {
        val 키가_없는_요청객체 =
            ObjectMapperFactory.writeValueAsString(Search(keys = arrayOf()))

        mockMvc.POST("/cache", 키가_없는_요청객체).유효하지_않은_요청값_검증("keys")
    }
}
