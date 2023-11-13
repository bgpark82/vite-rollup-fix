package com.musinsa.stat.sales.dto

import com.musinsa.stat.sales.fixture.DailyFixture.DAILY_20230506_CachedRowSet
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class GoodsTest {
    @Test
    fun `생성자로 입력된 썸네일에 CDN URL 을 prefix 한다`() {
        val 썸네일_이미지 = "/image/thumbnail-image"
        val sut = Goods(
            rs = DAILY_20230506_CachedRowSet(),
            goodsNumber = "goodsNumber",
            goodsName = "goodsName",
            styleNumber = "styleNumber",
            brandName = "brandName",
            category = "category",
            mdId = "mdId",
            goodsStatusName = "goodsStatusName",
            thumbnail = 썸네일_이미지
        )

        assertThat(sut.thumbnail).isEqualTo(
            "https://image.msscdn.net".plus(
                썸네일_이미지
            )
        )
    }
}
