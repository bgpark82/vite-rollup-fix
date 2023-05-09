package com.musinsa.stat.sales.fixture

object Query {
    val SAMPLE_QUERY = """
        SELECT
          om.ord_state_date AS `date`
          ,SUM(om.sell_qty) AS `sellQuantity`
          ,SUM(om.sell_amt) AS `sellAmount`
          ,SUM(om.refund_qty) AS `refundQuantity`
          ,SUM(om.refund_amt) AS `refundAmount`
          ,SUM(om.exchange_qty) AS `exchangeQuantity`
          ,SUM(om.exchange_amt) AS `exchangeAmount`
          ,SUM(om.sell_sub_clm_qty) AS `tradeQuantity`
          ,SUM(om.sell_sub_clm_amt) AS `tradeAmount`
          ,SUM(IF(om.dc_type=1, IF(om.gmv_state IN ('1000', '5000'), om.dc_apply_amt, -1*om.dc_apply_amt), 0)) as `memberDiscounts`
          ,SUM(IF(om.dc_type=2, IF(om.gmv_state IN ('1000', '5000'), om.dc_apply_amt, -1*om.dc_apply_amt), 0)) as `affiliateDiscounts`
          ,SUM(IF(om.dc_type NOT IN (1,2), IF(om.gmv_state IN ('1000', '5000'), om.dc_apply_amt, -1*om.dc_apply_amt), 0)) as `otherDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.coupon_apply_amt-om.coupon_partner_apply_amt, -1*om.coupon_apply_amt-om.coupon_partner_apply_amt)) as `couponDiscounts`
          ,SUM(om.coupon_partner_apply_amt) as `partnerCouponDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.point_apply_amt-om.pre_point_amt, -1*(om.point_apply_amt-om.pre_point_amt))) as `pointDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.pre_point_amt, -1*pre_point_amt)) as `prePointDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.cart_dc_amt, -1*cart_dc_amt)) as `cartDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.group_dc_amt, -1*group_dc_amt)) as `groupDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.total_dc_amt, -1*total_dc_amt)) as `totalDiscounts`
          ,ROUND(SUM(IF(om.gmv_state IN ('1000', '5000'), om.total_dc_amt, -1*total_dc_amt))/SUM(om.price*om.qty)*100, 2) `discountRate`
          ,SUM(om.pay_fee) as `paymentFees`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.recv_amt, -1*om.recv_amt)) as `paymentAmount`
          ,SUM(om.revenue) as `sales`
          ,SUM(om.head_wonga) as `originalPrice`
          ,SUM(om.profit) as `profit`
          ,ROUND(IFNULL(SUM(om.profit)/SUM(om.price*om.qty)*100, 0), 2) as `profitMargin`
          ,SUM(om.head_amt) as `purchasesTradeAmount`
          ,SUM(IF(om.ord_com_type=2, 0, (om.point_apply_amt+om.dc_apply_amt+om.coupon_apply_amt+om.cart_dc_amt+om.group_dc_amt)*IF(om.gmv_state IN ('1000', '5000'),1,-1))) as `purchasesDiscounts`
          ,SUM(om.head_pay_fee) as `purchasesPaymentFees`
          ,SUM(IF(om.ord_com_type=2, 0, om.recv_amt*IF(om.gmv_state IN ('1000', '5000'), 1, -1))) as `purchasesPaymentAmount`
          ,SUM(om.head_wonga) as `purchasesOriginalPrice`
          ,SUM(om.head_profit) as `purchasesProfit`
          ,ROUND(IFNULL(SUM(om.head_profit)/SUM(om.head_amt)*100, 0), 2) as `purchasesProfitMargin`
          ,SUM(om.head_amt)/SUM(om.price*om.qty) * 100 as `purchasesRatio`
          ,SUM(om.partner_amt) as `partnerTradeAmount`
          ,SUM(IF(om.ord_com_type=2, (om.point_apply_amt+om.dc_apply_amt+om.coupon_apply_amt+om.cart_dc_amt+om.group_dc_amt)*IF(om.gmv_state IN ('1000', '5000'),1,-1), 0)) as `partnerDiscounts`
          ,SUM(om.partner_pay_fee) as `partnerPaymentFees`
          ,SUM(IF(om.ord_com_type=2, om.recv_amt*IF(om.gmv_state IN ('1000', '5000'), 1, -1), 0)) as `partnerPaymentAmount`
          ,SUM(om.partner_sale_fee) as `partnerSellFees`
          ,SUM(om.partner_fee) as `partnerFees`
          ,SUM(om.partner_support_amt) as `partnerSellGrants`
          ,ROUND(IFNULL(SUM(om.partner_profit)/SUM(om.partner_amt) * 100, 0), 2) as `partnerProfitMargin`
          ,CEIL(SUM(om.not_included_vat_revenue)) as `salesExcludedVAT`
          ,CEIL(SUM(om.not_included_vat_wonga)) as `originalPriceExcludedVAT`
          ,CEIL(SUM(om.not_included_vat_profit)) as `profitExcludedVAT`
          ,ROUND(CEIL(SUM((om.not_included_vat_revenue)-(om.head_wonga/11*10)))/CEIL(SUM(om.price*om.qty))*100, 2) as `profitMarginExcludedVAT`

        FROM datamart.datamart.orders_merged om
          JOIN datamart.datamart.goods_tags as gt
            ON om.goods_no = gt.goods_no
          JOIN datamart.datamart.coupon as c
            ON om.coupon_no = c.coupon_no
          JOIN datamart.datamart.specialty_goods as sg
            ON om.goods_no = sg.goods_no

        WHERE 
          -- 일자
          om.ord_state_date >= '{{startDate}}'
          AND om.ord_state_date <= '{{endDate}}'

          -- 태그(String List)
          AND gt.tag IN ({{tag}})

          -- 매출시점
          AND if('{{salesStart}}'='SHIPPING_REQUEST', om.state_order, om.state_order_done) = True

          -- 업체
          AND om.com_id = '{{partnerId}}'

          -- 카테고리
          AND om.small_nm = '{{category}}'

          -- 스타일넘버
          AND om.style_no = '{{styleNumber}}'

          -- 상품코드
          AND om.goods_no = '{{goodsNumber}}'

          -- 브랜드
          AND om.brand = '{{brandId}}'

          -- 쿠폰
          AND c.coupon_no = '{{couponNumber}}'

          -- 광고코드
          AND om.ad_cd = '{{adCode}}'

          -- 전문관코드
          AND sg.specialty_cd = '{{specialtyCode}}'

          -- 담당MD
          AND om.md_id = '{{mdId}}'

        GROUP BY 1

        ORDER BY `{{orderBy}}` ASC

        LIMIT {{size}}

        OFFSET {{number}}
    """.trimIndent()

    val SAMPLE_QUERY_SET_START_END_DATE = """
        SELECT
          om.ord_state_date AS `date`
          ,SUM(om.sell_qty) AS `sellQuantity`
          ,SUM(om.sell_amt) AS `sellAmount`
          ,SUM(om.refund_qty) AS `refundQuantity`
          ,SUM(om.refund_amt) AS `refundAmount`
          ,SUM(om.exchange_qty) AS `exchangeQuantity`
          ,SUM(om.exchange_amt) AS `exchangeAmount`
          ,SUM(om.sell_sub_clm_qty) AS `tradeQuantity`
          ,SUM(om.sell_sub_clm_amt) AS `tradeAmount`
          ,SUM(IF(om.dc_type=1, IF(om.gmv_state IN ('1000', '5000'), om.dc_apply_amt, -1*om.dc_apply_amt), 0)) as `memberDiscounts`
          ,SUM(IF(om.dc_type=2, IF(om.gmv_state IN ('1000', '5000'), om.dc_apply_amt, -1*om.dc_apply_amt), 0)) as `affiliateDiscounts`
          ,SUM(IF(om.dc_type NOT IN (1,2), IF(om.gmv_state IN ('1000', '5000'), om.dc_apply_amt, -1*om.dc_apply_amt), 0)) as `otherDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.coupon_apply_amt-om.coupon_partner_apply_amt, -1*om.coupon_apply_amt-om.coupon_partner_apply_amt)) as `couponDiscounts`
          ,SUM(om.coupon_partner_apply_amt) as `partnerCouponDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.point_apply_amt-om.pre_point_amt, -1*(om.point_apply_amt-om.pre_point_amt))) as `pointDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.pre_point_amt, -1*pre_point_amt)) as `prePointDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.cart_dc_amt, -1*cart_dc_amt)) as `cartDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.group_dc_amt, -1*group_dc_amt)) as `groupDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.total_dc_amt, -1*total_dc_amt)) as `totalDiscounts`
          ,ROUND(SUM(IF(om.gmv_state IN ('1000', '5000'), om.total_dc_amt, -1*total_dc_amt))/SUM(om.price*om.qty)*100, 2) `discountRate`
          ,SUM(om.pay_fee) as `paymentFees`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.recv_amt, -1*om.recv_amt)) as `paymentAmount`
          ,SUM(om.revenue) as `sales`
          ,SUM(om.head_wonga) as `originalPrice`
          ,SUM(om.profit) as `profit`
          ,ROUND(IFNULL(SUM(om.profit)/SUM(om.price*om.qty)*100, 0), 2) as `profitMargin`
          ,SUM(om.head_amt) as `purchasesTradeAmount`
          ,SUM(IF(om.ord_com_type=2, 0, (om.point_apply_amt+om.dc_apply_amt+om.coupon_apply_amt+om.cart_dc_amt+om.group_dc_amt)*IF(om.gmv_state IN ('1000', '5000'),1,-1))) as `purchasesDiscounts`
          ,SUM(om.head_pay_fee) as `purchasesPaymentFees`
          ,SUM(IF(om.ord_com_type=2, 0, om.recv_amt*IF(om.gmv_state IN ('1000', '5000'), 1, -1))) as `purchasesPaymentAmount`
          ,SUM(om.head_wonga) as `purchasesOriginalPrice`
          ,SUM(om.head_profit) as `purchasesProfit`
          ,ROUND(IFNULL(SUM(om.head_profit)/SUM(om.head_amt)*100, 0), 2) as `purchasesProfitMargin`
          ,SUM(om.head_amt)/SUM(om.price*om.qty) * 100 as `purchasesRatio`
          ,SUM(om.partner_amt) as `partnerTradeAmount`
          ,SUM(IF(om.ord_com_type=2, (om.point_apply_amt+om.dc_apply_amt+om.coupon_apply_amt+om.cart_dc_amt+om.group_dc_amt)*IF(om.gmv_state IN ('1000', '5000'),1,-1), 0)) as `partnerDiscounts`
          ,SUM(om.partner_pay_fee) as `partnerPaymentFees`
          ,SUM(IF(om.ord_com_type=2, om.recv_amt*IF(om.gmv_state IN ('1000', '5000'), 1, -1), 0)) as `partnerPaymentAmount`
          ,SUM(om.partner_sale_fee) as `partnerSellFees`
          ,SUM(om.partner_fee) as `partnerFees`
          ,SUM(om.partner_support_amt) as `partnerSellGrants`
          ,ROUND(IFNULL(SUM(om.partner_profit)/SUM(om.partner_amt) * 100, 0), 2) as `partnerProfitMargin`
          ,CEIL(SUM(om.not_included_vat_revenue)) as `salesExcludedVAT`
          ,CEIL(SUM(om.not_included_vat_wonga)) as `originalPriceExcludedVAT`
          ,CEIL(SUM(om.not_included_vat_profit)) as `profitExcludedVAT`
          ,ROUND(CEIL(SUM((om.not_included_vat_revenue)-(om.head_wonga/11*10)))/CEIL(SUM(om.price*om.qty))*100, 2) as `profitMarginExcludedVAT`

        FROM datamart.datamart.orders_merged om
          JOIN datamart.datamart.goods_tags as gt
            ON om.goods_no = gt.goods_no
          JOIN datamart.datamart.coupon as c
            ON om.coupon_no = c.coupon_no
          JOIN datamart.datamart.specialty_goods as sg
            ON om.goods_no = sg.goods_no

        WHERE 
          -- 일자
          om.ord_state_date >= '20230501'
          AND om.ord_state_date <= '20230509'

          -- 태그(String List)
          AND gt.tag IN ({{tag}})

          -- 매출시점
          AND if('{{salesStart}}'='SHIPPING_REQUEST', om.state_order, om.state_order_done) = True

          -- 업체
          AND om.com_id = '{{partnerId}}'

          -- 카테고리
          AND om.small_nm = '{{category}}'

          -- 스타일넘버
          AND om.style_no = '{{styleNumber}}'

          -- 상품코드
          AND om.goods_no = '{{goodsNumber}}'

          -- 브랜드
          AND om.brand = '{{brandId}}'

          -- 쿠폰
          AND c.coupon_no = '{{couponNumber}}'

          -- 광고코드
          AND om.ad_cd = '{{adCode}}'

          -- 전문관코드
          AND sg.specialty_cd = '{{specialtyCode}}'

          -- 담당MD
          AND om.md_id = '{{mdId}}'

        GROUP BY 1

        ORDER BY `{{orderBy}}` ASC

        LIMIT {{size}}

        OFFSET {{number}}
    """.trimIndent()

    val SAMPLE_QUERY_SET_TAG = """
        SELECT
          om.ord_state_date AS `date`
          ,SUM(om.sell_qty) AS `sellQuantity`
          ,SUM(om.sell_amt) AS `sellAmount`
          ,SUM(om.refund_qty) AS `refundQuantity`
          ,SUM(om.refund_amt) AS `refundAmount`
          ,SUM(om.exchange_qty) AS `exchangeQuantity`
          ,SUM(om.exchange_amt) AS `exchangeAmount`
          ,SUM(om.sell_sub_clm_qty) AS `tradeQuantity`
          ,SUM(om.sell_sub_clm_amt) AS `tradeAmount`
          ,SUM(IF(om.dc_type=1, IF(om.gmv_state IN ('1000', '5000'), om.dc_apply_amt, -1*om.dc_apply_amt), 0)) as `memberDiscounts`
          ,SUM(IF(om.dc_type=2, IF(om.gmv_state IN ('1000', '5000'), om.dc_apply_amt, -1*om.dc_apply_amt), 0)) as `affiliateDiscounts`
          ,SUM(IF(om.dc_type NOT IN (1,2), IF(om.gmv_state IN ('1000', '5000'), om.dc_apply_amt, -1*om.dc_apply_amt), 0)) as `otherDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.coupon_apply_amt-om.coupon_partner_apply_amt, -1*om.coupon_apply_amt-om.coupon_partner_apply_amt)) as `couponDiscounts`
          ,SUM(om.coupon_partner_apply_amt) as `partnerCouponDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.point_apply_amt-om.pre_point_amt, -1*(om.point_apply_amt-om.pre_point_amt))) as `pointDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.pre_point_amt, -1*pre_point_amt)) as `prePointDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.cart_dc_amt, -1*cart_dc_amt)) as `cartDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.group_dc_amt, -1*group_dc_amt)) as `groupDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.total_dc_amt, -1*total_dc_amt)) as `totalDiscounts`
          ,ROUND(SUM(IF(om.gmv_state IN ('1000', '5000'), om.total_dc_amt, -1*total_dc_amt))/SUM(om.price*om.qty)*100, 2) `discountRate`
          ,SUM(om.pay_fee) as `paymentFees`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.recv_amt, -1*om.recv_amt)) as `paymentAmount`
          ,SUM(om.revenue) as `sales`
          ,SUM(om.head_wonga) as `originalPrice`
          ,SUM(om.profit) as `profit`
          ,ROUND(IFNULL(SUM(om.profit)/SUM(om.price*om.qty)*100, 0), 2) as `profitMargin`
          ,SUM(om.head_amt) as `purchasesTradeAmount`
          ,SUM(IF(om.ord_com_type=2, 0, (om.point_apply_amt+om.dc_apply_amt+om.coupon_apply_amt+om.cart_dc_amt+om.group_dc_amt)*IF(om.gmv_state IN ('1000', '5000'),1,-1))) as `purchasesDiscounts`
          ,SUM(om.head_pay_fee) as `purchasesPaymentFees`
          ,SUM(IF(om.ord_com_type=2, 0, om.recv_amt*IF(om.gmv_state IN ('1000', '5000'), 1, -1))) as `purchasesPaymentAmount`
          ,SUM(om.head_wonga) as `purchasesOriginalPrice`
          ,SUM(om.head_profit) as `purchasesProfit`
          ,ROUND(IFNULL(SUM(om.head_profit)/SUM(om.head_amt)*100, 0), 2) as `purchasesProfitMargin`
          ,SUM(om.head_amt)/SUM(om.price*om.qty) * 100 as `purchasesRatio`
          ,SUM(om.partner_amt) as `partnerTradeAmount`
          ,SUM(IF(om.ord_com_type=2, (om.point_apply_amt+om.dc_apply_amt+om.coupon_apply_amt+om.cart_dc_amt+om.group_dc_amt)*IF(om.gmv_state IN ('1000', '5000'),1,-1), 0)) as `partnerDiscounts`
          ,SUM(om.partner_pay_fee) as `partnerPaymentFees`
          ,SUM(IF(om.ord_com_type=2, om.recv_amt*IF(om.gmv_state IN ('1000', '5000'), 1, -1), 0)) as `partnerPaymentAmount`
          ,SUM(om.partner_sale_fee) as `partnerSellFees`
          ,SUM(om.partner_fee) as `partnerFees`
          ,SUM(om.partner_support_amt) as `partnerSellGrants`
          ,ROUND(IFNULL(SUM(om.partner_profit)/SUM(om.partner_amt) * 100, 0), 2) as `partnerProfitMargin`
          ,CEIL(SUM(om.not_included_vat_revenue)) as `salesExcludedVAT`
          ,CEIL(SUM(om.not_included_vat_wonga)) as `originalPriceExcludedVAT`
          ,CEIL(SUM(om.not_included_vat_profit)) as `profitExcludedVAT`
          ,ROUND(CEIL(SUM((om.not_included_vat_revenue)-(om.head_wonga/11*10)))/CEIL(SUM(om.price*om.qty))*100, 2) as `profitMarginExcludedVAT`

        FROM datamart.datamart.orders_merged om
          JOIN datamart.datamart.goods_tags as gt
            ON om.goods_no = gt.goods_no
          JOIN datamart.datamart.coupon as c
            ON om.coupon_no = c.coupon_no
          JOIN datamart.datamart.specialty_goods as sg
            ON om.goods_no = sg.goods_no

        WHERE 
          -- 일자
          om.ord_state_date >= '{{startDate}}'
          AND om.ord_state_date <= '{{endDate}}'

          -- 태그(String List)
          AND gt.tag IN ('청바지', '반소매티')

          -- 매출시점
          AND if('{{salesStart}}'='SHIPPING_REQUEST', om.state_order, om.state_order_done) = True

          -- 업체
          AND om.com_id = '{{partnerId}}'

          -- 카테고리
          AND om.small_nm = '{{category}}'

          -- 스타일넘버
          AND om.style_no = '{{styleNumber}}'

          -- 상품코드
          AND om.goods_no = '{{goodsNumber}}'

          -- 브랜드
          AND om.brand = '{{brandId}}'

          -- 쿠폰
          AND c.coupon_no = '{{couponNumber}}'

          -- 광고코드
          AND om.ad_cd = '{{adCode}}'

          -- 전문관코드
          AND sg.specialty_cd = '{{specialtyCode}}'

          -- 담당MD
          AND om.md_id = '{{mdId}}'

        GROUP BY 1

        ORDER BY `{{orderBy}}` ASC

        LIMIT {{size}}

        OFFSET {{number}}
    """.trimIndent()

