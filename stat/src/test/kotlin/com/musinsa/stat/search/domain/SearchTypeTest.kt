package com.musinsa.stat.search.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

private class SearchTypeTest {
    @Test
    fun 검색유형은_3개_존재한다() {
        assertThat(SearchType.values().size).isEqualTo(3)
    }

    @ParameterizedTest
    @EnumSource(mode = EnumSource.Mode.MATCH_ALL)
    fun 존재하는_검색유형_확인(searchType: SearchType) {
        assertThat(searchType).isIn(
            SearchType.BRAND,
            SearchType.PARTNER,
            SearchType.TAG
        )
    }
}
