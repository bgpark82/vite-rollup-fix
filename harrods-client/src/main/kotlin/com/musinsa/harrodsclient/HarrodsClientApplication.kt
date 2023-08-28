package com.musinsa.harrodsclient

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class HarrodsClientApplication

fun main(args: Array<String>) {
    runApplication<HarrodsClientApplication>(*args)
}