    val SAMPLE_QUERY_EMPTY_TAG = """
        SELECT
          om.ord_state_date AS `date`
          ,SUM(om.sell_qty) AS `sellQuantity`
          ,SUM(om.sell_amt) AS `sellAmount`
          ,SUM(om.refund_qty) AS `refundQuantity`
          ,SUM(om.refund_amt) AS `refundAmount`
          ,SUM(om.exchange_qty) AS `exchangeQuantity`
          ,SUM(om.exchange_amt) AS `exchangeAmount`
          ,SUM(om.sell_sub_clm_qty) AS `tradeQuantity`
          ,SUM(om.sell_sub_clm_amt) AS `tradeAmount`
          ,SUM(IF(om.dc_type=1, IF(om.gmv_state IN ('1000', '5000'), om.dc_apply_amt, -1*om.dc_apply_amt), 0)) as `memberDiscounts`
          ,SUM(IF(om.dc_type=2, IF(om.gmv_state IN ('1000', '5000'), om.dc_apply_amt, -1*om.dc_apply_amt), 0)) as `affiliateDiscounts`
          ,SUM(IF(om.dc_type NOT IN (1,2), IF(om.gmv_state IN ('1000', '5000'), om.dc_apply_amt, -1*om.dc_apply_amt), 0)) as `otherDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.coupon_apply_amt-om.coupon_partner_apply_amt, -1*om.coupon_apply_amt-om.coupon_partner_apply_amt)) as `couponDiscounts`
          ,SUM(om.coupon_partner_apply_amt) as `partnerCouponDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.point_apply_amt-om.pre_point_amt, -1*(om.point_apply_amt-om.pre_point_amt))) as `pointDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.pre_point_amt, -1*pre_point_amt)) as `prePointDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.cart_dc_amt, -1*cart_dc_amt)) as `cartDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.group_dc_amt, -1*group_dc_amt)) as `groupDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.total_dc_amt, -1*total_dc_amt)) as `totalDiscounts`
          ,ROUND(SUM(IF(om.gmv_state IN ('1000', '5000'), om.total_dc_amt, -1*total_dc_amt))/SUM(om.price*om.qty)*100, 2) `discountRate`
          ,SUM(om.pay_fee) as `paymentFees`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.recv_amt, -1*om.recv_amt)) as `paymentAmount`
          ,SUM(om.revenue) as `sales`
          ,SUM(om.head_wonga) as `originalPrice`
          ,SUM(om.profit) as `profit`
          ,ROUND(IFNULL(SUM(om.profit)/SUM(om.price*om.qty)*100, 0), 2) as `profitMargin`
          ,SUM(om.head_amt) as `purchasesTradeAmount`
          ,SUM(IF(om.ord_com_type=2, 0, (om.point_apply_amt+om.dc_apply_amt+om.coupon_apply_amt+om.cart_dc_amt+om.group_dc_amt)*IF(om.gmv_state IN ('1000', '5000'),1,-1))) as `purchasesDiscounts`
          ,SUM(om.head_pay_fee) as `purchasesPaymentFees`
          ,SUM(IF(om.ord_com_type=2, 0, om.recv_amt*IF(om.gmv_state IN ('1000', '5000'), 1, -1))) as `purchasesPaymentAmount`
          ,SUM(om.head_wonga) as `purchasesOriginalPrice`
          ,SUM(om.head_profit) as `purchasesProfit`
          ,ROUND(IFNULL(SUM(om.head_profit)/SUM(om.head_amt)*100, 0), 2) as `purchasesProfitMargin`
          ,SUM(om.head_amt)/SUM(om.price*om.qty) * 100 as `purchasesRatio`
          ,SUM(om.partner_amt) as `partnerTradeAmount`
          ,SUM(IF(om.ord_com_type=2, (om.point_apply_amt+om.dc_apply_amt+om.coupon_apply_amt+om.cart_dc_amt+om.group_dc_amt)*IF(om.gmv_state IN ('1000', '5000'),1,-1), 0)) as `partnerDiscounts`
          ,SUM(om.partner_pay_fee) as `partnerPaymentFees`
          ,SUM(IF(om.ord_com_type=2, om.recv_amt*IF(om.gmv_state IN ('1000', '5000'), 1, -1), 0)) as `partnerPaymentAmount`
          ,SUM(om.partner_sale_fee) as `partnerSellFees`
          ,SUM(om.partner_fee) as `partnerFees`
          ,SUM(om.partner_support_amt) as `partnerSellGrants`
          ,ROUND(IFNULL(SUM(om.partner_profit)/SUM(om.partner_amt) * 100, 0), 2) as `partnerProfitMargin`
          ,CEIL(SUM(om.not_included_vat_revenue)) as `salesExcludedVAT`
          ,CEIL(SUM(om.not_included_vat_wonga)) as `originalPriceExcludedVAT`
          ,CEIL(SUM(om.not_included_vat_profit)) as `profitExcludedVAT`
          ,ROUND(CEIL(SUM((om.not_included_vat_revenue)-(om.head_wonga/11*10)))/CEIL(SUM(om.price*om.qty))*100, 2) as `profitMarginExcludedVAT`

        FROM datamart.datamart.orders_merged om
          JOIN datamart.datamart.goods_tags as gt
            ON om.goods_no = gt.goods_no
          JOIN datamart.datamart.coupon as c
            ON om.coupon_no = c.coupon_no
          JOIN datamart.datamart.specialty_goods as sg
            ON om.goods_no = sg.goods_no

        WHERE 
          -- 일자
          om.ord_state_date >= '{{startDate}}'
          AND om.ord_state_date <= '{{endDate}}'

          -- 태그(String List)
        --  AND gt.tag IN ({{tag}})

          -- 매출시점
          AND if('{{salesStart}}'='SHIPPING_REQUEST', om.state_order, om.state_order_done) = True

          -- 업체
          AND om.com_id = '{{partnerId}}'

          -- 카테고리
          AND om.small_nm = '{{category}}'

          -- 스타일넘버
          AND om.style_no = '{{styleNumber}}'

          -- 상품코드
          AND om.goods_no = '{{goodsNumber}}'

          -- 브랜드
          AND om.brand = '{{brandId}}'

          -- 쿠폰
          AND c.coupon_no = '{{couponNumber}}'

          -- 광고코드
          AND om.ad_cd = '{{adCode}}'

          -- 전문관코드
          AND sg.specialty_cd = '{{specialtyCode}}'

          -- 담당MD
          AND om.md_id = '{{mdId}}'

        GROUP BY 1

        ORDER BY `{{orderBy}}` ASC

        LIMIT {{size}}

        OFFSET {{number}}
    """.trimIndent()

    val SAMPLE_QUERY_SET_SALES_START = """
        SELECT
          om.ord_state_date AS `date`
          ,SUM(om.sell_qty) AS `sellQuantity`
          ,SUM(om.sell_amt) AS `sellAmount`
          ,SUM(om.refund_qty) AS `refundQuantity`
          ,SUM(om.refund_amt) AS `refundAmount`
          ,SUM(om.exchange_qty) AS `exchangeQuantity`
          ,SUM(om.exchange_amt) AS `exchangeAmount`
          ,SUM(om.sell_sub_clm_qty) AS `tradeQuantity`
          ,SUM(om.sell_sub_clm_amt) AS `tradeAmount`
          ,SUM(IF(om.dc_type=1, IF(om.gmv_state IN ('1000', '5000'), om.dc_apply_amt, -1*om.dc_apply_amt), 0)) as `memberDiscounts`
          ,SUM(IF(om.dc_type=2, IF(om.gmv_state IN ('1000', '5000'), om.dc_apply_amt, -1*om.dc_apply_amt), 0)) as `affiliateDiscounts`
          ,SUM(IF(om.dc_type NOT IN (1,2), IF(om.gmv_state IN ('1000', '5000'), om.dc_apply_amt, -1*om.dc_apply_amt), 0)) as `otherDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.coupon_apply_amt-om.coupon_partner_apply_amt, -1*om.coupon_apply_amt-om.coupon_partner_apply_amt)) as `couponDiscounts`
          ,SUM(om.coupon_partner_apply_amt) as `partnerCouponDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.point_apply_amt-om.pre_point_amt, -1*(om.point_apply_amt-om.pre_point_amt))) as `pointDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.pre_point_amt, -1*pre_point_amt)) as `prePointDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.cart_dc_amt, -1*cart_dc_amt)) as `cartDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.group_dc_amt, -1*group_dc_amt)) as `groupDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.total_dc_amt, -1*total_dc_amt)) as `totalDiscounts`
          ,ROUND(SUM(IF(om.gmv_state IN ('1000', '5000'), om.total_dc_amt, -1*total_dc_amt))/SUM(om.price*om.qty)*100, 2) `discountRate`
          ,SUM(om.pay_fee) as `paymentFees`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.recv_amt, -1*om.recv_amt)) as `paymentAmount`
          ,SUM(om.revenue) as `sales`
          ,SUM(om.head_wonga) as `originalPrice`
          ,SUM(om.profit) as `profit`
          ,ROUND(IFNULL(SUM(om.profit)/SUM(om.price*om.qty)*100, 0), 2) as `profitMargin`
          ,SUM(om.head_amt) as `purchasesTradeAmount`
          ,SUM(IF(om.ord_com_type=2, 0, (om.point_apply_amt+om.dc_apply_amt+om.coupon_apply_amt+om.cart_dc_amt+om.group_dc_amt)*IF(om.gmv_state IN ('1000', '5000'),1,-1))) as `purchasesDiscounts`
          ,SUM(om.head_pay_fee) as `purchasesPaymentFees`
          ,SUM(IF(om.ord_com_type=2, 0, om.recv_amt*IF(om.gmv_state IN ('1000', '5000'), 1, -1))) as `purchasesPaymentAmount`
          ,SUM(om.head_wonga) as `purchasesOriginalPrice`
          ,SUM(om.head_profit) as `purchasesProfit`
          ,ROUND(IFNULL(SUM(om.head_profit)/SUM(om.head_amt)*100, 0), 2) as `purchasesProfitMargin`
          ,SUM(om.head_amt)/SUM(om.price*om.qty) * 100 as `purchasesRatio`
          ,SUM(om.partner_amt) as `partnerTradeAmount`
          ,SUM(IF(om.ord_com_type=2, (om.point_apply_amt+om.dc_apply_amt+om.coupon_apply_amt+om.cart_dc_amt+om.group_dc_amt)*IF(om.gmv_state IN ('1000', '5000'),1,-1), 0)) as `partnerDiscounts`
          ,SUM(om.partner_pay_fee) as `partnerPaymentFees`
          ,SUM(IF(om.ord_com_type=2, om.recv_amt*IF(om.gmv_state IN ('1000', '5000'), 1, -1), 0)) as `partnerPaymentAmount`
          ,SUM(om.partner_sale_fee) as `partnerSellFees`
          ,SUM(om.partner_fee) as `partnerFees`
          ,SUM(om.partner_support_amt) as `partnerSellGrants`
          ,ROUND(IFNULL(SUM(om.partner_profit)/SUM(om.partner_amt) * 100, 0), 2) as `partnerProfitMargin`
          ,CEIL(SUM(om.not_included_vat_revenue)) as `salesExcludedVAT`
          ,CEIL(SUM(om.not_included_vat_wonga)) as `originalPriceExcludedVAT`
          ,CEIL(SUM(om.not_included_vat_profit)) as `profitExcludedVAT`
          ,ROUND(CEIL(SUM((om.not_included_vat_revenue)-(om.head_wonga/11*10)))/CEIL(SUM(om.price*om.qty))*100, 2) as `profitMarginExcludedVAT`

        FROM datamart.datamart.orders_merged om
          JOIN datamart.datamart.goods_tags as gt
            ON om.goods_no = gt.goods_no
          JOIN datamart.datamart.coupon as c
            ON om.coupon_no = c.coupon_no
          JOIN datamart.datamart.specialty_goods as sg
            ON om.goods_no = sg.goods_no

        WHERE 
          -- 일자
          om.ord_state_date >= '{{startDate}}'
          AND om.ord_state_date <= '{{endDate}}'

          -- 태그(String List)
          AND gt.tag IN ({{tag}})

          -- 매출시점
          AND if('PURCHASE_CONFIRM'='SHIPPING_REQUEST', om.state_order, om.state_order_done) = True

          -- 업체
          AND om.com_id = '{{partnerId}}'

          -- 카테고리
          AND om.small_nm = '{{category}}'

          -- 스타일넘버
          AND om.style_no = '{{styleNumber}}'

          -- 상품코드
          AND om.goods_no = '{{goodsNumber}}'

          -- 브랜드
          AND om.brand = '{{brandId}}'

          -- 쿠폰
          AND c.coupon_no = '{{couponNumber}}'

          -- 광고코드
          AND om.ad_cd = '{{adCode}}'

          -- 전문관코드
          AND sg.specialty_cd = '{{specialtyCode}}'

          -- 담당MD
          AND om.md_id = '{{mdId}}'

        GROUP BY 1

        ORDER BY `{{orderBy}}` ASC

        LIMIT {{size}}

        OFFSET {{number}}
    """.trimIndent()

    val SAMPLE_QUERY_SET_PARTNER_ID = """
        SELECT
          om.ord_state_date AS `date`
          ,SUM(om.sell_qty) AS `sellQuantity`
          ,SUM(om.sell_amt) AS `sellAmount`
          ,SUM(om.refund_qty) AS `refundQuantity`
          ,SUM(om.refund_amt) AS `refundAmount`
          ,SUM(om.exchange_qty) AS `exchangeQuantity`
          ,SUM(om.exchange_amt) AS `exchangeAmount`
          ,SUM(om.sell_sub_clm_qty) AS `tradeQuantity`
          ,SUM(om.sell_sub_clm_amt) AS `tradeAmount`
          ,SUM(IF(om.dc_type=1, IF(om.gmv_state IN ('1000', '5000'), om.dc_apply_amt, -1*om.dc_apply_amt), 0)) as `memberDiscounts`
          ,SUM(IF(om.dc_type=2, IF(om.gmv_state IN ('1000', '5000'), om.dc_apply_amt, -1*om.dc_apply_amt), 0)) as `affiliateDiscounts`
          ,SUM(IF(om.dc_type NOT IN (1,2), IF(om.gmv_state IN ('1000', '5000'), om.dc_apply_amt, -1*om.dc_apply_amt), 0)) as `otherDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.coupon_apply_amt-om.coupon_partner_apply_amt, -1*om.coupon_apply_amt-om.coupon_partner_apply_amt)) as `couponDiscounts`
          ,SUM(om.coupon_partner_apply_amt) as `partnerCouponDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.point_apply_amt-om.pre_point_amt, -1*(om.point_apply_amt-om.pre_point_amt))) as `pointDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.pre_point_amt, -1*pre_point_amt)) as `prePointDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.cart_dc_amt, -1*cart_dc_amt)) as `cartDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.group_dc_amt, -1*group_dc_amt)) as `groupDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.total_dc_amt, -1*total_dc_amt)) as `totalDiscounts`
          ,ROUND(SUM(IF(om.gmv_state IN ('1000', '5000'), om.total_dc_amt, -1*total_dc_amt))/SUM(om.price*om.qty)*100, 2) `discountRate`
          ,SUM(om.pay_fee) as `paymentFees`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.recv_amt, -1*om.recv_amt)) as `paymentAmount`
          ,SUM(om.revenue) as `sales`
          ,SUM(om.head_wonga) as `originalPrice`
          ,SUM(om.profit) as `profit`
          ,ROUND(IFNULL(SUM(om.profit)/SUM(om.price*om.qty)*100, 0), 2) as `profitMargin`
          ,SUM(om.head_amt) as `purchasesTradeAmount`
          ,SUM(IF(om.ord_com_type=2, 0, (om.point_apply_amt+om.dc_apply_amt+om.coupon_apply_amt+om.cart_dc_amt+om.group_dc_amt)*IF(om.gmv_state IN ('1000', '5000'),1,-1))) as `purchasesDiscounts`
          ,SUM(om.head_pay_fee) as `purchasesPaymentFees`
          ,SUM(IF(om.ord_com_type=2, 0, om.recv_amt*IF(om.gmv_state IN ('1000', '5000'), 1, -1))) as `purchasesPaymentAmount`
          ,SUM(om.head_wonga) as `purchasesOriginalPrice`
          ,SUM(om.head_profit) as `purchasesProfit`
          ,ROUND(IFNULL(SUM(om.head_profit)/SUM(om.head_amt)*100, 0), 2) as `purchasesProfitMargin`
          ,SUM(om.head_amt)/SUM(om.price*om.qty) * 100 as `purchasesRatio`
          ,SUM(om.partner_amt) as `partnerTradeAmount`
          ,SUM(IF(om.ord_com_type=2, (om.point_apply_amt+om.dc_apply_amt+om.coupon_apply_amt+om.cart_dc_amt+om.group_dc_amt)*IF(om.gmv_state IN ('1000', '5000'),1,-1), 0)) as `partnerDiscounts`
          ,SUM(om.partner_pay_fee) as `partnerPaymentFees`
          ,SUM(IF(om.ord_com_type=2, om.recv_amt*IF(om.gmv_state IN ('1000', '5000'), 1, -1), 0)) as `partnerPaymentAmount`
          ,SUM(om.partner_sale_fee) as `partnerSellFees`
          ,SUM(om.partner_fee) as `partnerFees`
          ,SUM(om.partner_support_amt) as `partnerSellGrants`
          ,ROUND(IFNULL(SUM(om.partner_profit)/SUM(om.partner_amt) * 100, 0), 2) as `partnerProfitMargin`
          ,CEIL(SUM(om.not_included_vat_revenue)) as `salesExcludedVAT`
          ,CEIL(SUM(om.not_included_vat_wonga)) as `originalPriceExcludedVAT`
          ,CEIL(SUM(om.not_included_vat_profit)) as `profitExcludedVAT`
          ,ROUND(CEIL(SUM((om.not_included_vat_revenue)-(om.head_wonga/11*10)))/CEIL(SUM(om.price*om.qty))*100, 2) as `profitMarginExcludedVAT`

        FROM datamart.datamart.orders_merged om
          JOIN datamart.datamart.goods_tags as gt
            ON om.goods_no = gt.goods_no
          JOIN datamart.datamart.coupon as c
            ON om.coupon_no = c.coupon_no
          JOIN datamart.datamart.specialty_goods as sg
            ON om.goods_no = sg.goods_no

        WHERE 
          -- 일자
          om.ord_state_date >= '{{startDate}}'
          AND om.ord_state_date <= '{{endDate}}'

          -- 태그(String List)
          AND gt.tag IN ({{tag}})

          -- 매출시점
          AND if('{{salesStart}}'='SHIPPING_REQUEST', om.state_order, om.state_order_done) = True

          -- 업체
          AND om.com_id = 'musinsastandard'

          -- 카테고리
          AND om.small_nm = '{{category}}'

          -- 스타일넘버
          AND om.style_no = '{{styleNumber}}'

          -- 상품코드
          AND om.goods_no = '{{goodsNumber}}'

          -- 브랜드
          AND om.brand = '{{brandId}}'

          -- 쿠폰
          AND c.coupon_no = '{{couponNumber}}'

          -- 광고코드
          AND om.ad_cd = '{{adCode}}'

          -- 전문관코드
          AND sg.specialty_cd = '{{specialtyCode}}'

          -- 담당MD
          AND om.md_id = '{{mdId}}'

        GROUP BY 1

        ORDER BY `{{orderBy}}` ASC

        LIMIT {{size}}

        OFFSET {{number}}
    """.trimIndent()

