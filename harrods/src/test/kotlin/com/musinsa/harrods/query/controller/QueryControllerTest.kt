package com.musinsa.harrods.query.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.musinsa.common.redis.config.LocalRedisServer
import com.musinsa.harrods.query.service.QueryService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest
class QueryControllerTest @Autowired constructor(
    val mvc: MockMvc,
    val mapper: ObjectMapper,
    @MockBean val queryService: QueryService,
    @MockBean val redisServer: LocalRedisServer
) {
    @Test
    fun `template은 빈 문자열이 아니다`() {
        val template이_빈문자열인_요청 = """
            {
                "template": "",
                "interval": "* * * *"
            }
        """.trimIndent()

        mvc.perform(
            post("/queries")
                .contentType(MediaType.APPLICATION_JSON)
                .content(template이_빈문자열인_요청)
        )
            .andDo(print())
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.errorCode").value("INVALID_REQUEST_VALUE"))
            .andExpect(jsonPath("$.invalidField").value("template"))
    }

    @Test
    fun `template은 null이 아니다`() {
        val template이_없는_요청 = """
            {
                "interval": "* * * *"
            }
        """.trimIndent()

        mvc.perform(
            post("/queries")
                .contentType(MediaType.APPLICATION_JSON)
                .content(template이_없는_요청)
        )
            .andDo(print())
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.errorCode").value("INVALID_REQUEST_VALUE"))
            .andExpect(jsonPath("$.invalidField").value("template"))
    }

    @Test
    fun `ttl은 기본값은 3일이다`() {
        val ttl이_없는_요청 = """
            {
              "template": "SELECT * FROM user",
              "interval": "* * * *"
            }
        """.trimIndent()

        mvc.perform(
            post("/queries")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ttl이_없는_요청)
        )
            .andDo(print())
            .andExpect(status().isOk)
    }

    @Test
    fun `ttl은 최소값이 1이상이다`() {
        val request = MockQueryRequest(template = "SELECT * FROM user", params = mapOf(), ttl = -1L, interval = "* * * * *")

        mvc.perform(
            post("/queries")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(request))
        )
            .andDo(print())
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.errorCode").value("INVALID_REQUEST_VALUE"))
            .andExpect(jsonPath("$.invalidField").value("ttl"))
    }

    @Test
    fun `ttl은 최대값은 9_223_370_000_000_000 이하이다`() {
        val ttlMax = 9_223_370_000_000_000L + 1L
        val request = MockQueryRequest(template = "SELECT * FROM user", params = mapOf(), ttl = ttlMax, interval = "* * * * *")

        mvc.perform(
            post("/queries")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(request))
        )
            .andDo(print())
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.errorCode").value("INVALID_REQUEST_VALUE"))
            .andExpect(jsonPath("$.invalidField").value("ttl"))
    }

    data class MockQueryRequest(
        val template: String? = null,
        val params: Map<String, Any>? = null,
        val ttl: Long? = null,
        val interval: String? = null
    )

    @Test
    fun name() {
        "SELECT t1.brand,\n" +
            "       SUM(IF(t1.age_band = 'age_band.0', t1.cnt, 0)) AS `age_band.0`,\n" +
            "       SUM(IF(t1.age_band = 'age_band.19', t1.cnt, 0)) AS `age_band.19`,\n" +
            "       SUM(IF(t1.age_band = 'age_band.24', t1.cnt, 0)) AS `age_band.24`,\n" +
            "       SUM(IF(t1.age_band = 'age_band.29', t1.cnt, 0)) AS `age_band.29`,\n" +
            "       SUM(IF(t1.age_band = 'age_band.34', t1.cnt, 0)) AS `age_band.34`,\n" +
            "       SUM(IF(t1.age_band = 'age_band.40', t1.cnt, 0)) AS `age_band.40`,\n" +
            "       SUM(IF(t1.gender = 'gender.F', t1.cnt, 0)) AS `gender.F`,\n" +
            "       SUM(IF(t1.gender = 'gender.M', t1.cnt, 0)) AS `gender.M`,\n" +
            "       SUM(IF(t1.gender = 'gender.N', t1.cnt, 0)) AS `gender.N`,\n" +
            "       SUM(t1.cnt) AS total,\n" +
            "       SUM(t1.qty) AS quantity\n" +
            "\n" +
            "  FROM (\n" +
            "\t\tSELECT o.uid,\n" +
            "\t\t       o.brand,\n" +
            "\t\t       CASE WHEN o.age <= 18 THEN 'age_band.0'\n" +
            "            \t\tWHEN o.age >= 19 AND o.age < 24 THEN 'age_band.19'\n" +
            "            \t\tWHEN o.age >= 24 AND o.age < 29 THEN 'age_band.24'\n" +
            "            \t\tWHEN o.age >= 29 AND o.age < 34 THEN 'age_band.29'\n" +
            "            \t\tWHEN o.age >= 34 AND o.age < 40 THEN 'age_band.34'\n" +
            "            \t\tELSE 'age_band.40' END AS age_band,\n" +
            "       \n" +
            "\t\t       CASE WHEN o.gender = 'F' THEN 'gender.F'\n" +
            "                    WHEN o.gender = 'M' THEN 'gender.M'\n" +
            "                    ELSE 'gender.N' END AS gender,\n" +
            "\n" +
            "\t\t       SUM(IF(o.ord_state = 5, 1, -1)) AS cnt,\n" +
            "\t\t       SUM(IF(o.ord_state = 5, o.qty, -1*o.qty)) AS qty\n" +
            "\t\t  \n" +
            "\t\t  FROM (SELECT ow.ord_wonga_no, \n" +
            "\t\t\t\t       om.uid AS uid, \n" +
            "\t\t\t\t       g.brand AS brand,\n" +
            "\t\t\t\t       u.age AS age, \n" +
            "\t\t\t\t       u.gender AS gender,\n" +
            "\t\t\t\t       -- INT(date_format(now(), 'yyyy'))-m.birth1 AS age, \n" +
            "\t\t\t\t       -- m.sex AS gender,\n" +
            "\t\t\t\t       ow.ord_state AS ord_state, \n" +
            "\t\t\t\t       ow.qty AS qty \n" +
            "\t\t\t\t  FROM musinsa.bizest.order_opt_wonga ow JOIN musinsa.bizest.order_opt oo ON ow.ord_opt_no = oo.ord_opt_no\n" +
            "\t\t\t\t                                         JOIN musinsa.bizest.order_mst om ON oo.ord_no = om.ord_no\n" +
            "\t\t\t\t                                         JOIN datamart.datamart.users u ON om.uid = u.uid\n" +
            "\t\t\t\t                                         JOIN musinsa.bizest.goods g ON ow.goods_no = g.goods_no\n" +
            "\t\t\t\t                                         -- JOIN musinsa.member.rb_s_mbrdata m ON om.uid = m.memberuid\n" +
            "\t\t\t\t WHERE ow.ord_state IN (5, 60, 61)\n" +
            "\t\t\t\t   AND ow.ord_state_date >= date_format(date_add(now(), -365),'yyyyMMdd')\n" +
            "\t\t\t\t   AND ow.ord_state_date < date_format(now(),'yyyyMMdd')\n" +
            "           AND u.gender = 'F'\n" +
            "\t\t\t\t   ) o\n" +
            "\t\t GROUP BY all\n" +
            "\t\t ) t1 JOIN (SELECT g.brand\n" +
            "\t\t      \t      FROM musinsa.bizest.order_opt_wonga ow JOIN musinsa.bizest.goods g ON ow.goods_no = g.goods_no\n" +
            "\t\t\t           WHERE ow.ord_state IN (5, 60, 61)\n" +
            "\t\t\t             AND ow.ut >= date_format(dateadd(HOUR, 9-2, now()), 'yyyy-MM-dd HH:mm:ss') -- utc 에 9시간을 더하고(KST) 2시간 이전\n" +
            "\t\t\t             AND ow.ut < date_format(dateadd(HOUR, 9-1, now()), 'yyyy-MM-dd HH:mm:ss')\n" +
            "\t\t\t           GROUP BY all) t2 ON t1.brand = t2.brand\n" +
            "  GROUP BY all"
    }
}
