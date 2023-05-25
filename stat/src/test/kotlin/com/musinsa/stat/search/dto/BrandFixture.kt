package com.musinsa.stat.search.dto

import com.musinsa.stat.util.ObjectMapperFactory.readValues

object BrandFixture {
    fun 무신사_브랜드_목록(): List<Brand> {
        return readValues(
            """
            [
                {
                    "brandType": "S",
                    "brandId": "musinsa",
                    "brandName": "무신사",
                    "brandNameEn": "MUSINSA",
                    "used": false,
                    "isGlobal": true
                },
                {
                    "brandType": "U",
                    "brandId": "musinsadesignerinvitation",
                    "brandName": "무신사 디자이너 인비테이션",
                    "brandNameEn": "MUSINSA DESIGNER INVITATION",
                    "used": true,
                    "isGlobal": false
                }
            ]
        """.trimIndent(), Array<Brand>::class.java
        )
    }
}