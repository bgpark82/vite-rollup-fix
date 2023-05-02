package com.musinsa.stat

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
// @ConstructorBinding 사용시 필수
class StatApplication

fun main(args: Array<String>) {
    runApplication<StatApplication>(*args)
}