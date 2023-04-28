package com.musinsa.stat

import com.musinsa.stat.databricks.DatabricksResolver
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(value = [DatabricksResolver::class]) // @ConstructorBinding 사용시 필수
class StatApplication

fun main(args: Array<String>) {
	runApplication<StatApplication>(*args)
}