    val SAMPLE_QUERY_EMPTY_PARTNER_ID = """
        SELECT
          om.ord_state_date AS `date`
          ,SUM(om.sell_qty) AS `sellQuantity`
          ,SUM(om.sell_amt) AS `sellAmount`
          ,SUM(om.refund_qty) AS `refundQuantity`
          ,SUM(om.refund_amt) AS `refundAmount`
          ,SUM(om.exchange_qty) AS `exchangeQuantity`
          ,SUM(om.exchange_amt) AS `exchangeAmount`
          ,SUM(om.sell_sub_clm_qty) AS `tradeQuantity`
          ,SUM(om.sell_sub_clm_amt) AS `tradeAmount`
          ,SUM(IF(om.dc_type=1, IF(om.gmv_state IN ('1000', '5000'), om.dc_apply_amt, -1*om.dc_apply_amt), 0)) as `memberDiscounts`
          ,SUM(IF(om.dc_type=2, IF(om.gmv_state IN ('1000', '5000'), om.dc_apply_amt, -1*om.dc_apply_amt), 0)) as `affiliateDiscounts`
          ,SUM(IF(om.dc_type NOT IN (1,2), IF(om.gmv_state IN ('1000', '5000'), om.dc_apply_amt, -1*om.dc_apply_amt), 0)) as `otherDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.coupon_apply_amt-om.coupon_partner_apply_amt, -1*om.coupon_apply_amt-om.coupon_partner_apply_amt)) as `couponDiscounts`
          ,SUM(om.coupon_partner_apply_amt) as `partnerCouponDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.point_apply_amt-om.pre_point_amt, -1*(om.point_apply_amt-om.pre_point_amt))) as `pointDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.pre_point_amt, -1*pre_point_amt)) as `prePointDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.cart_dc_amt, -1*cart_dc_amt)) as `cartDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.group_dc_amt, -1*group_dc_amt)) as `groupDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.total_dc_amt, -1*total_dc_amt)) as `totalDiscounts`
          ,ROUND(SUM(IF(om.gmv_state IN ('1000', '5000'), om.total_dc_amt, -1*total_dc_amt))/SUM(om.price*om.qty)*100, 2) `discountRate`
          ,SUM(om.pay_fee) as `paymentFees`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.recv_amt, -1*om.recv_amt)) as `paymentAmount`
          ,SUM(om.revenue) as `sales`
          ,SUM(om.head_wonga) as `originalPrice`
          ,SUM(om.profit) as `profit`
          ,ROUND(IFNULL(SUM(om.profit)/SUM(om.price*om.qty)*100, 0), 2) as `profitMargin`
          ,SUM(om.head_amt) as `purchasesTradeAmount`
          ,SUM(IF(om.ord_com_type=2, 0, (om.point_apply_amt+om.dc_apply_amt+om.coupon_apply_amt+om.cart_dc_amt+om.group_dc_amt)*IF(om.gmv_state IN ('1000', '5000'),1,-1))) as `purchasesDiscounts`
          ,SUM(om.head_pay_fee) as `purchasesPaymentFees`
          ,SUM(IF(om.ord_com_type=2, 0, om.recv_amt*IF(om.gmv_state IN ('1000', '5000'), 1, -1))) as `purchasesPaymentAmount`
          ,SUM(om.head_wonga) as `purchasesOriginalPrice`
          ,SUM(om.head_profit) as `purchasesProfit`
          ,ROUND(IFNULL(SUM(om.head_profit)/SUM(om.head_amt)*100, 0), 2) as `purchasesProfitMargin`
          ,SUM(om.head_amt)/SUM(om.price*om.qty) * 100 as `purchasesRatio`
          ,SUM(om.partner_amt) as `partnerTradeAmount`
          ,SUM(IF(om.ord_com_type=2, (om.point_apply_amt+om.dc_apply_amt+om.coupon_apply_amt+om.cart_dc_amt+om.group_dc_amt)*IF(om.gmv_state IN ('1000', '5000'),1,-1), 0)) as `partnerDiscounts`
          ,SUM(om.partner_pay_fee) as `partnerPaymentFees`
          ,SUM(IF(om.ord_com_type=2, om.recv_amt*IF(om.gmv_state IN ('1000', '5000'), 1, -1), 0)) as `partnerPaymentAmount`
          ,SUM(om.partner_sale_fee) as `partnerSellFees`
          ,SUM(om.partner_fee) as `partnerFees`
          ,SUM(om.partner_support_amt) as `partnerSellGrants`
          ,ROUND(IFNULL(SUM(om.partner_profit)/SUM(om.partner_amt) * 100, 0), 2) as `partnerProfitMargin`
          ,CEIL(SUM(om.not_included_vat_revenue)) as `salesExcludedVAT`
          ,CEIL(SUM(om.not_included_vat_wonga)) as `originalPriceExcludedVAT`
          ,CEIL(SUM(om.not_included_vat_profit)) as `profitExcludedVAT`
          ,ROUND(CEIL(SUM((om.not_included_vat_revenue)-(om.head_wonga/11*10)))/CEIL(SUM(om.price*om.qty))*100, 2) as `profitMarginExcludedVAT`

        FROM datamart.datamart.orders_merged om
          JOIN datamart.datamart.goods_tags as gt
            ON om.goods_no = gt.goods_no
          JOIN datamart.datamart.coupon as c
            ON om.coupon_no = c.coupon_no
          JOIN datamart.datamart.specialty_goods as sg
            ON om.goods_no = sg.goods_no

        WHERE 
          -- 일자
          om.ord_state_date >= '{{startDate}}'
          AND om.ord_state_date <= '{{endDate}}'

          -- 태그(String List)
          AND gt.tag IN ({{tag}})

          -- 매출시점
          AND if('{{salesStart}}'='SHIPPING_REQUEST', om.state_order, om.state_order_done) = True

          -- 업체
        --  AND om.com_id = '{{partnerId}}'

          -- 카테고리
          AND om.small_nm = '{{category}}'

          -- 스타일넘버
          AND om.style_no = '{{styleNumber}}'

          -- 상품코드
          AND om.goods_no = '{{goodsNumber}}'

          -- 브랜드
          AND om.brand = '{{brandId}}'

          -- 쿠폰
          AND c.coupon_no = '{{couponNumber}}'

          -- 광고코드
          AND om.ad_cd = '{{adCode}}'

          -- 전문관코드
          AND sg.specialty_cd = '{{specialtyCode}}'

          -- 담당MD
          AND om.md_id = '{{mdId}}'

        GROUP BY 1

        ORDER BY `{{orderBy}}` ASC

        LIMIT {{size}}

        OFFSET {{number}}
    """.trimIndent()

    val SAMPLE_QUERY_SET_CATEGORY = """
        SELECT
          om.ord_state_date AS `date`
          ,SUM(om.sell_qty) AS `sellQuantity`
          ,SUM(om.sell_amt) AS `sellAmount`
          ,SUM(om.refund_qty) AS `refundQuantity`
          ,SUM(om.refund_amt) AS `refundAmount`
          ,SUM(om.exchange_qty) AS `exchangeQuantity`
          ,SUM(om.exchange_amt) AS `exchangeAmount`
          ,SUM(om.sell_sub_clm_qty) AS `tradeQuantity`
          ,SUM(om.sell_sub_clm_amt) AS `tradeAmount`
          ,SUM(IF(om.dc_type=1, IF(om.gmv_state IN ('1000', '5000'), om.dc_apply_amt, -1*om.dc_apply_amt), 0)) as `memberDiscounts`
          ,SUM(IF(om.dc_type=2, IF(om.gmv_state IN ('1000', '5000'), om.dc_apply_amt, -1*om.dc_apply_amt), 0)) as `affiliateDiscounts`
          ,SUM(IF(om.dc_type NOT IN (1,2), IF(om.gmv_state IN ('1000', '5000'), om.dc_apply_amt, -1*om.dc_apply_amt), 0)) as `otherDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.coupon_apply_amt-om.coupon_partner_apply_amt, -1*om.coupon_apply_amt-om.coupon_partner_apply_amt)) as `couponDiscounts`
          ,SUM(om.coupon_partner_apply_amt) as `partnerCouponDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.point_apply_amt-om.pre_point_amt, -1*(om.point_apply_amt-om.pre_point_amt))) as `pointDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.pre_point_amt, -1*pre_point_amt)) as `prePointDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.cart_dc_amt, -1*cart_dc_amt)) as `cartDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.group_dc_amt, -1*group_dc_amt)) as `groupDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.total_dc_amt, -1*total_dc_amt)) as `totalDiscounts`
          ,ROUND(SUM(IF(om.gmv_state IN ('1000', '5000'), om.total_dc_amt, -1*total_dc_amt))/SUM(om.price*om.qty)*100, 2) `discountRate`
          ,SUM(om.pay_fee) as `paymentFees`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.recv_amt, -1*om.recv_amt)) as `paymentAmount`
          ,SUM(om.revenue) as `sales`
          ,SUM(om.head_wonga) as `originalPrice`
          ,SUM(om.profit) as `profit`
          ,ROUND(IFNULL(SUM(om.profit)/SUM(om.price*om.qty)*100, 0), 2) as `profitMargin`
          ,SUM(om.head_amt) as `purchasesTradeAmount`
          ,SUM(IF(om.ord_com_type=2, 0, (om.point_apply_amt+om.dc_apply_amt+om.coupon_apply_amt+om.cart_dc_amt+om.group_dc_amt)*IF(om.gmv_state IN ('1000', '5000'),1,-1))) as `purchasesDiscounts`
          ,SUM(om.head_pay_fee) as `purchasesPaymentFees`
          ,SUM(IF(om.ord_com_type=2, 0, om.recv_amt*IF(om.gmv_state IN ('1000', '5000'), 1, -1))) as `purchasesPaymentAmount`
          ,SUM(om.head_wonga) as `purchasesOriginalPrice`
          ,SUM(om.head_profit) as `purchasesProfit`
          ,ROUND(IFNULL(SUM(om.head_profit)/SUM(om.head_amt)*100, 0), 2) as `purchasesProfitMargin`
          ,SUM(om.head_amt)/SUM(om.price*om.qty) * 100 as `purchasesRatio`
          ,SUM(om.partner_amt) as `partnerTradeAmount`
          ,SUM(IF(om.ord_com_type=2, (om.point_apply_amt+om.dc_apply_amt+om.coupon_apply_amt+om.cart_dc_amt+om.group_dc_amt)*IF(om.gmv_state IN ('1000', '5000'),1,-1), 0)) as `partnerDiscounts`
          ,SUM(om.partner_pay_fee) as `partnerPaymentFees`
          ,SUM(IF(om.ord_com_type=2, om.recv_amt*IF(om.gmv_state IN ('1000', '5000'), 1, -1), 0)) as `partnerPaymentAmount`
          ,SUM(om.partner_sale_fee) as `partnerSellFees`
          ,SUM(om.partner_fee) as `partnerFees`
          ,SUM(om.partner_support_amt) as `partnerSellGrants`
          ,ROUND(IFNULL(SUM(om.partner_profit)/SUM(om.partner_amt) * 100, 0), 2) as `partnerProfitMargin`
          ,CEIL(SUM(om.not_included_vat_revenue)) as `salesExcludedVAT`
          ,CEIL(SUM(om.not_included_vat_wonga)) as `originalPriceExcludedVAT`
          ,CEIL(SUM(om.not_included_vat_profit)) as `profitExcludedVAT`
          ,ROUND(CEIL(SUM((om.not_included_vat_revenue)-(om.head_wonga/11*10)))/CEIL(SUM(om.price*om.qty))*100, 2) as `profitMarginExcludedVAT`

        FROM datamart.datamart.orders_merged om
          JOIN datamart.datamart.goods_tags as gt
            ON om.goods_no = gt.goods_no
          JOIN datamart.datamart.coupon as c
            ON om.coupon_no = c.coupon_no
          JOIN datamart.datamart.specialty_goods as sg
            ON om.goods_no = sg.goods_no

        WHERE 
          -- 일자
          om.ord_state_date >= '{{startDate}}'
          AND om.ord_state_date <= '{{endDate}}'

          -- 태그(String List)
          AND gt.tag IN ({{tag}})

          -- 매출시점
          AND if('{{salesStart}}'='SHIPPING_REQUEST', om.state_order, om.state_order_done) = True

          -- 업체
          AND om.com_id = '{{partnerId}}'

          -- 카테고리
          AND om.small_nm = '청/데님 팬츠'

          -- 스타일넘버
          AND om.style_no = '{{styleNumber}}'

          -- 상품코드
          AND om.goods_no = '{{goodsNumber}}'

          -- 브랜드
          AND om.brand = '{{brandId}}'

          -- 쿠폰
          AND c.coupon_no = '{{couponNumber}}'

          -- 광고코드
          AND om.ad_cd = '{{adCode}}'

          -- 전문관코드
          AND sg.specialty_cd = '{{specialtyCode}}'

          -- 담당MD
          AND om.md_id = '{{mdId}}'

        GROUP BY 1

        ORDER BY `{{orderBy}}` ASC

        LIMIT {{size}}

        OFFSET {{number}}
    """.trimIndent()

    val SAMPLE_QUERY_EMPTY_CATEGORY = """
        SELECT
          om.ord_state_date AS `date`
          ,SUM(om.sell_qty) AS `sellQuantity`
          ,SUM(om.sell_amt) AS `sellAmount`
          ,SUM(om.refund_qty) AS `refundQuantity`
          ,SUM(om.refund_amt) AS `refundAmount`
          ,SUM(om.exchange_qty) AS `exchangeQuantity`
          ,SUM(om.exchange_amt) AS `exchangeAmount`
          ,SUM(om.sell_sub_clm_qty) AS `tradeQuantity`
          ,SUM(om.sell_sub_clm_amt) AS `tradeAmount`
          ,SUM(IF(om.dc_type=1, IF(om.gmv_state IN ('1000', '5000'), om.dc_apply_amt, -1*om.dc_apply_amt), 0)) as `memberDiscounts`
          ,SUM(IF(om.dc_type=2, IF(om.gmv_state IN ('1000', '5000'), om.dc_apply_amt, -1*om.dc_apply_amt), 0)) as `affiliateDiscounts`
          ,SUM(IF(om.dc_type NOT IN (1,2), IF(om.gmv_state IN ('1000', '5000'), om.dc_apply_amt, -1*om.dc_apply_amt), 0)) as `otherDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.coupon_apply_amt-om.coupon_partner_apply_amt, -1*om.coupon_apply_amt-om.coupon_partner_apply_amt)) as `couponDiscounts`
          ,SUM(om.coupon_partner_apply_amt) as `partnerCouponDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.point_apply_amt-om.pre_point_amt, -1*(om.point_apply_amt-om.pre_point_amt))) as `pointDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.pre_point_amt, -1*pre_point_amt)) as `prePointDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.cart_dc_amt, -1*cart_dc_amt)) as `cartDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.group_dc_amt, -1*group_dc_amt)) as `groupDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.total_dc_amt, -1*total_dc_amt)) as `totalDiscounts`
          ,ROUND(SUM(IF(om.gmv_state IN ('1000', '5000'), om.total_dc_amt, -1*total_dc_amt))/SUM(om.price*om.qty)*100, 2) `discountRate`
          ,SUM(om.pay_fee) as `paymentFees`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.recv_amt, -1*om.recv_amt)) as `paymentAmount`
          ,SUM(om.revenue) as `sales`
          ,SUM(om.head_wonga) as `originalPrice`
          ,SUM(om.profit) as `profit`
          ,ROUND(IFNULL(SUM(om.profit)/SUM(om.price*om.qty)*100, 0), 2) as `profitMargin`
          ,SUM(om.head_amt) as `purchasesTradeAmount`
          ,SUM(IF(om.ord_com_type=2, 0, (om.point_apply_amt+om.dc_apply_amt+om.coupon_apply_amt+om.cart_dc_amt+om.group_dc_amt)*IF(om.gmv_state IN ('1000', '5000'),1,-1))) as `purchasesDiscounts`
          ,SUM(om.head_pay_fee) as `purchasesPaymentFees`
          ,SUM(IF(om.ord_com_type=2, 0, om.recv_amt*IF(om.gmv_state IN ('1000', '5000'), 1, -1))) as `purchasesPaymentAmount`
          ,SUM(om.head_wonga) as `purchasesOriginalPrice`
          ,SUM(om.head_profit) as `purchasesProfit`
          ,ROUND(IFNULL(SUM(om.head_profit)/SUM(om.head_amt)*100, 0), 2) as `purchasesProfitMargin`
          ,SUM(om.head_amt)/SUM(om.price*om.qty) * 100 as `purchasesRatio`
          ,SUM(om.partner_amt) as `partnerTradeAmount`
          ,SUM(IF(om.ord_com_type=2, (om.point_apply_amt+om.dc_apply_amt+om.coupon_apply_amt+om.cart_dc_amt+om.group_dc_amt)*IF(om.gmv_state IN ('1000', '5000'),1,-1), 0)) as `partnerDiscounts`
          ,SUM(om.partner_pay_fee) as `partnerPaymentFees`
          ,SUM(IF(om.ord_com_type=2, om.recv_amt*IF(om.gmv_state IN ('1000', '5000'), 1, -1), 0)) as `partnerPaymentAmount`
          ,SUM(om.partner_sale_fee) as `partnerSellFees`
          ,SUM(om.partner_fee) as `partnerFees`
          ,SUM(om.partner_support_amt) as `partnerSellGrants`
          ,ROUND(IFNULL(SUM(om.partner_profit)/SUM(om.partner_amt) * 100, 0), 2) as `partnerProfitMargin`
          ,CEIL(SUM(om.not_included_vat_revenue)) as `salesExcludedVAT`
          ,CEIL(SUM(om.not_included_vat_wonga)) as `originalPriceExcludedVAT`
          ,CEIL(SUM(om.not_included_vat_profit)) as `profitExcludedVAT`
          ,ROUND(CEIL(SUM((om.not_included_vat_revenue)-(om.head_wonga/11*10)))/CEIL(SUM(om.price*om.qty))*100, 2) as `profitMarginExcludedVAT`

        FROM datamart.datamart.orders_merged om
          JOIN datamart.datamart.goods_tags as gt
            ON om.goods_no = gt.goods_no
          JOIN datamart.datamart.coupon as c
            ON om.coupon_no = c.coupon_no
          JOIN datamart.datamart.specialty_goods as sg
            ON om.goods_no = sg.goods_no

        WHERE 
          -- 일자
          om.ord_state_date >= '{{startDate}}'
          AND om.ord_state_date <= '{{endDate}}'

          -- 태그(String List)
          AND gt.tag IN ({{tag}})

          -- 매출시점
          AND if('{{salesStart}}'='SHIPPING_REQUEST', om.state_order, om.state_order_done) = True

          -- 업체
          AND om.com_id = '{{partnerId}}'

          -- 카테고리
        --  AND om.small_nm = '{{category}}'

          -- 스타일넘버
          AND om.style_no = '{{styleNumber}}'

          -- 상품코드
          AND om.goods_no = '{{goodsNumber}}'

          -- 브랜드
          AND om.brand = '{{brandId}}'

          -- 쿠폰
          AND c.coupon_no = '{{couponNumber}}'

          -- 광고코드
          AND om.ad_cd = '{{adCode}}'

          -- 전문관코드
          AND sg.specialty_cd = '{{specialtyCode}}'

          -- 담당MD
          AND om.md_id = '{{mdId}}'

        GROUP BY 1

        ORDER BY `{{orderBy}}` ASC

        LIMIT {{size}}

        OFFSET {{number}}
    """.trimIndent()

