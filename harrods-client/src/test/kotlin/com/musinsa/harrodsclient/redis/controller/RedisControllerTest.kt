package com.musinsa.harrodsclient.redis.controller

import com.musinsa.common.restdoc.POST
import com.musinsa.common.restdoc.RestDocsControllerHelper
import com.musinsa.common.restdoc.유효하지_않은_요청값_검증
import com.musinsa.common.util.ObjectMapperFactory
import com.musinsa.harrodsclient.redis.dto.KEY_SIZE_MAX
import com.musinsa.harrodsclient.redis.dto.KEY_SIZE_MIN
import com.musinsa.harrodsclient.redis.dto.Search
import com.musinsa.harrodsclient.redis.service.RedisClient
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
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

    @ParameterizedTest
    @CsvSource(value = ["$KEY_SIZE_MIN,-1", "$KEY_SIZE_MAX,1"])
    fun `유효하지 않은 사이즈의 캐시키는 요청이 실패한다`(KEY_SIZE: Int, ADD_SIZE: Int) {
        val 유효하지_않은_캐시키_사이즈를_가진_요청객체 =
            ObjectMapperFactory.writeValueAsString(요청객체_생성(KEY_SIZE + ADD_SIZE))

        mockMvc.POST("/cache", 유효하지_않은_캐시키_사이즈를_가진_요청객체).유효하지_않은_요청값_검증("keys")
    }

    private fun 요청객체_생성(keySize: Int): Search {
        return Search(keys = Array(keySize) { index -> index.toString() })
    }
}
