package com.musinsa.stat.search.dto

import com.musinsa.common.util.ObjectMapperFactory.readValues

fun 태그_목록(): List<Tag> {
    return readValues(
        """
            [
                {
                    "tag": "코튼스트라이프라운드넥티셔츠"
                },
                {
                    "tag": "THXSM스트라이프셔츠원피스"
                },
                {
                    "tag": "스트라이프럭비티"
                }
            ]
        """.trimIndent(),
        Array<Tag>::class.java
    )
}