    val SAMPLE_QUERY_SET_STYLE_NUMBER = """
        SELECT
          om.ord_state_date AS `date`
          ,SUM(om.sell_qty) AS `sellQuantity`
          ,SUM(om.sell_amt) AS `sellAmount`
          ,SUM(om.refund_qty) AS `refundQuantity`
          ,SUM(om.refund_amt) AS `refundAmount`
          ,SUM(om.exchange_qty) AS `exchangeQuantity`
          ,SUM(om.exchange_amt) AS `exchangeAmount`
          ,SUM(om.sell_sub_clm_qty) AS `tradeQuantity`
          ,SUM(om.sell_sub_clm_amt) AS `tradeAmount`
          ,SUM(IF(om.dc_type=1, IF(om.gmv_state IN ('1000', '5000'), om.dc_apply_amt, -1*om.dc_apply_amt), 0)) as `memberDiscounts`
          ,SUM(IF(om.dc_type=2, IF(om.gmv_state IN ('1000', '5000'), om.dc_apply_amt, -1*om.dc_apply_amt), 0)) as `affiliateDiscounts`
          ,SUM(IF(om.dc_type NOT IN (1,2), IF(om.gmv_state IN ('1000', '5000'), om.dc_apply_amt, -1*om.dc_apply_amt), 0)) as `otherDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.coupon_apply_amt-om.coupon_partner_apply_amt, -1*om.coupon_apply_amt-om.coupon_partner_apply_amt)) as `couponDiscounts`
          ,SUM(om.coupon_partner_apply_amt) as `partnerCouponDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.point_apply_amt-om.pre_point_amt, -1*(om.point_apply_amt-om.pre_point_amt))) as `pointDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.pre_point_amt, -1*pre_point_amt)) as `prePointDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.cart_dc_amt, -1*cart_dc_amt)) as `cartDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.group_dc_amt, -1*group_dc_amt)) as `groupDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.total_dc_amt, -1*total_dc_amt)) as `totalDiscounts`
          ,ROUND(SUM(IF(om.gmv_state IN ('1000', '5000'), om.total_dc_amt, -1*total_dc_amt))/SUM(om.price*om.qty)*100, 2) `discountRate`
          ,SUM(om.pay_fee) as `paymentFees`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.recv_amt, -1*om.recv_amt)) as `paymentAmount`
          ,SUM(om.revenue) as `sales`
          ,SUM(om.head_wonga) as `originalPrice`
          ,SUM(om.profit) as `profit`
          ,ROUND(IFNULL(SUM(om.profit)/SUM(om.price*om.qty)*100, 0), 2) as `profitMargin`
          ,SUM(om.head_amt) as `purchasesTradeAmount`
          ,SUM(IF(om.ord_com_type=2, 0, (om.point_apply_amt+om.dc_apply_amt+om.coupon_apply_amt+om.cart_dc_amt+om.group_dc_amt)*IF(om.gmv_state IN ('1000', '5000'),1,-1))) as `purchasesDiscounts`
          ,SUM(om.head_pay_fee) as `purchasesPaymentFees`
          ,SUM(IF(om.ord_com_type=2, 0, om.recv_amt*IF(om.gmv_state IN ('1000', '5000'), 1, -1))) as `purchasesPaymentAmount`
          ,SUM(om.head_wonga) as `purchasesOriginalPrice`
          ,SUM(om.head_profit) as `purchasesProfit`
          ,ROUND(IFNULL(SUM(om.head_profit)/SUM(om.head_amt)*100, 0), 2) as `purchasesProfitMargin`
          ,SUM(om.head_amt)/SUM(om.price*om.qty) * 100 as `purchasesRatio`
          ,SUM(om.partner_amt) as `partnerTradeAmount`
          ,SUM(IF(om.ord_com_type=2, (om.point_apply_amt+om.dc_apply_amt+om.coupon_apply_amt+om.cart_dc_amt+om.group_dc_amt)*IF(om.gmv_state IN ('1000', '5000'),1,-1), 0)) as `partnerDiscounts`
          ,SUM(om.partner_pay_fee) as `partnerPaymentFees`
          ,SUM(IF(om.ord_com_type=2, om.recv_amt*IF(om.gmv_state IN ('1000', '5000'), 1, -1), 0)) as `partnerPaymentAmount`
          ,SUM(om.partner_sale_fee) as `partnerSellFees`
          ,SUM(om.partner_fee) as `partnerFees`
          ,SUM(om.partner_support_amt) as `partnerSellGrants`
          ,ROUND(IFNULL(SUM(om.partner_profit)/SUM(om.partner_amt) * 100, 0), 2) as `partnerProfitMargin`
          ,CEIL(SUM(om.not_included_vat_revenue)) as `salesExcludedVAT`
          ,CEIL(SUM(om.not_included_vat_wonga)) as `originalPriceExcludedVAT`
          ,CEIL(SUM(om.not_included_vat_profit)) as `profitExcludedVAT`
          ,ROUND(CEIL(SUM((om.not_included_vat_revenue)-(om.head_wonga/11*10)))/CEIL(SUM(om.price*om.qty))*100, 2) as `profitMarginExcludedVAT`

        FROM datamart.datamart.orders_merged om
          JOIN datamart.datamart.goods_tags as gt
            ON om.goods_no = gt.goods_no
          JOIN datamart.datamart.coupon as c
            ON om.coupon_no = c.coupon_no
          JOIN datamart.datamart.specialty_goods as sg
            ON om.goods_no = sg.goods_no

        WHERE 
          -- 일자
          om.ord_state_date >= '{{startDate}}'
          AND om.ord_state_date <= '{{endDate}}'

          -- 태그(String List)
          AND gt.tag IN ({{tag}})

          -- 매출시점
          AND if('{{salesStart}}'='SHIPPING_REQUEST', om.state_order, om.state_order_done) = True

          -- 업체
          AND om.com_id = '{{partnerId}}'

          -- 카테고리
          AND om.small_nm = '{{category}}'

          -- 스타일넘버
          AND om.style_no = 'DF22SS7022'

          -- 상품코드
          AND om.goods_no = '{{goodsNumber}}'

          -- 브랜드
          AND om.brand = '{{brandId}}'

          -- 쿠폰
          AND c.coupon_no = '{{couponNumber}}'

          -- 광고코드
          AND om.ad_cd = '{{adCode}}'

          -- 전문관코드
          AND sg.specialty_cd = '{{specialtyCode}}'

          -- 담당MD
          AND om.md_id = '{{mdId}}'

        GROUP BY 1

        ORDER BY `{{orderBy}}` ASC

        LIMIT {{size}}

        OFFSET {{number}}
    """.trimIndent()

    val SAMPLE_QUERY_EMPTY_STYLE_NUMBER = """
        SELECT
          om.ord_state_date AS `date`
          ,SUM(om.sell_qty) AS `sellQuantity`
          ,SUM(om.sell_amt) AS `sellAmount`
          ,SUM(om.refund_qty) AS `refundQuantity`
          ,SUM(om.refund_amt) AS `refundAmount`
          ,SUM(om.exchange_qty) AS `exchangeQuantity`
          ,SUM(om.exchange_amt) AS `exchangeAmount`
          ,SUM(om.sell_sub_clm_qty) AS `tradeQuantity`
          ,SUM(om.sell_sub_clm_amt) AS `tradeAmount`
          ,SUM(IF(om.dc_type=1, IF(om.gmv_state IN ('1000', '5000'), om.dc_apply_amt, -1*om.dc_apply_amt), 0)) as `memberDiscounts`
          ,SUM(IF(om.dc_type=2, IF(om.gmv_state IN ('1000', '5000'), om.dc_apply_amt, -1*om.dc_apply_amt), 0)) as `affiliateDiscounts`
          ,SUM(IF(om.dc_type NOT IN (1,2), IF(om.gmv_state IN ('1000', '5000'), om.dc_apply_amt, -1*om.dc_apply_amt), 0)) as `otherDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.coupon_apply_amt-om.coupon_partner_apply_amt, -1*om.coupon_apply_amt-om.coupon_partner_apply_amt)) as `couponDiscounts`
          ,SUM(om.coupon_partner_apply_amt) as `partnerCouponDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.point_apply_amt-om.pre_point_amt, -1*(om.point_apply_amt-om.pre_point_amt))) as `pointDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.pre_point_amt, -1*pre_point_amt)) as `prePointDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.cart_dc_amt, -1*cart_dc_amt)) as `cartDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.group_dc_amt, -1*group_dc_amt)) as `groupDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.total_dc_amt, -1*total_dc_amt)) as `totalDiscounts`
          ,ROUND(SUM(IF(om.gmv_state IN ('1000', '5000'), om.total_dc_amt, -1*total_dc_amt))/SUM(om.price*om.qty)*100, 2) `discountRate`
          ,SUM(om.pay_fee) as `paymentFees`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.recv_amt, -1*om.recv_amt)) as `paymentAmount`
          ,SUM(om.revenue) as `sales`
          ,SUM(om.head_wonga) as `originalPrice`
          ,SUM(om.profit) as `profit`
          ,ROUND(IFNULL(SUM(om.profit)/SUM(om.price*om.qty)*100, 0), 2) as `profitMargin`
          ,SUM(om.head_amt) as `purchasesTradeAmount`
          ,SUM(IF(om.ord_com_type=2, 0, (om.point_apply_amt+om.dc_apply_amt+om.coupon_apply_amt+om.cart_dc_amt+om.group_dc_amt)*IF(om.gmv_state IN ('1000', '5000'),1,-1))) as `purchasesDiscounts`
          ,SUM(om.head_pay_fee) as `purchasesPaymentFees`
          ,SUM(IF(om.ord_com_type=2, 0, om.recv_amt*IF(om.gmv_state IN ('1000', '5000'), 1, -1))) as `purchasesPaymentAmount`
          ,SUM(om.head_wonga) as `purchasesOriginalPrice`
          ,SUM(om.head_profit) as `purchasesProfit`
          ,ROUND(IFNULL(SUM(om.head_profit)/SUM(om.head_amt)*100, 0), 2) as `purchasesProfitMargin`
          ,SUM(om.head_amt)/SUM(om.price*om.qty) * 100 as `purchasesRatio`
          ,SUM(om.partner_amt) as `partnerTradeAmount`
          ,SUM(IF(om.ord_com_type=2, (om.point_apply_amt+om.dc_apply_amt+om.coupon_apply_amt+om.cart_dc_amt+om.group_dc_amt)*IF(om.gmv_state IN ('1000', '5000'),1,-1), 0)) as `partnerDiscounts`
          ,SUM(om.partner_pay_fee) as `partnerPaymentFees`
          ,SUM(IF(om.ord_com_type=2, om.recv_amt*IF(om.gmv_state IN ('1000', '5000'), 1, -1), 0)) as `partnerPaymentAmount`
          ,SUM(om.partner_sale_fee) as `partnerSellFees`
          ,SUM(om.partner_fee) as `partnerFees`
          ,SUM(om.partner_support_amt) as `partnerSellGrants`
          ,ROUND(IFNULL(SUM(om.partner_profit)/SUM(om.partner_amt) * 100, 0), 2) as `partnerProfitMargin`
          ,CEIL(SUM(om.not_included_vat_revenue)) as `salesExcludedVAT`
          ,CEIL(SUM(om.not_included_vat_wonga)) as `originalPriceExcludedVAT`
          ,CEIL(SUM(om.not_included_vat_profit)) as `profitExcludedVAT`
          ,ROUND(CEIL(SUM((om.not_included_vat_revenue)-(om.head_wonga/11*10)))/CEIL(SUM(om.price*om.qty))*100, 2) as `profitMarginExcludedVAT`

        FROM datamart.datamart.orders_merged om
          JOIN datamart.datamart.goods_tags as gt
            ON om.goods_no = gt.goods_no
          JOIN datamart.datamart.coupon as c
            ON om.coupon_no = c.coupon_no
          JOIN datamart.datamart.specialty_goods as sg
            ON om.goods_no = sg.goods_no

        WHERE 
          -- 일자
          om.ord_state_date >= '{{startDate}}'
          AND om.ord_state_date <= '{{endDate}}'

          -- 태그(String List)
          AND gt.tag IN ({{tag}})

          -- 매출시점
          AND if('{{salesStart}}'='SHIPPING_REQUEST', om.state_order, om.state_order_done) = True

          -- 업체
          AND om.com_id = '{{partnerId}}'

          -- 카테고리
          AND om.small_nm = '{{category}}'

          -- 스타일넘버
        --  AND om.style_no = '{{styleNumber}}'

          -- 상품코드
          AND om.goods_no = '{{goodsNumber}}'

          -- 브랜드
          AND om.brand = '{{brandId}}'

          -- 쿠폰
          AND c.coupon_no = '{{couponNumber}}'

          -- 광고코드
          AND om.ad_cd = '{{adCode}}'

          -- 전문관코드
          AND sg.specialty_cd = '{{specialtyCode}}'

          -- 담당MD
          AND om.md_id = '{{mdId}}'

        GROUP BY 1

        ORDER BY `{{orderBy}}` ASC

        LIMIT {{size}}

        OFFSET {{number}}
    """.trimIndent()

