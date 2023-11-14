//package com.musinsa.harrodsclient.redis.controller
//
//import com.musinsa.common.devstandard.SuccessResponse
//import com.musinsa.common.util.ObjectMapperFactory.readValue
//import com.musinsa.common.util.ObjectMapperFactory.readValues
//import com.musinsa.common.util.ObjectMapperFactory.typeRefListMapAny
//import com.musinsa.common.util.ObjectMapperFactory.writeValueAsString
//import com.musinsa.commonmvc.restdoc.POST
//import com.musinsa.commonmvc.restdoc.RestDocsControllerHelper
//import com.musinsa.commonmvc.restdoc.성공_검증_AWAIT
//import com.musinsa.commonmvc.restdoc.유효하지_않은_요청값_검증
//import com.musinsa.harrodsclient.redis.dto.KEY_SIZE_MAX
//import com.musinsa.harrodsclient.redis.dto.KEY_SIZE_MIN
//import com.musinsa.harrodsclient.redis.dto.Search
//import com.musinsa.harrodsclient.redis.service.KEY
//import com.musinsa.harrodsclient.redis.service.RedisClient
//import com.musinsa.harrodsclient.redis.service.VALUE
//import kotlinx.coroutines.runBlocking
//import org.junit.jupiter.api.Test
//import org.junit.jupiter.params.ParameterizedTest
//import org.junit.jupiter.params.provider.CsvSource
//import org.mockito.kotlin.any
//import org.mockito.kotlin.whenever
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
//import org.springframework.boot.test.mock.mockito.MockBean
//import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
//import org.springframework.restdocs.payload.JsonFieldType
//import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
//import org.springframework.restdocs.payload.PayloadDocumentation.requestFields
//
//@WebMvcTest(controllers = [RedisController::class])
//internal class RedisControllerTest : RestDocsControllerHelper() {
//    @MockBean
//    lateinit var redisClient: RedisClient
//
//    @ParameterizedTest
//    @CsvSource(value = ["$KEY_SIZE_MIN,-1", "$KEY_SIZE_MAX,1"])
//    fun `유효하지 않은 사이즈의 캐시키는 요청이 실패한다`(KEY_SIZE: Int, ADD_SIZE: Int) {
//        val 유효하지_않은_캐시키_사이즈를_가진_요청객체 =
//            writeValueAsString(요청객체_생성(KEY_SIZE + ADD_SIZE))
//
//        mockMvc.POST("/cache", 유효하지_않은_캐시키_사이즈를_가진_요청객체).유효하지_않은_요청값_검증("keys")
//    }
//
//    private fun 요청객체_생성(keySize: Int): Search {
//        return Search(keys = Array(keySize) { index -> index.toString() })
//    }
//
//    @Test
//    fun `캐시 가져오기`() {
//        // given
//        val SAMPLE_1 = "harrods:woo.choi:1355159473:goods_no:2559251"
//        val SAMPLE_2 = "harrods:woo.choi:-1050180701:goods_no:2725587"
//        val SAMPLE_1_VAL = """
//                {"key": "harrods:woo.choi:1355159473:goods_no:2559251", "value": {"goods_no": "2559251", "age_band.0": 0, "age_band.19": 0, "age_band.24": 2, "age_band.29": 1, "age_band.34": 2, "age_band.40": 1, "age_band.N": 1, "gender.F": 4, "gender.M": 2, "gender.N": 1, "total": 7}}
//        """.trimIndent()
//        val SAMPLE_2_VAL = """
//                {"key": "harrods:woo.choi:-1050180701:goods_no:2725587", "value": {"goods_no": 2725587, "age_band.0": 97, "age_band.19": 289, "age_band.24": 195, "age_band.29": 75, "age_band.34": 12, "age_band.40": 111, "gender.F": 339, "gender.M": 440, "gender.N": 0, "total": 779, "quantity": 780}}
//        """.trimIndent()
//        val SAMPLE_3 = "test:not:exist"
//        val 조회키 = Search(arrayOf(SAMPLE_1, SAMPLE_2, SAMPLE_3))
//        val 응답값 = readValues(
//            "[$SAMPLE_1_VAL, $SAMPLE_2_VAL, {\"$KEY\": \"$SAMPLE_3\", \"$VALUE\": {}}]",
//            typeRefListMapAny
//        )
//        val API_응답값 = readValue(
//            writeValueAsString(
//                SuccessResponse(
//                    data = 응답값
//                )
//            ),
//            SuccessResponse::class.java
//        )
//
//        runBlocking {
//            whenever(redisClient.getAll(any())).thenReturn(응답값)
//
//            mockMvc.POST("/cache", writeValueAsString(조회키)).성공_검증_AWAIT(API_응답값)
//                .andDo(
//                    document(
//                        "harrods-client",
//                        requestFields(
//                            fieldWithPath("keys")
//                                .type(JsonFieldType.ARRAY)
//                                .description("조회할 키.".plus("최소: $KEY_SIZE_MIN. 최대: $KEY_SIZE_MAX"))
//                        )
//                    )
//                )
//            // Coroutine suspend 적용으로 Response 값이 ascii Docs 로 생성되지 않는다.
//            // 어쩔수 없이 test/resources/harrods-client 폴더에 응답값을 생성 후, test Task build 시, 해당 값을 build/generated-snippets 에 복사하는 방식으로 구현
//        }
//    }
//}
