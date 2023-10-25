package com.musinsa.harrodsclient.redis.controller

import com.musinsa.common.restdoc.POST
import com.musinsa.common.restdoc.RestDocsControllerHelper
import com.musinsa.common.restdoc.성공_검증_AWAIT
import com.musinsa.common.restdoc.유효하지_않은_요청값_검증
import com.musinsa.common.util.ObjectMapperFactory.readValues
import com.musinsa.common.util.ObjectMapperFactory.typeRefListMapAny
import com.musinsa.common.util.ObjectMapperFactory.writeValueAsString
import com.musinsa.harrodsclient.redis.dto.KEY_SIZE_MAX
import com.musinsa.harrodsclient.redis.dto.KEY_SIZE_MIN
import com.musinsa.harrodsclient.redis.dto.Search
import com.musinsa.harrodsclient.redis.service.RedisClient
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.payload.PayloadDocumentation.requestFields

@WebMvcTest(controllers = [RedisController::class])
internal class RedisControllerTest : RestDocsControllerHelper() {
    @MockBean
    lateinit var redisClient: RedisClient

    @ParameterizedTest
    @CsvSource(value = ["$KEY_SIZE_MIN,-1", "$KEY_SIZE_MAX,1"])
    fun `유효하지 않은 사이즈의 캐시키는 요청이 실패한다`(KEY_SIZE: Int, ADD_SIZE: Int) {
        val 유효하지_않은_캐시키_사이즈를_가진_요청객체 =
            writeValueAsString(요청객체_생성(KEY_SIZE + ADD_SIZE))

        mockMvc.POST("/cache", 유효하지_않은_캐시키_사이즈를_가진_요청객체).유효하지_않은_요청값_검증("keys")
    }

    private fun 요청객체_생성(keySize: Int): Search {
        return Search(keys = Array(keySize) { index -> index.toString() })
    }

    @Test
    fun `캐시 가져오기`() {
        // given
        val SAMPLE_1 = "harrods:peter.park:1990737503:gender:M:brand:twn:gender:M"
        val SAMPLE_2 = "harrods:peter.park:1413978463:brand:blackmoment"
        val SAMPLE_1_VAL = """
                {"brand": "twn", "gender": "M", "age_band.0": 10515, "age_band.19": 20256, "age_band.24": 17019, "age_band.29": 10902, "age_band.34": 4476, "age_band.40": 5636, "gender.F": 0, "gender.M": 68804, "gender.N": 0, "total": 68804, "quantity": 69195}
        """.trimIndent()
        val SAMPLE_2_VAL = """
                {"brand": "blackmoment", "age_band.0": 317, "age_band.19": 1963, "age_band.24": 1549, "age_band.29": 649, "age_band.34": 300, "age_band.40": 907, "gender.F": 5685, "gender.M": 0, "gender.N": 0, "total": 5685, "quantity": 5727}
        """.trimIndent()
        val SAMPLE_3 = "test:not:exist"
        val 조회키 = Search(arrayOf(SAMPLE_1, SAMPLE_2, SAMPLE_3))
        val 응답값 = readValues("[{\"$SAMPLE_1\":$SAMPLE_1_VAL}, {\"$SAMPLE_2\":$SAMPLE_2_VAL}, {\"$SAMPLE_3\":{}}]", typeRefListMapAny)

        runBlocking {
            whenever(redisClient.getAll(any())).thenReturn(응답값)

            mockMvc.POST("/cache", writeValueAsString(조회키)).성공_검증_AWAIT(응답값)
                .andDo(
                    document(
                        "harrods-client",
                        requestFields(
                            fieldWithPath("keys")
                                .type(JsonFieldType.ARRAY).description("조회할 키.".plus("최소: $KEY_SIZE_MIN. 최대: $KEY_SIZE_MAX"))
                        )
                    )
                )
            // Coroutine suspend 적용으로 Response 값이 ascii Docs 로 생성되지 않는다.
            // 어쩔수 없이 test/resources/harrods-client 폴더에 응답값을 생성 후, test Task build 시, 해당 값을 build/generated-snippets 에 복사하는 방식으로 구현
        }
    }
}
