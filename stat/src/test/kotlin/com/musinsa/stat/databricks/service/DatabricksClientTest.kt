package com.musinsa.stat.databricks.service

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DatabricksClientTest {
    @Autowired
    private lateinit var databricksClient: DatabricksClient

    @Test
    fun test() {
        println(databricksClient.getDatabricksQuery("5d7fad6c-1fdd-461e-8102-2e2fa39381a5?o=3626753574208338"))
    }
}