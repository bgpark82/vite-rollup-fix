package com.musinsa.harrods.learning

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.JsonNodeFactory
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.Test

class JsonNodeTest {

    @Test
    fun `문자로 쓰기`() {
        val mapper = ObjectMapper()
        val map = mapOf("key" to "value")

        val string = mapper.writeValueAsString(map)

        assertThat(string).isEqualTo("{\"key\":\"value\"}")
    }

    @Test
    fun `ObjectNode 테스트`() {
        val objectNode = JsonNodeFactory.instance.objectNode()
        objectNode.put("name", "peter")
        objectNode.put("age", 30)

        assertThat(objectNode.isObject).isTrue()
        assertThat(objectNode.findValue("name").asText()).isEqualTo("peter")
        assertThat(objectNode.path("age").asInt()).isEqualTo(30)
    }

    @Test
    fun `ArrayNode 테스트`() {
        val arrayNode = JsonNodeFactory.instance.arrayNode()
        arrayNode.add(10)
        arrayNode.add("10")

        assertThat(arrayNode.isArray).isTrue()
        assertThat(arrayNode.get(0).asInt()).isEqualTo(10)
        assertThat(arrayNode.get(0).asText()).isEqualTo("10")
        assertThat(arrayNode.get(1).asInt()).isEqualTo(10)
        assertThat(arrayNode.get(1).asText()).isEqualTo("10")
    }

    @Test
    fun `Pojo 테스트`() {
        val arrayNode = JsonNodeFactory.instance.arrayNode()
        arrayNode.add(10)
        arrayNode.add("10")

        val childNode = JsonNodeFactory.instance.objectNode()
        childNode.putPOJO("child", "10")

        val objectNode = JsonNodeFactory.instance.objectNode()
        objectNode.set<JsonNode>("list", arrayNode)
        objectNode.set<JsonNode>("object", childNode)

        assertThat(objectNode.isObject).isTrue()
        assertThat(objectNode.findValue("list").isArray).isTrue()
        assertThat(objectNode.findValue("list").get(0).asInt()).isEqualTo(10)
        assertThat(objectNode.get("object").get("child").isPojo).isTrue()
        assertThat(objectNode.get("object").get("child").asText()).isEqualTo("10")
    }
}