    val SAMPLE_QUERY_SET_GOODS_NUMBER = """
        SELECT
          om.ord_state_date AS `date`
          ,SUM(om.sell_qty) AS `sellQuantity`
          ,SUM(om.sell_amt) AS `sellAmount`
          ,SUM(om.refund_qty) AS `refundQuantity`
          ,SUM(om.refund_amt) AS `refundAmount`
          ,SUM(om.exchange_qty) AS `exchangeQuantity`
          ,SUM(om.exchange_amt) AS `exchangeAmount`
          ,SUM(om.sell_sub_clm_qty) AS `tradeQuantity`
          ,SUM(om.sell_sub_clm_amt) AS `tradeAmount`
          ,SUM(IF(om.dc_type=1, IF(om.gmv_state IN ('1000', '5000'), om.dc_apply_amt, -1*om.dc_apply_amt), 0)) as `memberDiscounts`
          ,SUM(IF(om.dc_type=2, IF(om.gmv_state IN ('1000', '5000'), om.dc_apply_amt, -1*om.dc_apply_amt), 0)) as `affiliateDiscounts`
          ,SUM(IF(om.dc_type NOT IN (1,2), IF(om.gmv_state IN ('1000', '5000'), om.dc_apply_amt, -1*om.dc_apply_amt), 0)) as `otherDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.coupon_apply_amt-om.coupon_partner_apply_amt, -1*om.coupon_apply_amt-om.coupon_partner_apply_amt)) as `couponDiscounts`
          ,SUM(om.coupon_partner_apply_amt) as `partnerCouponDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.point_apply_amt-om.pre_point_amt, -1*(om.point_apply_amt-om.pre_point_amt))) as `pointDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.pre_point_amt, -1*pre_point_amt)) as `prePointDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.cart_dc_amt, -1*cart_dc_amt)) as `cartDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.group_dc_amt, -1*group_dc_amt)) as `groupDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.total_dc_amt, -1*total_dc_amt)) as `totalDiscounts`
          ,ROUND(SUM(IF(om.gmv_state IN ('1000', '5000'), om.total_dc_amt, -1*total_dc_amt))/SUM(om.price*om.qty)*100, 2) `discountRate`
          ,SUM(om.pay_fee) as `paymentFees`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.recv_amt, -1*om.recv_amt)) as `paymentAmount`
          ,SUM(om.revenue) as `sales`
          ,SUM(om.head_wonga) as `originalPrice`
          ,SUM(om.profit) as `profit`
          ,ROUND(IFNULL(SUM(om.profit)/SUM(om.price*om.qty)*100, 0), 2) as `profitMargin`
          ,SUM(om.head_amt) as `purchasesTradeAmount`
          ,SUM(IF(om.ord_com_type=2, 0, (om.point_apply_amt+om.dc_apply_amt+om.coupon_apply_amt+om.cart_dc_amt+om.group_dc_amt)*IF(om.gmv_state IN ('1000', '5000'),1,-1))) as `purchasesDiscounts`
          ,SUM(om.head_pay_fee) as `purchasesPaymentFees`
          ,SUM(IF(om.ord_com_type=2, 0, om.recv_amt*IF(om.gmv_state IN ('1000', '5000'), 1, -1))) as `purchasesPaymentAmount`
          ,SUM(om.head_wonga) as `purchasesOriginalPrice`
          ,SUM(om.head_profit) as `purchasesProfit`
          ,ROUND(IFNULL(SUM(om.head_profit)/SUM(om.head_amt)*100, 0), 2) as `purchasesProfitMargin`
          ,SUM(om.head_amt)/SUM(om.price*om.qty) * 100 as `purchasesRatio`
          ,SUM(om.partner_amt) as `partnerTradeAmount`
          ,SUM(IF(om.ord_com_type=2, (om.point_apply_amt+om.dc_apply_amt+om.coupon_apply_amt+om.cart_dc_amt+om.group_dc_amt)*IF(om.gmv_state IN ('1000', '5000'),1,-1), 0)) as `partnerDiscounts`
          ,SUM(om.partner_pay_fee) as `partnerPaymentFees`
          ,SUM(IF(om.ord_com_type=2, om.recv_amt*IF(om.gmv_state IN ('1000', '5000'), 1, -1), 0)) as `partnerPaymentAmount`
          ,SUM(om.partner_sale_fee) as `partnerSellFees`
          ,SUM(om.partner_fee) as `partnerFees`
          ,SUM(om.partner_support_amt) as `partnerSellGrants`
          ,ROUND(IFNULL(SUM(om.partner_profit)/SUM(om.partner_amt) * 100, 0), 2) as `partnerProfitMargin`
          ,CEIL(SUM(om.not_included_vat_revenue)) as `salesExcludedVAT`
          ,CEIL(SUM(om.not_included_vat_wonga)) as `originalPriceExcludedVAT`
          ,CEIL(SUM(om.not_included_vat_profit)) as `profitExcludedVAT`
          ,ROUND(CEIL(SUM((om.not_included_vat_revenue)-(om.head_wonga/11*10)))/CEIL(SUM(om.price*om.qty))*100, 2) as `profitMarginExcludedVAT`

        FROM datamart.datamart.orders_merged om
          JOIN datamart.datamart.goods_tags as gt
            ON om.goods_no = gt.goods_no
          JOIN datamart.datamart.coupon as c
            ON om.coupon_no = c.coupon_no
          JOIN datamart.datamart.specialty_goods as sg
            ON om.goods_no = sg.goods_no

        WHERE 
          -- 일자
          om.ord_state_date >= '{{startDate}}'
          AND om.ord_state_date <= '{{endDate}}'

          -- 태그(String List)
          AND gt.tag IN ({{tag}})

          -- 매출시점
          AND if('{{salesStart}}'='SHIPPING_REQUEST', om.state_order, om.state_order_done) = True

          -- 업체
          AND om.com_id = '{{partnerId}}'

          -- 카테고리
          AND om.small_nm = '{{category}}'

          -- 스타일넘버
          AND om.style_no = '{{styleNumber}}'

          -- 상품코드
          AND om.goods_no = '1387960'

          -- 브랜드
          AND om.brand = '{{brandId}}'

          -- 쿠폰
          AND c.coupon_no = '{{couponNumber}}'

          -- 광고코드
          AND om.ad_cd = '{{adCode}}'

          -- 전문관코드
          AND sg.specialty_cd = '{{specialtyCode}}'

          -- 담당MD
          AND om.md_id = '{{mdId}}'

        GROUP BY 1

        ORDER BY `{{orderBy}}` ASC

        LIMIT {{size}}

        OFFSET {{number}}
    """.trimIndent()

    val SAMPLE_QUERY_EMPTY_GOODS_NUMBER = """
        SELECT
          om.ord_state_date AS `date`
          ,SUM(om.sell_qty) AS `sellQuantity`
          ,SUM(om.sell_amt) AS `sellAmount`
          ,SUM(om.refund_qty) AS `refundQuantity`
          ,SUM(om.refund_amt) AS `refundAmount`
          ,SUM(om.exchange_qty) AS `exchangeQuantity`
          ,SUM(om.exchange_amt) AS `exchangeAmount`
          ,SUM(om.sell_sub_clm_qty) AS `tradeQuantity`
          ,SUM(om.sell_sub_clm_amt) AS `tradeAmount`
          ,SUM(IF(om.dc_type=1, IF(om.gmv_state IN ('1000', '5000'), om.dc_apply_amt, -1*om.dc_apply_amt), 0)) as `memberDiscounts`
          ,SUM(IF(om.dc_type=2, IF(om.gmv_state IN ('1000', '5000'), om.dc_apply_amt, -1*om.dc_apply_amt), 0)) as `affiliateDiscounts`
          ,SUM(IF(om.dc_type NOT IN (1,2), IF(om.gmv_state IN ('1000', '5000'), om.dc_apply_amt, -1*om.dc_apply_amt), 0)) as `otherDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.coupon_apply_amt-om.coupon_partner_apply_amt, -1*om.coupon_apply_amt-om.coupon_partner_apply_amt)) as `couponDiscounts`
          ,SUM(om.coupon_partner_apply_amt) as `partnerCouponDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.point_apply_amt-om.pre_point_amt, -1*(om.point_apply_amt-om.pre_point_amt))) as `pointDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.pre_point_amt, -1*pre_point_amt)) as `prePointDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.cart_dc_amt, -1*cart_dc_amt)) as `cartDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.group_dc_amt, -1*group_dc_amt)) as `groupDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.total_dc_amt, -1*total_dc_amt)) as `totalDiscounts`
          ,ROUND(SUM(IF(om.gmv_state IN ('1000', '5000'), om.total_dc_amt, -1*total_dc_amt))/SUM(om.price*om.qty)*100, 2) `discountRate`
          ,SUM(om.pay_fee) as `paymentFees`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.recv_amt, -1*om.recv_amt)) as `paymentAmount`
          ,SUM(om.revenue) as `sales`
          ,SUM(om.head_wonga) as `originalPrice`
          ,SUM(om.profit) as `profit`
          ,ROUND(IFNULL(SUM(om.profit)/SUM(om.price*om.qty)*100, 0), 2) as `profitMargin`
          ,SUM(om.head_amt) as `purchasesTradeAmount`
          ,SUM(IF(om.ord_com_type=2, 0, (om.point_apply_amt+om.dc_apply_amt+om.coupon_apply_amt+om.cart_dc_amt+om.group_dc_amt)*IF(om.gmv_state IN ('1000', '5000'),1,-1))) as `purchasesDiscounts`
          ,SUM(om.head_pay_fee) as `purchasesPaymentFees`
          ,SUM(IF(om.ord_com_type=2, 0, om.recv_amt*IF(om.gmv_state IN ('1000', '5000'), 1, -1))) as `purchasesPaymentAmount`
          ,SUM(om.head_wonga) as `purchasesOriginalPrice`
          ,SUM(om.head_profit) as `purchasesProfit`
          ,ROUND(IFNULL(SUM(om.head_profit)/SUM(om.head_amt)*100, 0), 2) as `purchasesProfitMargin`
          ,SUM(om.head_amt)/SUM(om.price*om.qty) * 100 as `purchasesRatio`
          ,SUM(om.partner_amt) as `partnerTradeAmount`
          ,SUM(IF(om.ord_com_type=2, (om.point_apply_amt+om.dc_apply_amt+om.coupon_apply_amt+om.cart_dc_amt+om.group_dc_amt)*IF(om.gmv_state IN ('1000', '5000'),1,-1), 0)) as `partnerDiscounts`
          ,SUM(om.partner_pay_fee) as `partnerPaymentFees`
          ,SUM(IF(om.ord_com_type=2, om.recv_amt*IF(om.gmv_state IN ('1000', '5000'), 1, -1), 0)) as `partnerPaymentAmount`
          ,SUM(om.partner_sale_fee) as `partnerSellFees`
          ,SUM(om.partner_fee) as `partnerFees`
          ,SUM(om.partner_support_amt) as `partnerSellGrants`
          ,ROUND(IFNULL(SUM(om.partner_profit)/SUM(om.partner_amt) * 100, 0), 2) as `partnerProfitMargin`
          ,CEIL(SUM(om.not_included_vat_revenue)) as `salesExcludedVAT`
          ,CEIL(SUM(om.not_included_vat_wonga)) as `originalPriceExcludedVAT`
          ,CEIL(SUM(om.not_included_vat_profit)) as `profitExcludedVAT`
          ,ROUND(CEIL(SUM((om.not_included_vat_revenue)-(om.head_wonga/11*10)))/CEIL(SUM(om.price*om.qty))*100, 2) as `profitMarginExcludedVAT`

        FROM datamart.datamart.orders_merged om
          JOIN datamart.datamart.goods_tags as gt
            ON om.goods_no = gt.goods_no
          JOIN datamart.datamart.coupon as c
            ON om.coupon_no = c.coupon_no
          JOIN datamart.datamart.specialty_goods as sg
            ON om.goods_no = sg.goods_no

        WHERE 
          -- 일자
          om.ord_state_date >= '{{startDate}}'
          AND om.ord_state_date <= '{{endDate}}'

          -- 태그(String List)
          AND gt.tag IN ({{tag}})

          -- 매출시점
          AND if('{{salesStart}}'='SHIPPING_REQUEST', om.state_order, om.state_order_done) = True

          -- 업체
          AND om.com_id = '{{partnerId}}'

          -- 카테고리
          AND om.small_nm = '{{category}}'

          -- 스타일넘버
          AND om.style_no = '{{styleNumber}}'

          -- 상품코드
        --  AND om.goods_no = '{{goodsNumber}}'

          -- 브랜드
          AND om.brand = '{{brandId}}'

          -- 쿠폰
          AND c.coupon_no = '{{couponNumber}}'

          -- 광고코드
          AND om.ad_cd = '{{adCode}}'

          -- 전문관코드
          AND sg.specialty_cd = '{{specialtyCode}}'

          -- 담당MD
          AND om.md_id = '{{mdId}}'

        GROUP BY 1

        ORDER BY `{{orderBy}}` ASC

        LIMIT {{size}}

        OFFSET {{number}}
    """.trimIndent()

    val SAMPLE_QUERY_SET_BRAND_ID = """
        SELECT
          om.ord_state_date AS `date`
          ,SUM(om.sell_qty) AS `sellQuantity`
          ,SUM(om.sell_amt) AS `sellAmount`
          ,SUM(om.refund_qty) AS `refundQuantity`
          ,SUM(om.refund_amt) AS `refundAmount`
          ,SUM(om.exchange_qty) AS `exchangeQuantity`
          ,SUM(om.exchange_amt) AS `exchangeAmount`
          ,SUM(om.sell_sub_clm_qty) AS `tradeQuantity`
          ,SUM(om.sell_sub_clm_amt) AS `tradeAmount`
          ,SUM(IF(om.dc_type=1, IF(om.gmv_state IN ('1000', '5000'), om.dc_apply_amt, -1*om.dc_apply_amt), 0)) as `memberDiscounts`
          ,SUM(IF(om.dc_type=2, IF(om.gmv_state IN ('1000', '5000'), om.dc_apply_amt, -1*om.dc_apply_amt), 0)) as `affiliateDiscounts`
          ,SUM(IF(om.dc_type NOT IN (1,2), IF(om.gmv_state IN ('1000', '5000'), om.dc_apply_amt, -1*om.dc_apply_amt), 0)) as `otherDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.coupon_apply_amt-om.coupon_partner_apply_amt, -1*om.coupon_apply_amt-om.coupon_partner_apply_amt)) as `couponDiscounts`
          ,SUM(om.coupon_partner_apply_amt) as `partnerCouponDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.point_apply_amt-om.pre_point_amt, -1*(om.point_apply_amt-om.pre_point_amt))) as `pointDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.pre_point_amt, -1*pre_point_amt)) as `prePointDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.cart_dc_amt, -1*cart_dc_amt)) as `cartDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.group_dc_amt, -1*group_dc_amt)) as `groupDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.total_dc_amt, -1*total_dc_amt)) as `totalDiscounts`
          ,ROUND(SUM(IF(om.gmv_state IN ('1000', '5000'), om.total_dc_amt, -1*total_dc_amt))/SUM(om.price*om.qty)*100, 2) `discountRate`
          ,SUM(om.pay_fee) as `paymentFees`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.recv_amt, -1*om.recv_amt)) as `paymentAmount`
          ,SUM(om.revenue) as `sales`
          ,SUM(om.head_wonga) as `originalPrice`
          ,SUM(om.profit) as `profit`
          ,ROUND(IFNULL(SUM(om.profit)/SUM(om.price*om.qty)*100, 0), 2) as `profitMargin`
          ,SUM(om.head_amt) as `purchasesTradeAmount`
          ,SUM(IF(om.ord_com_type=2, 0, (om.point_apply_amt+om.dc_apply_amt+om.coupon_apply_amt+om.cart_dc_amt+om.group_dc_amt)*IF(om.gmv_state IN ('1000', '5000'),1,-1))) as `purchasesDiscounts`
          ,SUM(om.head_pay_fee) as `purchasesPaymentFees`
          ,SUM(IF(om.ord_com_type=2, 0, om.recv_amt*IF(om.gmv_state IN ('1000', '5000'), 1, -1))) as `purchasesPaymentAmount`
          ,SUM(om.head_wonga) as `purchasesOriginalPrice`
          ,SUM(om.head_profit) as `purchasesProfit`
          ,ROUND(IFNULL(SUM(om.head_profit)/SUM(om.head_amt)*100, 0), 2) as `purchasesProfitMargin`
          ,SUM(om.head_amt)/SUM(om.price*om.qty) * 100 as `purchasesRatio`
          ,SUM(om.partner_amt) as `partnerTradeAmount`
          ,SUM(IF(om.ord_com_type=2, (om.point_apply_amt+om.dc_apply_amt+om.coupon_apply_amt+om.cart_dc_amt+om.group_dc_amt)*IF(om.gmv_state IN ('1000', '5000'),1,-1), 0)) as `partnerDiscounts`
          ,SUM(om.partner_pay_fee) as `partnerPaymentFees`
          ,SUM(IF(om.ord_com_type=2, om.recv_amt*IF(om.gmv_state IN ('1000', '5000'), 1, -1), 0)) as `partnerPaymentAmount`
          ,SUM(om.partner_sale_fee) as `partnerSellFees`
          ,SUM(om.partner_fee) as `partnerFees`
          ,SUM(om.partner_support_amt) as `partnerSellGrants`
          ,ROUND(IFNULL(SUM(om.partner_profit)/SUM(om.partner_amt) * 100, 0), 2) as `partnerProfitMargin`
          ,CEIL(SUM(om.not_included_vat_revenue)) as `salesExcludedVAT`
          ,CEIL(SUM(om.not_included_vat_wonga)) as `originalPriceExcludedVAT`
          ,CEIL(SUM(om.not_included_vat_profit)) as `profitExcludedVAT`
          ,ROUND(CEIL(SUM((om.not_included_vat_revenue)-(om.head_wonga/11*10)))/CEIL(SUM(om.price*om.qty))*100, 2) as `profitMarginExcludedVAT`

        FROM datamart.datamart.orders_merged om
          JOIN datamart.datamart.goods_tags as gt
            ON om.goods_no = gt.goods_no
          JOIN datamart.datamart.coupon as c
            ON om.coupon_no = c.coupon_no
          JOIN datamart.datamart.specialty_goods as sg
            ON om.goods_no = sg.goods_no

        WHERE 
          -- 일자
          om.ord_state_date >= '{{startDate}}'
          AND om.ord_state_date <= '{{endDate}}'

          -- 태그(String List)
          AND gt.tag IN ({{tag}})

          -- 매출시점
          AND if('{{salesStart}}'='SHIPPING_REQUEST', om.state_order, om.state_order_done) = True

          -- 업체
          AND om.com_id = '{{partnerId}}'

          -- 카테고리
          AND om.small_nm = '{{category}}'

          -- 스타일넘버
          AND om.style_no = '{{styleNumber}}'

          -- 상품코드
          AND om.goods_no = '{{goodsNumber}}'

          -- 브랜드
          AND om.brand = 'musinsastandard'

          -- 쿠폰
          AND c.coupon_no = '{{couponNumber}}'

          -- 광고코드
          AND om.ad_cd = '{{adCode}}'

          -- 전문관코드
          AND sg.specialty_cd = '{{specialtyCode}}'

          -- 담당MD
          AND om.md_id = '{{mdId}}'

        GROUP BY 1

        ORDER BY `{{orderBy}}` ASC

        LIMIT {{size}}

        OFFSET {{number}}
    """.trimIndent()

