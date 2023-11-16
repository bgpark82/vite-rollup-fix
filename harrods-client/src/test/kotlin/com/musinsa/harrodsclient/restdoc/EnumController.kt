package com.musinsa.harrodsclient.restdoc

import com.musinsa.common.restdoc.COMMON_ERROR_VALUES
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

// TODO 매출통계 컨트롤러와 통합
/**
 * Error 아스키독 노출을 위한 테스트 전용 컨트롤러
 */
@RestController
@RequestMapping("/test")
internal class EnumController {
    @GetMapping("/error")
    fun getErrorValues(): Map<String, String> {
        return ERROR_VALUES()
    }
}

fun ERROR_VALUES(): Map<String, String> {
    return COMMON_ERROR_VALUES()
}
