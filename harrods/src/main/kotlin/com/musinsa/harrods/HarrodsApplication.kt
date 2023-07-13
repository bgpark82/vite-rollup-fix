package com.musinsa.harrods

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class HarrodsApplication

// TODO 실행 시, dataSource 에러 수정
fun main(args: Array<String>) {
    runApplication<HarrodsApplication>(*args)
}
