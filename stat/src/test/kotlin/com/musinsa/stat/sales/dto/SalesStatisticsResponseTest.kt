package com.musinsa.stat.sales.dto

import com.musinsa.stat.sales.fixture.DailyFixture.DAILY_20230505
import com.musinsa.stat.sales.fixture.DailyFixture.DAILY_20230506
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll

private class SalesStatisticsResponseTest {

    @Test
    fun API_응답값에_노출되는_SQL_을_가독성이_높게_바꾼다() {
        val 주석과_개행문자가_포함된_쿼리 = """
            SELECT *
            FROM table
            WHERE
                a = "a"
                --b = "b"
                
                --c = "c"
                
                d = "d"
        """.trimIndent()

        val sut = SalesStatisticsResponse(
            listOf(DAILY_20230505()),
            1,
            1,
            주석과_개행문자가_포함된_쿼리
        )

        assertThat(sut.sql).isEqualTo("SELECT * FROM table WHERE     a = \"a\"               d = \"d\"")
    }

    @Test
    fun 응답값을_조립한다() {
        // given
        val 테스트를_위한_DAILY_LIST =
            listOf(
                DAILY_20230505(),
                DAILY_20230506()
            )
        val 페이지_사이즈: Long = 1
        val 페이지: Long = 1

        // when
        val 결과값 =
            SalesStatisticsResponse(테스트를_위한_DAILY_LIST, 페이지_사이즈, 페이지, "실행된 SQL")

        // then(페이지)
        assertThat(결과값.pageSize).isEqualTo(페이지_사이즈)
        assertThat(결과값.page).isEqualTo(페이지)
        assertThat(결과값.totalPages).isEqualTo(2)

        // then(합계)
        assertAll(
            { assertThat(결과값.sum.sellQuantity).isEqualTo(결과값.content[0].sellQuantity + 결과값.content[1].sellQuantity) },
            { assertThat(결과값.sum.sellAmount).isEqualTo(결과값.content[0].sellAmount + 결과값.content[1].sellAmount) },
            { assertThat(결과값.sum.refundQuantity).isEqualTo(결과값.content[0].refundQuantity + 결과값.content[1].refundQuantity) },
            { assertThat(결과값.sum.refundAmount).isEqualTo(결과값.content[0].refundAmount + 결과값.content[1].refundAmount) },
            { assertThat(결과값.sum.exchangeQuantity).isEqualTo(결과값.content[0].exchangeQuantity + 결과값.content[1].exchangeQuantity) },
            { assertThat(결과값.sum.exchangeAmount).isEqualTo(결과값.content[0].exchangeAmount + 결과값.content[1].exchangeAmount) },
            { assertThat(결과값.sum.tradeQuantity).isEqualTo(결과값.content[0].tradeQuantity + 결과값.content[1].tradeQuantity) },
            { assertThat(결과값.sum.tradeAmount).isEqualTo(결과값.content[0].tradeAmount + 결과값.content[1].tradeAmount) },
            { assertThat(결과값.sum.memberDiscounts).isEqualTo(결과값.content[0].memberDiscounts + 결과값.content[1].memberDiscounts) },
            { assertThat(결과값.sum.affiliateDiscounts).isEqualTo(결과값.content[0].affiliateDiscounts + 결과값.content[1].affiliateDiscounts) },
            { assertThat(결과값.sum.otherDiscounts).isEqualTo(결과값.content[0].otherDiscounts + 결과값.content[1].otherDiscounts) },
            { assertThat(결과값.sum.couponDiscounts).isEqualTo(결과값.content[0].couponDiscounts + 결과값.content[1].couponDiscounts) },
            { assertThat(결과값.sum.partnerCouponDiscounts).isEqualTo(결과값.content[0].partnerCouponDiscounts + 결과값.content[1].partnerCouponDiscounts) },
            { assertThat(결과값.sum.pointDiscounts).isEqualTo(결과값.content[0].pointDiscounts + 결과값.content[1].pointDiscounts) },
            { assertThat(결과값.sum.prePointDiscounts).isEqualTo(결과값.content[0].prePointDiscounts + 결과값.content[1].prePointDiscounts) },
            { assertThat(결과값.sum.cartDiscounts).isEqualTo(결과값.content[0].cartDiscounts + 결과값.content[1].cartDiscounts) },
            { assertThat(결과값.sum.groupDiscounts).isEqualTo(결과값.content[0].groupDiscounts + 결과값.content[1].groupDiscounts) },
            { assertThat(결과값.sum.totalDiscounts).isEqualTo(결과값.content[0].totalDiscounts + 결과값.content[1].totalDiscounts) },
            { assertThat(결과값.sum.discountRate).isEqualTo(0.0) },
            { assertThat(결과값.sum.paymentFees).isEqualTo(결과값.content[0].paymentFees + 결과값.content[1].paymentFees) },
            { assertThat(결과값.sum.paymentAmount).isEqualTo(결과값.content[0].paymentAmount + 결과값.content[1].paymentAmount) },
            { assertThat(결과값.sum.sales).isEqualTo(결과값.content[0].sales + 결과값.content[1].sales) },
            { assertThat(결과값.sum.originalPrice).isEqualTo(결과값.content[0].originalPrice + 결과값.content[1].originalPrice) },
            { assertThat(결과값.sum.profit).isEqualTo(결과값.content[0].profit + 결과값.content[1].profit) },
            { assertThat(결과값.sum.profitMargin).isEqualTo(0.0) },
            { assertThat(결과값.sum.purchasesTradeAmount).isEqualTo(결과값.content[0].purchasesTradeAmount + 결과값.content[1].purchasesTradeAmount) },
            { assertThat(결과값.sum.purchasesDiscounts).isEqualTo(결과값.content[0].purchasesDiscounts + 결과값.content[1].purchasesDiscounts) },
            { assertThat(결과값.sum.purchasesPaymentFees).isEqualTo(결과값.content[0].purchasesPaymentFees + 결과값.content[1].purchasesPaymentFees) },
            { assertThat(결과값.sum.purchasesPaymentAmount).isEqualTo(결과값.content[0].purchasesPaymentAmount + 결과값.content[1].purchasesPaymentAmount) },
            { assertThat(결과값.sum.purchasesOriginalPrice).isEqualTo(결과값.content[0].purchasesOriginalPrice + 결과값.content[1].purchasesOriginalPrice) },
            { assertThat(결과값.sum.purchasesProfit).isEqualTo(결과값.content[0].purchasesProfit + 결과값.content[1].purchasesProfit) },
            { assertThat(결과값.sum.purchasesProfitMargin).isEqualTo(0.0) },
            { assertThat(결과값.sum.purchasesRatio).isEqualTo(0.0) },
            { assertThat(결과값.sum.partnerTradeAmount).isEqualTo(결과값.content[0].partnerTradeAmount + 결과값.content[1].partnerTradeAmount) },
            { assertThat(결과값.sum.partnerDiscounts).isEqualTo(결과값.content[0].partnerDiscounts + 결과값.content[1].partnerDiscounts) },
            { assertThat(결과값.sum.partnerPaymentFees).isEqualTo(결과값.content[0].partnerPaymentFees + 결과값.content[1].partnerPaymentFees) },
            { assertThat(결과값.sum.partnerPaymentAmount).isEqualTo(결과값.content[0].partnerPaymentAmount + 결과값.content[1].partnerPaymentAmount) },
            { assertThat(결과값.sum.partnerSellFees).isEqualTo(결과값.content[0].partnerSellFees + 결과값.content[1].partnerSellFees) },
            { assertThat(결과값.sum.partnerFees).isEqualTo(결과값.content[0].partnerFees + 결과값.content[1].partnerFees) },
            { assertThat(결과값.sum.partnerSellGrants).isEqualTo(결과값.content[0].partnerSellGrants + 결과값.content[1].partnerSellGrants) },
            { assertThat(결과값.sum.partnerProfitMargin).isEqualTo(0.0) },
            { assertThat(결과값.sum.salesExcludedVAT).isEqualTo(결과값.content[0].salesExcludedVAT + 결과값.content[1].salesExcludedVAT) },
            { assertThat(결과값.sum.originalPriceExcludedVAT).isEqualTo(결과값.content[0].originalPriceExcludedVAT + 결과값.content[1].originalPriceExcludedVAT) },
            { assertThat(결과값.sum.profitExcludedVAT).isEqualTo(결과값.content[0].profitExcludedVAT + 결과값.content[1].profitExcludedVAT) },
            { assertThat(결과값.sum.profitMarginExcludedVAT).isEqualTo(0.0) }
        )

        // then(평균)
        assertAll(
            { assertThat(결과값.average.sellQuantity).isEqualTo((결과값.content[0].sellQuantity + 결과값.content[1].sellQuantity) / 2) },
            { assertThat(결과값.average.sellAmount).isEqualTo((결과값.content[0].sellAmount + 결과값.content[1].sellAmount) / 2) },
            { assertThat(결과값.average.refundQuantity).isEqualTo((결과값.content[0].refundQuantity + 결과값.content[1].refundQuantity) / 2) },
            { assertThat(결과값.average.refundAmount).isEqualTo((결과값.content[0].refundAmount + 결과값.content[1].refundAmount) / 2) },
            { assertThat(결과값.average.exchangeQuantity).isEqualTo((결과값.content[0].exchangeQuantity + 결과값.content[1].exchangeQuantity) / 2) },
            { assertThat(결과값.average.exchangeAmount).isEqualTo((결과값.content[0].exchangeAmount + 결과값.content[1].exchangeAmount) / 2) },
            { assertThat(결과값.average.tradeQuantity).isEqualTo((결과값.content[0].tradeQuantity + 결과값.content[1].tradeQuantity) / 2) },
            { assertThat(결과값.average.tradeAmount).isEqualTo((결과값.content[0].tradeAmount + 결과값.content[1].tradeAmount) / 2) },
            { assertThat(결과값.average.memberDiscounts).isEqualTo((결과값.content[0].memberDiscounts + 결과값.content[1].memberDiscounts) / 2) },
            { assertThat(결과값.average.affiliateDiscounts).isEqualTo((결과값.content[0].affiliateDiscounts + 결과값.content[1].affiliateDiscounts) / 2) },
            { assertThat(결과값.average.otherDiscounts).isEqualTo((결과값.content[0].otherDiscounts + 결과값.content[1].otherDiscounts) / 2) },
            { assertThat(결과값.average.couponDiscounts).isEqualTo((결과값.content[0].couponDiscounts + 결과값.content[1].couponDiscounts) / 2) },
            { assertThat(결과값.average.partnerCouponDiscounts).isEqualTo((결과값.content[0].partnerCouponDiscounts + 결과값.content[1].partnerCouponDiscounts) / 2) },
            { assertThat(결과값.average.pointDiscounts).isEqualTo((결과값.content[0].pointDiscounts + 결과값.content[1].pointDiscounts) / 2) },
            { assertThat(결과값.average.prePointDiscounts).isEqualTo((결과값.content[0].prePointDiscounts + 결과값.content[1].prePointDiscounts) / 2) },
            { assertThat(결과값.average.cartDiscounts).isEqualTo((결과값.content[0].cartDiscounts + 결과값.content[1].cartDiscounts) / 2) },
            { assertThat(결과값.average.groupDiscounts).isEqualTo((결과값.content[0].groupDiscounts + 결과값.content[1].groupDiscounts) / 2) },
            { assertThat(결과값.average.totalDiscounts).isEqualTo((결과값.content[0].totalDiscounts + 결과값.content[1].totalDiscounts) / 2) },
            {
                assertThat(결과값.average.discountRate).isEqualTo(
                    String.format(
                        "%.2f",
                        (결과값.content[0].discountRate + 결과값.content[1].discountRate) / 2
                    ).toDouble()
                )
            },
            { assertThat(결과값.average.paymentFees).isEqualTo((결과값.content[0].paymentFees + 결과값.content[1].paymentFees) / 2) },
            { assertThat(결과값.average.paymentAmount).isEqualTo((결과값.content[0].paymentAmount + 결과값.content[1].paymentAmount) / 2) },
            { assertThat(결과값.average.sales).isEqualTo((결과값.content[0].sales + 결과값.content[1].sales) / 2) },
            { assertThat(결과값.average.originalPrice).isEqualTo((결과값.content[0].originalPrice + 결과값.content[1].originalPrice) / 2) },
            { assertThat(결과값.average.profit).isEqualTo((결과값.content[0].profit + 결과값.content[1].profit) / 2) },
            {
                assertThat(결과값.average.profitMargin).isEqualTo(
                    String.format(
                        "%.2f",
                        (결과값.content[0].profitMargin + 결과값.content[1].profitMargin) / 2
                    ).toDouble()
                )
            },
            { assertThat(결과값.average.purchasesTradeAmount).isEqualTo((결과값.content[0].purchasesTradeAmount + 결과값.content[1].purchasesTradeAmount) / 2) },
            { assertThat(결과값.average.purchasesDiscounts).isEqualTo((결과값.content[0].purchasesDiscounts + 결과값.content[1].purchasesDiscounts) / 2) },
            { assertThat(결과값.average.purchasesPaymentFees).isEqualTo((결과값.content[0].purchasesPaymentFees + 결과값.content[1].purchasesPaymentFees) / 2) },
            { assertThat(결과값.average.purchasesPaymentAmount).isEqualTo((결과값.content[0].purchasesPaymentAmount + 결과값.content[1].purchasesPaymentAmount) / 2) },
            { assertThat(결과값.average.purchasesOriginalPrice).isEqualTo((결과값.content[0].purchasesOriginalPrice + 결과값.content[1].purchasesOriginalPrice) / 2) },
            { assertThat(결과값.average.purchasesProfit).isEqualTo((결과값.content[0].purchasesProfit + 결과값.content[1].purchasesProfit) / 2) },
            {
                assertThat(결과값.average.purchasesProfitMargin).isEqualTo(
                    String.format(
                        "%.2f",
                        (결과값.content[0].purchasesProfitMargin + 결과값.content[1].purchasesProfitMargin) / 2
                    ).toDouble()
                )
            },
            {
                assertThat(결과값.average.purchasesRatio).isEqualTo(
                    String.format(
                        "%.2f",
                        (결과값.content[0].purchasesRatio + 결과값.content[1].purchasesRatio) / 2
                    ).toDouble()
                )
            },
            { assertThat(결과값.average.partnerTradeAmount).isEqualTo((결과값.content[0].partnerTradeAmount + 결과값.content[1].partnerTradeAmount) / 2) },
            { assertThat(결과값.average.partnerDiscounts).isEqualTo((결과값.content[0].partnerDiscounts + 결과값.content[1].partnerDiscounts) / 2) },
            { assertThat(결과값.average.partnerPaymentFees).isEqualTo((결과값.content[0].partnerPaymentFees + 결과값.content[1].partnerPaymentFees) / 2) },
            { assertThat(결과값.average.partnerPaymentAmount).isEqualTo((결과값.content[0].partnerPaymentAmount + 결과값.content[1].partnerPaymentAmount) / 2) },
            { assertThat(결과값.average.partnerSellFees).isEqualTo((결과값.content[0].partnerSellFees + 결과값.content[1].partnerSellFees) / 2) },
            { assertThat(결과값.average.partnerFees).isEqualTo((결과값.content[0].partnerFees + 결과값.content[1].partnerFees) / 2) },
            { assertThat(결과값.average.partnerSellGrants).isEqualTo((결과값.content[0].partnerSellGrants + 결과값.content[1].partnerSellGrants) / 2) },
            {
                assertThat(결과값.average.partnerProfitMargin).isEqualTo(
                    String.format(
                        "%.2f",
                        (결과값.content[0].partnerProfitMargin + 결과값.content[1].partnerProfitMargin) / 2
                    ).toDouble()
                )
            },
            { assertThat(결과값.average.salesExcludedVAT).isEqualTo((결과값.content[0].salesExcludedVAT + 결과값.content[1].salesExcludedVAT) / 2) },
            { assertThat(결과값.average.originalPriceExcludedVAT).isEqualTo((결과값.content[0].originalPriceExcludedVAT + 결과값.content[1].originalPriceExcludedVAT) / 2) },
            { assertThat(결과값.average.profitExcludedVAT).isEqualTo((결과값.content[0].profitExcludedVAT + 결과값.content[1].profitExcludedVAT) / 2) },
            {
                assertThat(결과값.average.profitMarginExcludedVAT).isEqualTo(
                    String.format(
                        "%.2f",
                        (결과값.content[0].profitMarginExcludedVAT + 결과값.content[1].profitMarginExcludedVAT) / 2
                    ).toDouble()
                )
            }
        )
    }
}
