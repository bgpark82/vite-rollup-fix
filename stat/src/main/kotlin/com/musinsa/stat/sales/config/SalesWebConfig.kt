package com.musinsa.stat.sales.config

import com.musinsa.stat.sales.domain.OrderBy
import org.springframework.context.annotation.Configuration
import org.springframework.core.convert.converter.Converter
import org.springframework.format.FormatterRegistry
import org.springframework.util.StringUtils
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

/**
 * API 처리에 필요한 설정값 추가
 */
@Configuration
class SalesWebConfig : WebMvcConfigurer {
    override fun addFormatters(registry: FormatterRegistry) {
        super.addFormatters(registry)
        registry.addConverter(
            StringToEnumConverter()
        )
    }
}

/**
 * OrderBy 값으로 입력된 String 의 첫 글자만 대문자로 바꾼다.
 */
class StringToEnumConverter :
    Converter<String, OrderBy> {
    override fun convert(source: String): OrderBy {
        return OrderBy.valueOf(StringUtils.capitalize(source))
    }
}