    val SAMPLE_QUERY_EMPTY_BRAND_ID = """
        SELECT
          om.ord_state_date AS `date`
          ,SUM(om.sell_qty) AS `sellQuantity`
          ,SUM(om.sell_amt) AS `sellAmount`
          ,SUM(om.refund_qty) AS `refundQuantity`
          ,SUM(om.refund_amt) AS `refundAmount`
          ,SUM(om.exchange_qty) AS `exchangeQuantity`
          ,SUM(om.exchange_amt) AS `exchangeAmount`
          ,SUM(om.sell_sub_clm_qty) AS `tradeQuantity`
          ,SUM(om.sell_sub_clm_amt) AS `tradeAmount`
          ,SUM(IF(om.dc_type=1, IF(om.gmv_state IN ('1000', '5000'), om.dc_apply_amt, -1*om.dc_apply_amt), 0)) as `memberDiscounts`
          ,SUM(IF(om.dc_type=2, IF(om.gmv_state IN ('1000', '5000'), om.dc_apply_amt, -1*om.dc_apply_amt), 0)) as `affiliateDiscounts`
          ,SUM(IF(om.dc_type NOT IN (1,2), IF(om.gmv_state IN ('1000', '5000'), om.dc_apply_amt, -1*om.dc_apply_amt), 0)) as `otherDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.coupon_apply_amt-om.coupon_partner_apply_amt, -1*om.coupon_apply_amt-om.coupon_partner_apply_amt)) as `couponDiscounts`
          ,SUM(om.coupon_partner_apply_amt) as `partnerCouponDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.point_apply_amt-om.pre_point_amt, -1*(om.point_apply_amt-om.pre_point_amt))) as `pointDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.pre_point_amt, -1*pre_point_amt)) as `prePointDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.cart_dc_amt, -1*cart_dc_amt)) as `cartDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.group_dc_amt, -1*group_dc_amt)) as `groupDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.total_dc_amt, -1*total_dc_amt)) as `totalDiscounts`
          ,ROUND(SUM(IF(om.gmv_state IN ('1000', '5000'), om.total_dc_amt, -1*total_dc_amt))/SUM(om.price*om.qty)*100, 2) `discountRate`
          ,SUM(om.pay_fee) as `paymentFees`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.recv_amt, -1*om.recv_amt)) as `paymentAmount`
          ,SUM(om.revenue) as `sales`
          ,SUM(om.head_wonga) as `originalPrice`
          ,SUM(om.profit) as `profit`
          ,ROUND(IFNULL(SUM(om.profit)/SUM(om.price*om.qty)*100, 0), 2) as `profitMargin`
          ,SUM(om.head_amt) as `purchasesTradeAmount`
          ,SUM(IF(om.ord_com_type=2, 0, (om.point_apply_amt+om.dc_apply_amt+om.coupon_apply_amt+om.cart_dc_amt+om.group_dc_amt)*IF(om.gmv_state IN ('1000', '5000'),1,-1))) as `purchasesDiscounts`
          ,SUM(om.head_pay_fee) as `purchasesPaymentFees`
          ,SUM(IF(om.ord_com_type=2, 0, om.recv_amt*IF(om.gmv_state IN ('1000', '5000'), 1, -1))) as `purchasesPaymentAmount`
          ,SUM(om.head_wonga) as `purchasesOriginalPrice`
          ,SUM(om.head_profit) as `purchasesProfit`
          ,ROUND(IFNULL(SUM(om.head_profit)/SUM(om.head_amt)*100, 0), 2) as `purchasesProfitMargin`
          ,SUM(om.head_amt)/SUM(om.price*om.qty) * 100 as `purchasesRatio`
          ,SUM(om.partner_amt) as `partnerTradeAmount`
          ,SUM(IF(om.ord_com_type=2, (om.point_apply_amt+om.dc_apply_amt+om.coupon_apply_amt+om.cart_dc_amt+om.group_dc_amt)*IF(om.gmv_state IN ('1000', '5000'),1,-1), 0)) as `partnerDiscounts`
          ,SUM(om.partner_pay_fee) as `partnerPaymentFees`
          ,SUM(IF(om.ord_com_type=2, om.recv_amt*IF(om.gmv_state IN ('1000', '5000'), 1, -1), 0)) as `partnerPaymentAmount`
          ,SUM(om.partner_sale_fee) as `partnerSellFees`
          ,SUM(om.partner_fee) as `partnerFees`
          ,SUM(om.partner_support_amt) as `partnerSellGrants`
          ,ROUND(IFNULL(SUM(om.partner_profit)/SUM(om.partner_amt) * 100, 0), 2) as `partnerProfitMargin`
          ,CEIL(SUM(om.not_included_vat_revenue)) as `salesExcludedVAT`
          ,CEIL(SUM(om.not_included_vat_wonga)) as `originalPriceExcludedVAT`
          ,CEIL(SUM(om.not_included_vat_profit)) as `profitExcludedVAT`
          ,ROUND(CEIL(SUM((om.not_included_vat_revenue)-(om.head_wonga/11*10)))/CEIL(SUM(om.price*om.qty))*100, 2) as `profitMarginExcludedVAT`

        FROM datamart.datamart.orders_merged om
          JOIN datamart.datamart.goods_tags as gt
            ON om.goods_no = gt.goods_no
          JOIN datamart.datamart.coupon as c
            ON om.coupon_no = c.coupon_no
          JOIN datamart.datamart.specialty_goods as sg
            ON om.goods_no = sg.goods_no

        WHERE 
          -- 일자
          om.ord_state_date >= '{{startDate}}'
          AND om.ord_state_date <= '{{endDate}}'

          -- 태그(String List)
          AND gt.tag IN ({{tag}})

          -- 매출시점
          AND if('{{salesStart}}'='SHIPPING_REQUEST', om.state_order, om.state_order_done) = True

          -- 업체
          AND om.com_id = '{{partnerId}}'

          -- 카테고리
          AND om.small_nm = '{{category}}'

          -- 스타일넘버
          AND om.style_no = '{{styleNumber}}'

          -- 상품코드
          AND om.goods_no = '{{goodsNumber}}'

          -- 브랜드
        --  AND om.brand = '{{brandId}}'

          -- 쿠폰
          AND c.coupon_no = '{{couponNumber}}'

          -- 광고코드
          AND om.ad_cd = '{{adCode}}'

          -- 전문관코드
          AND sg.specialty_cd = '{{specialtyCode}}'

          -- 담당MD
          AND om.md_id = '{{mdId}}'

        GROUP BY 1

        ORDER BY `{{orderBy}}` ASC

        LIMIT {{size}}

        OFFSET {{number}}
    """.trimIndent()

    val SAMPLE_QUERY_SET_COUPON_NUMBER = """
        SELECT
          om.ord_state_date AS `date`
          ,SUM(om.sell_qty) AS `sellQuantity`
          ,SUM(om.sell_amt) AS `sellAmount`
          ,SUM(om.refund_qty) AS `refundQuantity`
          ,SUM(om.refund_amt) AS `refundAmount`
          ,SUM(om.exchange_qty) AS `exchangeQuantity`
          ,SUM(om.exchange_amt) AS `exchangeAmount`
          ,SUM(om.sell_sub_clm_qty) AS `tradeQuantity`
          ,SUM(om.sell_sub_clm_amt) AS `tradeAmount`
          ,SUM(IF(om.dc_type=1, IF(om.gmv_state IN ('1000', '5000'), om.dc_apply_amt, -1*om.dc_apply_amt), 0)) as `memberDiscounts`
          ,SUM(IF(om.dc_type=2, IF(om.gmv_state IN ('1000', '5000'), om.dc_apply_amt, -1*om.dc_apply_amt), 0)) as `affiliateDiscounts`
          ,SUM(IF(om.dc_type NOT IN (1,2), IF(om.gmv_state IN ('1000', '5000'), om.dc_apply_amt, -1*om.dc_apply_amt), 0)) as `otherDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.coupon_apply_amt-om.coupon_partner_apply_amt, -1*om.coupon_apply_amt-om.coupon_partner_apply_amt)) as `couponDiscounts`
          ,SUM(om.coupon_partner_apply_amt) as `partnerCouponDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.point_apply_amt-om.pre_point_amt, -1*(om.point_apply_amt-om.pre_point_amt))) as `pointDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.pre_point_amt, -1*pre_point_amt)) as `prePointDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.cart_dc_amt, -1*cart_dc_amt)) as `cartDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.group_dc_amt, -1*group_dc_amt)) as `groupDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.total_dc_amt, -1*total_dc_amt)) as `totalDiscounts`
          ,ROUND(SUM(IF(om.gmv_state IN ('1000', '5000'), om.total_dc_amt, -1*total_dc_amt))/SUM(om.price*om.qty)*100, 2) `discountRate`
          ,SUM(om.pay_fee) as `paymentFees`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.recv_amt, -1*om.recv_amt)) as `paymentAmount`
          ,SUM(om.revenue) as `sales`
          ,SUM(om.head_wonga) as `originalPrice`
          ,SUM(om.profit) as `profit`
          ,ROUND(IFNULL(SUM(om.profit)/SUM(om.price*om.qty)*100, 0), 2) as `profitMargin`
          ,SUM(om.head_amt) as `purchasesTradeAmount`
          ,SUM(IF(om.ord_com_type=2, 0, (om.point_apply_amt+om.dc_apply_amt+om.coupon_apply_amt+om.cart_dc_amt+om.group_dc_amt)*IF(om.gmv_state IN ('1000', '5000'),1,-1))) as `purchasesDiscounts`
          ,SUM(om.head_pay_fee) as `purchasesPaymentFees`
          ,SUM(IF(om.ord_com_type=2, 0, om.recv_amt*IF(om.gmv_state IN ('1000', '5000'), 1, -1))) as `purchasesPaymentAmount`
          ,SUM(om.head_wonga) as `purchasesOriginalPrice`
          ,SUM(om.head_profit) as `purchasesProfit`
          ,ROUND(IFNULL(SUM(om.head_profit)/SUM(om.head_amt)*100, 0), 2) as `purchasesProfitMargin`
          ,SUM(om.head_amt)/SUM(om.price*om.qty) * 100 as `purchasesRatio`
          ,SUM(om.partner_amt) as `partnerTradeAmount`
          ,SUM(IF(om.ord_com_type=2, (om.point_apply_amt+om.dc_apply_amt+om.coupon_apply_amt+om.cart_dc_amt+om.group_dc_amt)*IF(om.gmv_state IN ('1000', '5000'),1,-1), 0)) as `partnerDiscounts`
          ,SUM(om.partner_pay_fee) as `partnerPaymentFees`
          ,SUM(IF(om.ord_com_type=2, om.recv_amt*IF(om.gmv_state IN ('1000', '5000'), 1, -1), 0)) as `partnerPaymentAmount`
          ,SUM(om.partner_sale_fee) as `partnerSellFees`
          ,SUM(om.partner_fee) as `partnerFees`
          ,SUM(om.partner_support_amt) as `partnerSellGrants`
          ,ROUND(IFNULL(SUM(om.partner_profit)/SUM(om.partner_amt) * 100, 0), 2) as `partnerProfitMargin`
          ,CEIL(SUM(om.not_included_vat_revenue)) as `salesExcludedVAT`
          ,CEIL(SUM(om.not_included_vat_wonga)) as `originalPriceExcludedVAT`
          ,CEIL(SUM(om.not_included_vat_profit)) as `profitExcludedVAT`
          ,ROUND(CEIL(SUM((om.not_included_vat_revenue)-(om.head_wonga/11*10)))/CEIL(SUM(om.price*om.qty))*100, 2) as `profitMarginExcludedVAT`

        FROM datamart.datamart.orders_merged om
          JOIN datamart.datamart.goods_tags as gt
            ON om.goods_no = gt.goods_no
          JOIN datamart.datamart.coupon as c
            ON om.coupon_no = c.coupon_no
          JOIN datamart.datamart.specialty_goods as sg
            ON om.goods_no = sg.goods_no

        WHERE 
          -- 일자
          om.ord_state_date >= '{{startDate}}'
          AND om.ord_state_date <= '{{endDate}}'

          -- 태그(String List)
          AND gt.tag IN ({{tag}})

          -- 매출시점
          AND if('{{salesStart}}'='SHIPPING_REQUEST', om.state_order, om.state_order_done) = True

          -- 업체
          AND om.com_id = '{{partnerId}}'

          -- 카테고리
          AND om.small_nm = '{{category}}'

          -- 스타일넘버
          AND om.style_no = '{{styleNumber}}'

          -- 상품코드
          AND om.goods_no = '{{goodsNumber}}'

          -- 브랜드
          AND om.brand = '{{brandId}}'

          -- 쿠폰
          AND c.coupon_no = '72852'

          -- 광고코드
          AND om.ad_cd = '{{adCode}}'

          -- 전문관코드
          AND sg.specialty_cd = '{{specialtyCode}}'

          -- 담당MD
          AND om.md_id = '{{mdId}}'

        GROUP BY 1

        ORDER BY `{{orderBy}}` ASC

        LIMIT {{size}}

        OFFSET {{number}}
    """.trimIndent()

    val SAMPLE_QUERY_EMPTY_COUPON_NUMBER = """
        SELECT
          om.ord_state_date AS `date`
          ,SUM(om.sell_qty) AS `sellQuantity`
          ,SUM(om.sell_amt) AS `sellAmount`
          ,SUM(om.refund_qty) AS `refundQuantity`
          ,SUM(om.refund_amt) AS `refundAmount`
          ,SUM(om.exchange_qty) AS `exchangeQuantity`
          ,SUM(om.exchange_amt) AS `exchangeAmount`
          ,SUM(om.sell_sub_clm_qty) AS `tradeQuantity`
          ,SUM(om.sell_sub_clm_amt) AS `tradeAmount`
          ,SUM(IF(om.dc_type=1, IF(om.gmv_state IN ('1000', '5000'), om.dc_apply_amt, -1*om.dc_apply_amt), 0)) as `memberDiscounts`
          ,SUM(IF(om.dc_type=2, IF(om.gmv_state IN ('1000', '5000'), om.dc_apply_amt, -1*om.dc_apply_amt), 0)) as `affiliateDiscounts`
          ,SUM(IF(om.dc_type NOT IN (1,2), IF(om.gmv_state IN ('1000', '5000'), om.dc_apply_amt, -1*om.dc_apply_amt), 0)) as `otherDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.coupon_apply_amt-om.coupon_partner_apply_amt, -1*om.coupon_apply_amt-om.coupon_partner_apply_amt)) as `couponDiscounts`
          ,SUM(om.coupon_partner_apply_amt) as `partnerCouponDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.point_apply_amt-om.pre_point_amt, -1*(om.point_apply_amt-om.pre_point_amt))) as `pointDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.pre_point_amt, -1*pre_point_amt)) as `prePointDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.cart_dc_amt, -1*cart_dc_amt)) as `cartDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.group_dc_amt, -1*group_dc_amt)) as `groupDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.total_dc_amt, -1*total_dc_amt)) as `totalDiscounts`
          ,ROUND(SUM(IF(om.gmv_state IN ('1000', '5000'), om.total_dc_amt, -1*total_dc_amt))/SUM(om.price*om.qty)*100, 2) `discountRate`
          ,SUM(om.pay_fee) as `paymentFees`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.recv_amt, -1*om.recv_amt)) as `paymentAmount`
          ,SUM(om.revenue) as `sales`
          ,SUM(om.head_wonga) as `originalPrice`
          ,SUM(om.profit) as `profit`
          ,ROUND(IFNULL(SUM(om.profit)/SUM(om.price*om.qty)*100, 0), 2) as `profitMargin`
          ,SUM(om.head_amt) as `purchasesTradeAmount`
          ,SUM(IF(om.ord_com_type=2, 0, (om.point_apply_amt+om.dc_apply_amt+om.coupon_apply_amt+om.cart_dc_amt+om.group_dc_amt)*IF(om.gmv_state IN ('1000', '5000'),1,-1))) as `purchasesDiscounts`
          ,SUM(om.head_pay_fee) as `purchasesPaymentFees`
          ,SUM(IF(om.ord_com_type=2, 0, om.recv_amt*IF(om.gmv_state IN ('1000', '5000'), 1, -1))) as `purchasesPaymentAmount`
          ,SUM(om.head_wonga) as `purchasesOriginalPrice`
          ,SUM(om.head_profit) as `purchasesProfit`
          ,ROUND(IFNULL(SUM(om.head_profit)/SUM(om.head_amt)*100, 0), 2) as `purchasesProfitMargin`
          ,SUM(om.head_amt)/SUM(om.price*om.qty) * 100 as `purchasesRatio`
          ,SUM(om.partner_amt) as `partnerTradeAmount`
          ,SUM(IF(om.ord_com_type=2, (om.point_apply_amt+om.dc_apply_amt+om.coupon_apply_amt+om.cart_dc_amt+om.group_dc_amt)*IF(om.gmv_state IN ('1000', '5000'),1,-1), 0)) as `partnerDiscounts`
          ,SUM(om.partner_pay_fee) as `partnerPaymentFees`
          ,SUM(IF(om.ord_com_type=2, om.recv_amt*IF(om.gmv_state IN ('1000', '5000'), 1, -1), 0)) as `partnerPaymentAmount`
          ,SUM(om.partner_sale_fee) as `partnerSellFees`
          ,SUM(om.partner_fee) as `partnerFees`
          ,SUM(om.partner_support_amt) as `partnerSellGrants`
          ,ROUND(IFNULL(SUM(om.partner_profit)/SUM(om.partner_amt) * 100, 0), 2) as `partnerProfitMargin`
          ,CEIL(SUM(om.not_included_vat_revenue)) as `salesExcludedVAT`
          ,CEIL(SUM(om.not_included_vat_wonga)) as `originalPriceExcludedVAT`
          ,CEIL(SUM(om.not_included_vat_profit)) as `profitExcludedVAT`
          ,ROUND(CEIL(SUM((om.not_included_vat_revenue)-(om.head_wonga/11*10)))/CEIL(SUM(om.price*om.qty))*100, 2) as `profitMarginExcludedVAT`

        FROM datamart.datamart.orders_merged om
          JOIN datamart.datamart.goods_tags as gt
            ON om.goods_no = gt.goods_no
          JOIN datamart.datamart.coupon as c
            ON om.coupon_no = c.coupon_no
          JOIN datamart.datamart.specialty_goods as sg
            ON om.goods_no = sg.goods_no

        WHERE 
          -- 일자
          om.ord_state_date >= '{{startDate}}'
          AND om.ord_state_date <= '{{endDate}}'

          -- 태그(String List)
          AND gt.tag IN ({{tag}})

          -- 매출시점
          AND if('{{salesStart}}'='SHIPPING_REQUEST', om.state_order, om.state_order_done) = True

          -- 업체
          AND om.com_id = '{{partnerId}}'

          -- 카테고리
          AND om.small_nm = '{{category}}'

          -- 스타일넘버
          AND om.style_no = '{{styleNumber}}'

          -- 상품코드
          AND om.goods_no = '{{goodsNumber}}'

          -- 브랜드
          AND om.brand = '{{brandId}}'

          -- 쿠폰
        --  AND c.coupon_no = '{{couponNumber}}'

          -- 광고코드
          AND om.ad_cd = '{{adCode}}'

          -- 전문관코드
          AND sg.specialty_cd = '{{specialtyCode}}'

          -- 담당MD
          AND om.md_id = '{{mdId}}'

        GROUP BY 1

        ORDER BY `{{orderBy}}` ASC

        LIMIT {{size}}

        OFFSET {{number}}
    """.trimIndent()

