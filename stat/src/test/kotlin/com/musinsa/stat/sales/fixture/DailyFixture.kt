package com.musinsa.stat.sales.fixture

import com.musinsa.stat.sales.dto.Daily
import com.musinsa.stat.util.ObjectMapperFactory

object DailyFixture {
    val DAILY_20230505_STRING = """
        {
            "date": "20230505",
            "sellQuantity": 194111,
            "sellAmount": 10157355964,
            "refundQuantity": -14271,
            "refundAmount": -1136867777,
            "exchangeQuantity": -265,
            "exchangeAmount": -13067650,
            "tradeQuantity": 179575,
            "tradeAmount": 9007420537,
            "memberDiscounts": 128814521,
            "affiliateDiscounts": 0,
            "otherDiscounts": 0,
            "couponDiscounts": 1059653863,
            "partnerCouponDiscounts": 42642058,
            "pointDiscounts": 181651357,
            "prePointDiscounts": 58831161,
            "cartDiscounts": 1598914,
            "groupDiscounts": 417900,
            "totalDiscounts": 1473609774,
            "discountRate": 16.36,
            "paymentFees": 3300067,
            "paymentAmount": 7537110830,
            "sales": 5237289163,
            "originalPrice": 3327393056,
            "profit": 1907266294,
            "profitMargin": 21.17,
            "purchasesTradeAmount": 5793722387,
            "purchasesDiscounts": 954514074,
            "purchasesPaymentFees": 1889744,
            "purchasesPaymentAmount": 4841098057,
            "purchasesOriginalPrice": 3327393056,
            "purchasesProfit": 1513705001,
            "purchasesProfitMargin": 26.13,
            "purchasesRatio": 64.32166,
            "partnerTradeAmount": 3213698150,
            "partnerDiscounts": 519095700,
            "partnerPaymentFees": 1410323,
            "partnerPaymentAmount": 2696012773,
            "partnerSellFees": 869080232,
            "partnerFees": 394780783,
            "partnerSellGrants": 2629813,
            "partnerProfitMargin": 12.25,
            "salesExcludedVAT": 4761171967,
            "originalPriceExcludedVAT": 3024902779,
            "profitExcludedVAT": 1736269189,
            "profitMarginExcludedVAT": 19.28
        }
        """.trimIndent()

    val DAILY_20230506_STRING = """
            {
            "date": "20230506",
            "sellQuantity": 187905,
            "sellAmount": 9754706839,
            "refundQuantity": -13648,
            "refundAmount": -1122106117,
            "exchangeQuantity": 0,
            "exchangeAmount": 0,
            "tradeQuantity": 174257,
            "tradeAmount": 8632600722,
            "memberDiscounts": 124531356,
            "affiliateDiscounts": 0,
            "otherDiscounts": 0,
            "couponDiscounts": 1022903732,
            "partnerCouponDiscounts": 36597922,
            "pointDiscounts": 173843599,
            "prePointDiscounts": 55781306,
            "cartDiscounts": 4218535,
            "groupDiscounts": 0,
            "totalDiscounts": 1417876450,
            "discountRate": 16.42,
            "paymentFees": 3673214,
            "paymentAmount": 7218397486,
            "sales": 5135503603,
            "originalPrice": 3182872386,
            "profit": 1948473685,
            "profitMargin": 22.57,
            "purchasesTradeAmount": 5710129850,
            "purchasesDiscounts": 934840227,
            "purchasesPaymentFees": 2017304,
            "purchasesPaymentAmount": 4777306927,
            "purchasesOriginalPrice": 3182872386,
            "purchasesProfit": 1594434541,
            "purchasesProfitMargin": 27.92,
            "purchasesRatio": 66.14611,
            "partnerTradeAmount": 2922470872,
            "partnerDiscounts": 483036223,
            "partnerPaymentFees": 1655910,
            "partnerPaymentAmount": 2441090559,
            "partnerSellFees": 799297463,
            "partnerFees": 356540766,
            "partnerSellGrants": 4157532,
            "partnerProfitMargin": 12.11,
            "salesExcludedVAT": 4668639640,
            "originalPriceExcludedVAT": 2893520352,
            "profitExcludedVAT": 1775119289,
            "profitMarginExcludedVAT": 20.56
        }
        """.trimIndent()

    val DAILY_20230505 =
        ObjectMapperFactory.readValue(DAILY_20230505_STRING, Daily::class.java)

    val DAILY_20230506 =
        ObjectMapperFactory.readValue(DAILY_20230506_STRING, Daily::class.java)
}