    val SAMPLE_QUERY_SET_AD_CODE = """
        SELECT
          om.ord_state_date AS `date`
          ,SUM(om.sell_qty) AS `sellQuantity`
          ,SUM(om.sell_amt) AS `sellAmount`
          ,SUM(om.refund_qty) AS `refundQuantity`
          ,SUM(om.refund_amt) AS `refundAmount`
          ,SUM(om.exchange_qty) AS `exchangeQuantity`
          ,SUM(om.exchange_amt) AS `exchangeAmount`
          ,SUM(om.sell_sub_clm_qty) AS `tradeQuantity`
          ,SUM(om.sell_sub_clm_amt) AS `tradeAmount`
          ,SUM(IF(om.dc_type=1, IF(om.gmv_state IN ('1000', '5000'), om.dc_apply_amt, -1*om.dc_apply_amt), 0)) as `memberDiscounts`
          ,SUM(IF(om.dc_type=2, IF(om.gmv_state IN ('1000', '5000'), om.dc_apply_amt, -1*om.dc_apply_amt), 0)) as `affiliateDiscounts`
          ,SUM(IF(om.dc_type NOT IN (1,2), IF(om.gmv_state IN ('1000', '5000'), om.dc_apply_amt, -1*om.dc_apply_amt), 0)) as `otherDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.coupon_apply_amt-om.coupon_partner_apply_amt, -1*om.coupon_apply_amt-om.coupon_partner_apply_amt)) as `couponDiscounts`
          ,SUM(om.coupon_partner_apply_amt) as `partnerCouponDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.point_apply_amt-om.pre_point_amt, -1*(om.point_apply_amt-om.pre_point_amt))) as `pointDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.pre_point_amt, -1*pre_point_amt)) as `prePointDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.cart_dc_amt, -1*cart_dc_amt)) as `cartDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.group_dc_amt, -1*group_dc_amt)) as `groupDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.total_dc_amt, -1*total_dc_amt)) as `totalDiscounts`
          ,ROUND(SUM(IF(om.gmv_state IN ('1000', '5000'), om.total_dc_amt, -1*total_dc_amt))/SUM(om.price*om.qty)*100, 2) `discountRate`
          ,SUM(om.pay_fee) as `paymentFees`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.recv_amt, -1*om.recv_amt)) as `paymentAmount`
          ,SUM(om.revenue) as `sales`
          ,SUM(om.head_wonga) as `originalPrice`
          ,SUM(om.profit) as `profit`
          ,ROUND(IFNULL(SUM(om.profit)/SUM(om.price*om.qty)*100, 0), 2) as `profitMargin`
          ,SUM(om.head_amt) as `purchasesTradeAmount`
          ,SUM(IF(om.ord_com_type=2, 0, (om.point_apply_amt+om.dc_apply_amt+om.coupon_apply_amt+om.cart_dc_amt+om.group_dc_amt)*IF(om.gmv_state IN ('1000', '5000'),1,-1))) as `purchasesDiscounts`
          ,SUM(om.head_pay_fee) as `purchasesPaymentFees`
          ,SUM(IF(om.ord_com_type=2, 0, om.recv_amt*IF(om.gmv_state IN ('1000', '5000'), 1, -1))) as `purchasesPaymentAmount`
          ,SUM(om.head_wonga) as `purchasesOriginalPrice`
          ,SUM(om.head_profit) as `purchasesProfit`
          ,ROUND(IFNULL(SUM(om.head_profit)/SUM(om.head_amt)*100, 0), 2) as `purchasesProfitMargin`
          ,SUM(om.head_amt)/SUM(om.price*om.qty) * 100 as `purchasesRatio`
          ,SUM(om.partner_amt) as `partnerTradeAmount`
          ,SUM(IF(om.ord_com_type=2, (om.point_apply_amt+om.dc_apply_amt+om.coupon_apply_amt+om.cart_dc_amt+om.group_dc_amt)*IF(om.gmv_state IN ('1000', '5000'),1,-1), 0)) as `partnerDiscounts`
          ,SUM(om.partner_pay_fee) as `partnerPaymentFees`
          ,SUM(IF(om.ord_com_type=2, om.recv_amt*IF(om.gmv_state IN ('1000', '5000'), 1, -1), 0)) as `partnerPaymentAmount`
          ,SUM(om.partner_sale_fee) as `partnerSellFees`
          ,SUM(om.partner_fee) as `partnerFees`
          ,SUM(om.partner_support_amt) as `partnerSellGrants`
          ,ROUND(IFNULL(SUM(om.partner_profit)/SUM(om.partner_amt) * 100, 0), 2) as `partnerProfitMargin`
          ,CEIL(SUM(om.not_included_vat_revenue)) as `salesExcludedVAT`
          ,CEIL(SUM(om.not_included_vat_wonga)) as `originalPriceExcludedVAT`
          ,CEIL(SUM(om.not_included_vat_profit)) as `profitExcludedVAT`
          ,ROUND(CEIL(SUM((om.not_included_vat_revenue)-(om.head_wonga/11*10)))/CEIL(SUM(om.price*om.qty))*100, 2) as `profitMarginExcludedVAT`

        FROM datamart.datamart.orders_merged om
          JOIN datamart.datamart.goods_tags as gt
            ON om.goods_no = gt.goods_no
          JOIN datamart.datamart.coupon as c
            ON om.coupon_no = c.coupon_no
          JOIN datamart.datamart.specialty_goods as sg
            ON om.goods_no = sg.goods_no

        WHERE 
          -- 일자
          om.ord_state_date >= '{{startDate}}'
          AND om.ord_state_date <= '{{endDate}}'

          -- 태그(String List)
          AND gt.tag IN ({{tag}})

          -- 매출시점
          AND if('{{salesStart}}'='SHIPPING_REQUEST', om.state_order, om.state_order_done) = True

          -- 업체
          AND om.com_id = '{{partnerId}}'

          -- 카테고리
          AND om.small_nm = '{{category}}'

          -- 스타일넘버
          AND om.style_no = '{{styleNumber}}'

          -- 상품코드
          AND om.goods_no = '{{goodsNumber}}'

          -- 브랜드
          AND om.brand = '{{brandId}}'

          -- 쿠폰
          AND c.coupon_no = '{{couponNumber}}'

          -- 광고코드
          AND om.ad_cd = 'REFCRLC003'

          -- 전문관코드
          AND sg.specialty_cd = '{{specialtyCode}}'

          -- 담당MD
          AND om.md_id = '{{mdId}}'

        GROUP BY 1

        ORDER BY `{{orderBy}}` ASC

        LIMIT {{size}}

        OFFSET {{number}}
    """.trimIndent()

    val SAMPLE_QUERY_EMPTY_AD_CODE = """
        SELECT
          om.ord_state_date AS `date`
          ,SUM(om.sell_qty) AS `sellQuantity`
          ,SUM(om.sell_amt) AS `sellAmount`
          ,SUM(om.refund_qty) AS `refundQuantity`
          ,SUM(om.refund_amt) AS `refundAmount`
          ,SUM(om.exchange_qty) AS `exchangeQuantity`
          ,SUM(om.exchange_amt) AS `exchangeAmount`
          ,SUM(om.sell_sub_clm_qty) AS `tradeQuantity`
          ,SUM(om.sell_sub_clm_amt) AS `tradeAmount`
          ,SUM(IF(om.dc_type=1, IF(om.gmv_state IN ('1000', '5000'), om.dc_apply_amt, -1*om.dc_apply_amt), 0)) as `memberDiscounts`
          ,SUM(IF(om.dc_type=2, IF(om.gmv_state IN ('1000', '5000'), om.dc_apply_amt, -1*om.dc_apply_amt), 0)) as `affiliateDiscounts`
          ,SUM(IF(om.dc_type NOT IN (1,2), IF(om.gmv_state IN ('1000', '5000'), om.dc_apply_amt, -1*om.dc_apply_amt), 0)) as `otherDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.coupon_apply_amt-om.coupon_partner_apply_amt, -1*om.coupon_apply_amt-om.coupon_partner_apply_amt)) as `couponDiscounts`
          ,SUM(om.coupon_partner_apply_amt) as `partnerCouponDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.point_apply_amt-om.pre_point_amt, -1*(om.point_apply_amt-om.pre_point_amt))) as `pointDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.pre_point_amt, -1*pre_point_amt)) as `prePointDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.cart_dc_amt, -1*cart_dc_amt)) as `cartDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.group_dc_amt, -1*group_dc_amt)) as `groupDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.total_dc_amt, -1*total_dc_amt)) as `totalDiscounts`
          ,ROUND(SUM(IF(om.gmv_state IN ('1000', '5000'), om.total_dc_amt, -1*total_dc_amt))/SUM(om.price*om.qty)*100, 2) `discountRate`
          ,SUM(om.pay_fee) as `paymentFees`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.recv_amt, -1*om.recv_amt)) as `paymentAmount`
          ,SUM(om.revenue) as `sales`
          ,SUM(om.head_wonga) as `originalPrice`
          ,SUM(om.profit) as `profit`
          ,ROUND(IFNULL(SUM(om.profit)/SUM(om.price*om.qty)*100, 0), 2) as `profitMargin`
          ,SUM(om.head_amt) as `purchasesTradeAmount`
          ,SUM(IF(om.ord_com_type=2, 0, (om.point_apply_amt+om.dc_apply_amt+om.coupon_apply_amt+om.cart_dc_amt+om.group_dc_amt)*IF(om.gmv_state IN ('1000', '5000'),1,-1))) as `purchasesDiscounts`
          ,SUM(om.head_pay_fee) as `purchasesPaymentFees`
          ,SUM(IF(om.ord_com_type=2, 0, om.recv_amt*IF(om.gmv_state IN ('1000', '5000'), 1, -1))) as `purchasesPaymentAmount`
          ,SUM(om.head_wonga) as `purchasesOriginalPrice`
          ,SUM(om.head_profit) as `purchasesProfit`
          ,ROUND(IFNULL(SUM(om.head_profit)/SUM(om.head_amt)*100, 0), 2) as `purchasesProfitMargin`
          ,SUM(om.head_amt)/SUM(om.price*om.qty) * 100 as `purchasesRatio`
          ,SUM(om.partner_amt) as `partnerTradeAmount`
          ,SUM(IF(om.ord_com_type=2, (om.point_apply_amt+om.dc_apply_amt+om.coupon_apply_amt+om.cart_dc_amt+om.group_dc_amt)*IF(om.gmv_state IN ('1000', '5000'),1,-1), 0)) as `partnerDiscounts`
          ,SUM(om.partner_pay_fee) as `partnerPaymentFees`
          ,SUM(IF(om.ord_com_type=2, om.recv_amt*IF(om.gmv_state IN ('1000', '5000'), 1, -1), 0)) as `partnerPaymentAmount`
          ,SUM(om.partner_sale_fee) as `partnerSellFees`
          ,SUM(om.partner_fee) as `partnerFees`
          ,SUM(om.partner_support_amt) as `partnerSellGrants`
          ,ROUND(IFNULL(SUM(om.partner_profit)/SUM(om.partner_amt) * 100, 0), 2) as `partnerProfitMargin`
          ,CEIL(SUM(om.not_included_vat_revenue)) as `salesExcludedVAT`
          ,CEIL(SUM(om.not_included_vat_wonga)) as `originalPriceExcludedVAT`
          ,CEIL(SUM(om.not_included_vat_profit)) as `profitExcludedVAT`
          ,ROUND(CEIL(SUM((om.not_included_vat_revenue)-(om.head_wonga/11*10)))/CEIL(SUM(om.price*om.qty))*100, 2) as `profitMarginExcludedVAT`

        FROM datamart.datamart.orders_merged om
          JOIN datamart.datamart.goods_tags as gt
            ON om.goods_no = gt.goods_no
          JOIN datamart.datamart.coupon as c
            ON om.coupon_no = c.coupon_no
          JOIN datamart.datamart.specialty_goods as sg
            ON om.goods_no = sg.goods_no

        WHERE 
          -- 일자
          om.ord_state_date >= '{{startDate}}'
          AND om.ord_state_date <= '{{endDate}}'

          -- 태그(String List)
          AND gt.tag IN ({{tag}})

          -- 매출시점
          AND if('{{salesStart}}'='SHIPPING_REQUEST', om.state_order, om.state_order_done) = True

          -- 업체
          AND om.com_id = '{{partnerId}}'

          -- 카테고리
          AND om.small_nm = '{{category}}'

          -- 스타일넘버
          AND om.style_no = '{{styleNumber}}'

          -- 상품코드
          AND om.goods_no = '{{goodsNumber}}'

          -- 브랜드
          AND om.brand = '{{brandId}}'

          -- 쿠폰
          AND c.coupon_no = '{{couponNumber}}'

          -- 광고코드
        --  AND om.ad_cd = '{{adCode}}'

          -- 전문관코드
          AND sg.specialty_cd = '{{specialtyCode}}'

          -- 담당MD
          AND om.md_id = '{{mdId}}'

        GROUP BY 1

        ORDER BY `{{orderBy}}` ASC

        LIMIT {{size}}

        OFFSET {{number}}
    """.trimIndent()

    val SAMPLE_QUERY_SET_SPECIALTY_CODE = """
        SELECT
          om.ord_state_date AS `date`
          ,SUM(om.sell_qty) AS `sellQuantity`
          ,SUM(om.sell_amt) AS `sellAmount`
          ,SUM(om.refund_qty) AS `refundQuantity`
          ,SUM(om.refund_amt) AS `refundAmount`
          ,SUM(om.exchange_qty) AS `exchangeQuantity`
          ,SUM(om.exchange_amt) AS `exchangeAmount`
          ,SUM(om.sell_sub_clm_qty) AS `tradeQuantity`
          ,SUM(om.sell_sub_clm_amt) AS `tradeAmount`
          ,SUM(IF(om.dc_type=1, IF(om.gmv_state IN ('1000', '5000'), om.dc_apply_amt, -1*om.dc_apply_amt), 0)) as `memberDiscounts`
          ,SUM(IF(om.dc_type=2, IF(om.gmv_state IN ('1000', '5000'), om.dc_apply_amt, -1*om.dc_apply_amt), 0)) as `affiliateDiscounts`
          ,SUM(IF(om.dc_type NOT IN (1,2), IF(om.gmv_state IN ('1000', '5000'), om.dc_apply_amt, -1*om.dc_apply_amt), 0)) as `otherDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.coupon_apply_amt-om.coupon_partner_apply_amt, -1*om.coupon_apply_amt-om.coupon_partner_apply_amt)) as `couponDiscounts`
          ,SUM(om.coupon_partner_apply_amt) as `partnerCouponDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.point_apply_amt-om.pre_point_amt, -1*(om.point_apply_amt-om.pre_point_amt))) as `pointDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.pre_point_amt, -1*pre_point_amt)) as `prePointDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.cart_dc_amt, -1*cart_dc_amt)) as `cartDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.group_dc_amt, -1*group_dc_amt)) as `groupDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.total_dc_amt, -1*total_dc_amt)) as `totalDiscounts`
          ,ROUND(SUM(IF(om.gmv_state IN ('1000', '5000'), om.total_dc_amt, -1*total_dc_amt))/SUM(om.price*om.qty)*100, 2) `discountRate`
          ,SUM(om.pay_fee) as `paymentFees`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.recv_amt, -1*om.recv_amt)) as `paymentAmount`
          ,SUM(om.revenue) as `sales`
          ,SUM(om.head_wonga) as `originalPrice`
          ,SUM(om.profit) as `profit`
          ,ROUND(IFNULL(SUM(om.profit)/SUM(om.price*om.qty)*100, 0), 2) as `profitMargin`
          ,SUM(om.head_amt) as `purchasesTradeAmount`
          ,SUM(IF(om.ord_com_type=2, 0, (om.point_apply_amt+om.dc_apply_amt+om.coupon_apply_amt+om.cart_dc_amt+om.group_dc_amt)*IF(om.gmv_state IN ('1000', '5000'),1,-1))) as `purchasesDiscounts`
          ,SUM(om.head_pay_fee) as `purchasesPaymentFees`
          ,SUM(IF(om.ord_com_type=2, 0, om.recv_amt*IF(om.gmv_state IN ('1000', '5000'), 1, -1))) as `purchasesPaymentAmount`
          ,SUM(om.head_wonga) as `purchasesOriginalPrice`
          ,SUM(om.head_profit) as `purchasesProfit`
          ,ROUND(IFNULL(SUM(om.head_profit)/SUM(om.head_amt)*100, 0), 2) as `purchasesProfitMargin`
          ,SUM(om.head_amt)/SUM(om.price*om.qty) * 100 as `purchasesRatio`
          ,SUM(om.partner_amt) as `partnerTradeAmount`
          ,SUM(IF(om.ord_com_type=2, (om.point_apply_amt+om.dc_apply_amt+om.coupon_apply_amt+om.cart_dc_amt+om.group_dc_amt)*IF(om.gmv_state IN ('1000', '5000'),1,-1), 0)) as `partnerDiscounts`
          ,SUM(om.partner_pay_fee) as `partnerPaymentFees`
          ,SUM(IF(om.ord_com_type=2, om.recv_amt*IF(om.gmv_state IN ('1000', '5000'), 1, -1), 0)) as `partnerPaymentAmount`
          ,SUM(om.partner_sale_fee) as `partnerSellFees`
          ,SUM(om.partner_fee) as `partnerFees`
          ,SUM(om.partner_support_amt) as `partnerSellGrants`
          ,ROUND(IFNULL(SUM(om.partner_profit)/SUM(om.partner_amt) * 100, 0), 2) as `partnerProfitMargin`
          ,CEIL(SUM(om.not_included_vat_revenue)) as `salesExcludedVAT`
          ,CEIL(SUM(om.not_included_vat_wonga)) as `originalPriceExcludedVAT`
          ,CEIL(SUM(om.not_included_vat_profit)) as `profitExcludedVAT`
          ,ROUND(CEIL(SUM((om.not_included_vat_revenue)-(om.head_wonga/11*10)))/CEIL(SUM(om.price*om.qty))*100, 2) as `profitMarginExcludedVAT`

        FROM datamart.datamart.orders_merged om
          JOIN datamart.datamart.goods_tags as gt
            ON om.goods_no = gt.goods_no
          JOIN datamart.datamart.coupon as c
            ON om.coupon_no = c.coupon_no
          JOIN datamart.datamart.specialty_goods as sg
            ON om.goods_no = sg.goods_no

        WHERE 
          -- 일자
          om.ord_state_date >= '{{startDate}}'
          AND om.ord_state_date <= '{{endDate}}'

          -- 태그(String List)
          AND gt.tag IN ({{tag}})

          -- 매출시점
          AND if('{{salesStart}}'='SHIPPING_REQUEST', om.state_order, om.state_order_done) = True

          -- 업체
          AND om.com_id = '{{partnerId}}'

          -- 카테고리
          AND om.small_nm = '{{category}}'

          -- 스타일넘버
          AND om.style_no = '{{styleNumber}}'

          -- 상품코드
          AND om.goods_no = '{{goodsNumber}}'

          -- 브랜드
          AND om.brand = '{{brandId}}'

          -- 쿠폰
          AND c.coupon_no = '{{couponNumber}}'

          -- 광고코드
          AND om.ad_cd = '{{adCode}}'

          -- 전문관코드
          AND sg.specialty_cd = 'golf'

          -- 담당MD
          AND om.md_id = '{{mdId}}'

        GROUP BY 1

        ORDER BY `{{orderBy}}` ASC

        LIMIT {{size}}

        OFFSET {{number}}
    """.trimIndent()

    val SAMPLE_QUERY_EMPTY_SPECIALTY_CODE = """
        SELECT
          om.ord_state_date AS `date`
          ,SUM(om.sell_qty) AS `sellQuantity`
          ,SUM(om.sell_amt) AS `sellAmount`
          ,SUM(om.refund_qty) AS `refundQuantity`
          ,SUM(om.refund_amt) AS `refundAmount`
          ,SUM(om.exchange_qty) AS `exchangeQuantity`
          ,SUM(om.exchange_amt) AS `exchangeAmount`
          ,SUM(om.sell_sub_clm_qty) AS `tradeQuantity`
          ,SUM(om.sell_sub_clm_amt) AS `tradeAmount`
          ,SUM(IF(om.dc_type=1, IF(om.gmv_state IN ('1000', '5000'), om.dc_apply_amt, -1*om.dc_apply_amt), 0)) as `memberDiscounts`
          ,SUM(IF(om.dc_type=2, IF(om.gmv_state IN ('1000', '5000'), om.dc_apply_amt, -1*om.dc_apply_amt), 0)) as `affiliateDiscounts`
          ,SUM(IF(om.dc_type NOT IN (1,2), IF(om.gmv_state IN ('1000', '5000'), om.dc_apply_amt, -1*om.dc_apply_amt), 0)) as `otherDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.coupon_apply_amt-om.coupon_partner_apply_amt, -1*om.coupon_apply_amt-om.coupon_partner_apply_amt)) as `couponDiscounts`
          ,SUM(om.coupon_partner_apply_amt) as `partnerCouponDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.point_apply_amt-om.pre_point_amt, -1*(om.point_apply_amt-om.pre_point_amt))) as `pointDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.pre_point_amt, -1*pre_point_amt)) as `prePointDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.cart_dc_amt, -1*cart_dc_amt)) as `cartDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.group_dc_amt, -1*group_dc_amt)) as `groupDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.total_dc_amt, -1*total_dc_amt)) as `totalDiscounts`
          ,ROUND(SUM(IF(om.gmv_state IN ('1000', '5000'), om.total_dc_amt, -1*total_dc_amt))/SUM(om.price*om.qty)*100, 2) `discountRate`
          ,SUM(om.pay_fee) as `paymentFees`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.recv_amt, -1*om.recv_amt)) as `paymentAmount`
          ,SUM(om.revenue) as `sales`
          ,SUM(om.head_wonga) as `originalPrice`
          ,SUM(om.profit) as `profit`
          ,ROUND(IFNULL(SUM(om.profit)/SUM(om.price*om.qty)*100, 0), 2) as `profitMargin`
          ,SUM(om.head_amt) as `purchasesTradeAmount`
          ,SUM(IF(om.ord_com_type=2, 0, (om.point_apply_amt+om.dc_apply_amt+om.coupon_apply_amt+om.cart_dc_amt+om.group_dc_amt)*IF(om.gmv_state IN ('1000', '5000'),1,-1))) as `purchasesDiscounts`
          ,SUM(om.head_pay_fee) as `purchasesPaymentFees`
          ,SUM(IF(om.ord_com_type=2, 0, om.recv_amt*IF(om.gmv_state IN ('1000', '5000'), 1, -1))) as `purchasesPaymentAmount`
          ,SUM(om.head_wonga) as `purchasesOriginalPrice`
          ,SUM(om.head_profit) as `purchasesProfit`
          ,ROUND(IFNULL(SUM(om.head_profit)/SUM(om.head_amt)*100, 0), 2) as `purchasesProfitMargin`
          ,SUM(om.head_amt)/SUM(om.price*om.qty) * 100 as `purchasesRatio`
          ,SUM(om.partner_amt) as `partnerTradeAmount`
          ,SUM(IF(om.ord_com_type=2, (om.point_apply_amt+om.dc_apply_amt+om.coupon_apply_amt+om.cart_dc_amt+om.group_dc_amt)*IF(om.gmv_state IN ('1000', '5000'),1,-1), 0)) as `partnerDiscounts`
          ,SUM(om.partner_pay_fee) as `partnerPaymentFees`
          ,SUM(IF(om.ord_com_type=2, om.recv_amt*IF(om.gmv_state IN ('1000', '5000'), 1, -1), 0)) as `partnerPaymentAmount`
          ,SUM(om.partner_sale_fee) as `partnerSellFees`
          ,SUM(om.partner_fee) as `partnerFees`
          ,SUM(om.partner_support_amt) as `partnerSellGrants`
          ,ROUND(IFNULL(SUM(om.partner_profit)/SUM(om.partner_amt) * 100, 0), 2) as `partnerProfitMargin`
          ,CEIL(SUM(om.not_included_vat_revenue)) as `salesExcludedVAT`
          ,CEIL(SUM(om.not_included_vat_wonga)) as `originalPriceExcludedVAT`
          ,CEIL(SUM(om.not_included_vat_profit)) as `profitExcludedVAT`
          ,ROUND(CEIL(SUM((om.not_included_vat_revenue)-(om.head_wonga/11*10)))/CEIL(SUM(om.price*om.qty))*100, 2) as `profitMarginExcludedVAT`

        FROM datamart.datamart.orders_merged om
          JOIN datamart.datamart.goods_tags as gt
            ON om.goods_no = gt.goods_no
          JOIN datamart.datamart.coupon as c
            ON om.coupon_no = c.coupon_no
          JOIN datamart.datamart.specialty_goods as sg
            ON om.goods_no = sg.goods_no

        WHERE 
          -- 일자
          om.ord_state_date >= '{{startDate}}'
          AND om.ord_state_date <= '{{endDate}}'

          -- 태그(String List)
          AND gt.tag IN ({{tag}})

          -- 매출시점
          AND if('{{salesStart}}'='SHIPPING_REQUEST', om.state_order, om.state_order_done) = True

          -- 업체
          AND om.com_id = '{{partnerId}}'

          -- 카테고리
          AND om.small_nm = '{{category}}'

          -- 스타일넘버
          AND om.style_no = '{{styleNumber}}'

          -- 상품코드
          AND om.goods_no = '{{goodsNumber}}'

          -- 브랜드
          AND om.brand = '{{brandId}}'

          -- 쿠폰
          AND c.coupon_no = '{{couponNumber}}'

          -- 광고코드
          AND om.ad_cd = '{{adCode}}'

          -- 전문관코드
        --  AND sg.specialty_cd = '{{specialtyCode}}'

          -- 담당MD
          AND om.md_id = '{{mdId}}'

        GROUP BY 1

        ORDER BY `{{orderBy}}` ASC

        LIMIT {{size}}

        OFFSET {{number}}
    """.trimIndent()

    val SAMPLE_QUERY_SET_ = """
        SELECT
          om.ord_state_date AS `date`
          ,SUM(om.sell_qty) AS `sellQuantity`
          ,SUM(om.sell_amt) AS `sellAmount`
          ,SUM(om.refund_qty) AS `refundQuantity`
          ,SUM(om.refund_amt) AS `refundAmount`
          ,SUM(om.exchange_qty) AS `exchangeQuantity`
          ,SUM(om.exchange_amt) AS `exchangeAmount`
          ,SUM(om.sell_sub_clm_qty) AS `tradeQuantity`
          ,SUM(om.sell_sub_clm_amt) AS `tradeAmount`
          ,SUM(IF(om.dc_type=1, IF(om.gmv_state IN ('1000', '5000'), om.dc_apply_amt, -1*om.dc_apply_amt), 0)) as `memberDiscounts`
          ,SUM(IF(om.dc_type=2, IF(om.gmv_state IN ('1000', '5000'), om.dc_apply_amt, -1*om.dc_apply_amt), 0)) as `affiliateDiscounts`
          ,SUM(IF(om.dc_type NOT IN (1,2), IF(om.gmv_state IN ('1000', '5000'), om.dc_apply_amt, -1*om.dc_apply_amt), 0)) as `otherDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.coupon_apply_amt-om.coupon_partner_apply_amt, -1*om.coupon_apply_amt-om.coupon_partner_apply_amt)) as `couponDiscounts`
          ,SUM(om.coupon_partner_apply_amt) as `partnerCouponDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.point_apply_amt-om.pre_point_amt, -1*(om.point_apply_amt-om.pre_point_amt))) as `pointDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.pre_point_amt, -1*pre_point_amt)) as `prePointDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.cart_dc_amt, -1*cart_dc_amt)) as `cartDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.group_dc_amt, -1*group_dc_amt)) as `groupDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.total_dc_amt, -1*total_dc_amt)) as `totalDiscounts`
          ,ROUND(SUM(IF(om.gmv_state IN ('1000', '5000'), om.total_dc_amt, -1*total_dc_amt))/SUM(om.price*om.qty)*100, 2) `discountRate`
          ,SUM(om.pay_fee) as `paymentFees`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.recv_amt, -1*om.recv_amt)) as `paymentAmount`
          ,SUM(om.revenue) as `sales`
          ,SUM(om.head_wonga) as `originalPrice`
          ,SUM(om.profit) as `profit`
          ,ROUND(IFNULL(SUM(om.profit)/SUM(om.price*om.qty)*100, 0), 2) as `profitMargin`
          ,SUM(om.head_amt) as `purchasesTradeAmount`
          ,SUM(IF(om.ord_com_type=2, 0, (om.point_apply_amt+om.dc_apply_amt+om.coupon_apply_amt+om.cart_dc_amt+om.group_dc_amt)*IF(om.gmv_state IN ('1000', '5000'),1,-1))) as `purchasesDiscounts`
          ,SUM(om.head_pay_fee) as `purchasesPaymentFees`
          ,SUM(IF(om.ord_com_type=2, 0, om.recv_amt*IF(om.gmv_state IN ('1000', '5000'), 1, -1))) as `purchasesPaymentAmount`
          ,SUM(om.head_wonga) as `purchasesOriginalPrice`
          ,SUM(om.head_profit) as `purchasesProfit`
          ,ROUND(IFNULL(SUM(om.head_profit)/SUM(om.head_amt)*100, 0), 2) as `purchasesProfitMargin`
          ,SUM(om.head_amt)/SUM(om.price*om.qty) * 100 as `purchasesRatio`
          ,SUM(om.partner_amt) as `partnerTradeAmount`
          ,SUM(IF(om.ord_com_type=2, (om.point_apply_amt+om.dc_apply_amt+om.coupon_apply_amt+om.cart_dc_amt+om.group_dc_amt)*IF(om.gmv_state IN ('1000', '5000'),1,-1), 0)) as `partnerDiscounts`
          ,SUM(om.partner_pay_fee) as `partnerPaymentFees`
          ,SUM(IF(om.ord_com_type=2, om.recv_amt*IF(om.gmv_state IN ('1000', '5000'), 1, -1), 0)) as `partnerPaymentAmount`
          ,SUM(om.partner_sale_fee) as `partnerSellFees`
          ,SUM(om.partner_fee) as `partnerFees`
          ,SUM(om.partner_support_amt) as `partnerSellGrants`
          ,ROUND(IFNULL(SUM(om.partner_profit)/SUM(om.partner_amt) * 100, 0), 2) as `partnerProfitMargin`
          ,CEIL(SUM(om.not_included_vat_revenue)) as `salesExcludedVAT`
          ,CEIL(SUM(om.not_included_vat_wonga)) as `originalPriceExcludedVAT`
          ,CEIL(SUM(om.not_included_vat_profit)) as `profitExcludedVAT`
          ,ROUND(CEIL(SUM((om.not_included_vat_revenue)-(om.head_wonga/11*10)))/CEIL(SUM(om.price*om.qty))*100, 2) as `profitMarginExcludedVAT`

        FROM datamart.datamart.orders_merged om
          JOIN datamart.datamart.goods_tags as gt
            ON om.goods_no = gt.goods_no
          JOIN datamart.datamart.coupon as c
            ON om.coupon_no = c.coupon_no
          JOIN datamart.datamart.specialty_goods as sg
            ON om.goods_no = sg.goods_no

        WHERE 
          -- 일자
          om.ord_state_date >= '{{startDate}}'
          AND om.ord_state_date <= '{{endDate}}'

          -- 태그(String List)
          AND gt.tag IN ({{tag}})

          -- 매출시점
          AND if('{{salesStart}}'='SHIPPING_REQUEST', om.state_order, om.state_order_done) = True

          -- 업체
          AND om.com_id = '{{partnerId}}'

          -- 카테고리
          AND om.small_nm = '{{category}}'

          -- 스타일넘버
          AND om.style_no = '{{styleNumber}}'

          -- 상품코드
          AND om.goods_no = '{{goodsNumber}}'

          -- 브랜드
          AND om.brand = '{{brandId}}'

          -- 쿠폰
          AND c.coupon_no = '{{couponNumber}}'

          -- 광고코드
          AND om.ad_cd = '{{adCode}}'

          -- 전문관코드
          AND sg.specialty_cd = '{{specialtyCode}}'

          -- 담당MD
          AND om.md_id = '{{mdId}}'

        GROUP BY 1

        ORDER BY `{{orderBy}}` ASC

        LIMIT {{size}}

        OFFSET {{number}}
    """.trimIndent()

    val SAMPLE_QUERY_EMPTY_ = """
        SELECT
          om.ord_state_date AS `date`
          ,SUM(om.sell_qty) AS `sellQuantity`
          ,SUM(om.sell_amt) AS `sellAmount`
          ,SUM(om.refund_qty) AS `refundQuantity`
          ,SUM(om.refund_amt) AS `refundAmount`
          ,SUM(om.exchange_qty) AS `exchangeQuantity`
          ,SUM(om.exchange_amt) AS `exchangeAmount`
          ,SUM(om.sell_sub_clm_qty) AS `tradeQuantity`
          ,SUM(om.sell_sub_clm_amt) AS `tradeAmount`
          ,SUM(IF(om.dc_type=1, IF(om.gmv_state IN ('1000', '5000'), om.dc_apply_amt, -1*om.dc_apply_amt), 0)) as `memberDiscounts`
          ,SUM(IF(om.dc_type=2, IF(om.gmv_state IN ('1000', '5000'), om.dc_apply_amt, -1*om.dc_apply_amt), 0)) as `affiliateDiscounts`
          ,SUM(IF(om.dc_type NOT IN (1,2), IF(om.gmv_state IN ('1000', '5000'), om.dc_apply_amt, -1*om.dc_apply_amt), 0)) as `otherDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.coupon_apply_amt-om.coupon_partner_apply_amt, -1*om.coupon_apply_amt-om.coupon_partner_apply_amt)) as `couponDiscounts`
          ,SUM(om.coupon_partner_apply_amt) as `partnerCouponDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.point_apply_amt-om.pre_point_amt, -1*(om.point_apply_amt-om.pre_point_amt))) as `pointDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.pre_point_amt, -1*pre_point_amt)) as `prePointDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.cart_dc_amt, -1*cart_dc_amt)) as `cartDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.group_dc_amt, -1*group_dc_amt)) as `groupDiscounts`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.total_dc_amt, -1*total_dc_amt)) as `totalDiscounts`
          ,ROUND(SUM(IF(om.gmv_state IN ('1000', '5000'), om.total_dc_amt, -1*total_dc_amt))/SUM(om.price*om.qty)*100, 2) `discountRate`
          ,SUM(om.pay_fee) as `paymentFees`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.recv_amt, -1*om.recv_amt)) as `paymentAmount`
          ,SUM(om.revenue) as `sales`
          ,SUM(om.head_wonga) as `originalPrice`
          ,SUM(om.profit) as `profit`
          ,ROUND(IFNULL(SUM(om.profit)/SUM(om.price*om.qty)*100, 0), 2) as `profitMargin`
          ,SUM(om.head_amt) as `purchasesTradeAmount`
          ,SUM(IF(om.ord_com_type=2, 0, (om.point_apply_amt+om.dc_apply_amt+om.coupon_apply_amt+om.cart_dc_amt+om.group_dc_amt)*IF(om.gmv_state IN ('1000', '5000'),1,-1))) as `purchasesDiscounts`
          ,SUM(om.head_pay_fee) as `purchasesPaymentFees`
          ,SUM(IF(om.ord_com_type=2, 0, om.recv_amt*IF(om.gmv_state IN ('1000', '5000'), 1, -1))) as `purchasesPaymentAmount`
          ,SUM(om.head_wonga) as `purchasesOriginalPrice`
          ,SUM(om.head_profit) as `purchasesProfit`
          ,ROUND(IFNULL(SUM(om.head_profit)/SUM(om.head_amt)*100, 0), 2) as `purchasesProfitMargin`
          ,SUM(om.head_amt)/SUM(om.price*om.qty) * 100 as `purchasesRatio`
          ,SUM(om.partner_amt) as `partnerTradeAmount`
          ,SUM(IF(om.ord_com_type=2, (om.point_apply_amt+om.dc_apply_amt+om.coupon_apply_amt+om.cart_dc_amt+om.group_dc_amt)*IF(om.gmv_state IN ('1000', '5000'),1,-1), 0)) as `partnerDiscounts`
          ,SUM(om.partner_pay_fee) as `partnerPaymentFees`
          ,SUM(IF(om.ord_com_type=2, om.recv_amt*IF(om.gmv_state IN ('1000', '5000'), 1, -1), 0)) as `partnerPaymentAmount`
          ,SUM(om.partner_sale_fee) as `partnerSellFees`
          ,SUM(om.partner_fee) as `partnerFees`
          ,SUM(om.partner_support_amt) as `partnerSellGrants`
          ,ROUND(IFNULL(SUM(om.partner_profit)/SUM(om.partner_amt) * 100, 0), 2) as `partnerProfitMargin`
          ,CEIL(SUM(om.not_included_vat_revenue)) as `salesExcludedVAT`
          ,CEIL(SUM(om.not_included_vat_wonga)) as `originalPriceExcludedVAT`
          ,CEIL(SUM(om.not_included_vat_profit)) as `profitExcludedVAT`
          ,ROUND(CEIL(SUM((om.not_included_vat_revenue)-(om.head_wonga/11*10)))/CEIL(SUM(om.price*om.qty))*100, 2) as `profitMarginExcludedVAT`

        FROM datamart.datamart.orders_merged om
          JOIN datamart.datamart.goods_tags as gt
            ON om.goods_no = gt.goods_no
          JOIN datamart.datamart.coupon as c
            ON om.coupon_no = c.coupon_no
          JOIN datamart.datamart.specialty_goods as sg
            ON om.goods_no = sg.goods_no

        WHERE 
          -- 일자
          om.ord_state_date >= '{{startDate}}'
          AND om.ord_state_date <= '{{endDate}}'

          -- 태그(String List)
          AND gt.tag IN ({{tag}})

          -- 매출시점
          AND if('{{salesStart}}'='SHIPPING_REQUEST', om.state_order, om.state_order_done) = True

          -- 업체
          AND om.com_id = '{{partnerId}}'

          -- 카테고리
          AND om.small_nm = '{{category}}'

          -- 스타일넘버
          AND om.style_no = '{{styleNumber}}'

          -- 상품코드
          AND om.goods_no = '{{goodsNumber}}'

          -- 브랜드
          AND om.brand = '{{brandId}}'

          -- 쿠폰
          AND c.coupon_no = '{{couponNumber}}'

          -- 광고코드
          AND om.ad_cd = '{{adCode}}'

          -- 전문관코드
          AND sg.specialty_cd = '{{specialtyCode}}'

          -- 담당MD
          AND om.md_id = '{{mdId}}'

        GROUP BY 1

        ORDER BY `{{orderBy}}` ASC

        LIMIT {{size}}

        OFFSET {{number}}
    """.trimIndent()
}