package com.musinsa.stat.sales.domain

// TODO 쿼리 효율적으로 관리
class Query {
    /**
     * 출고시점의 일별 매출통계 쿼리
     */
    val daily: String = "" +
            "SELECT  om.ord_state_date AS `date`\n" +
            "  ,SUM(om.sell_qty) AS `sellQuantity`\n" +
            "  ,SUM(om.sell_amt) AS `sellAmount`\n" +
            "  ,SUM(om.refund_qty) AS `refundQuantity`\n" +
            "  ,SUM(om.refund_amt) AS `refundAmount`\n" +
            "  ,SUM(om.exchange_qty) AS `exchangeQuantity`\n" +
            "  ,SUM(om.exchange_amt) AS `exchangeAmount`\n" +
            "  ,SUM(om.sell_sub_clm_qty) AS `tradeQuantity`\n" +
            "  ,SUM(om.sell_sub_clm_amt) AS `tradeAmount`\n" +
            "  ,SUM(IF(om.dc_type=1, IF(om.gmv_state='1000', om.dc_apply_amt, -1*om.dc_apply_amt), 0)) as `memberDiscounts`\n" +
            "  ,SUM(IF(om.dc_type=2, IF(om.gmv_state='1000', om.dc_apply_amt, -1*om.dc_apply_amt), 0)) as `affiliateDiscounts`\n" +
            "  ,SUM(IF(om.dc_type NOT IN (1,2), IF(om.gmv_state='1000', om.dc_apply_amt, -1*om.dc_apply_amt), 0)) as `otherDiscounts`\n" +
            "  ,SUM(IF(om.gmv_state='1000', om.coupon_apply_amt-om.coupon_partner_apply_amt, -1*om.coupon_apply_amt-om.coupon_partner_apply_amt)) as `couponDiscounts`\n" +
            "  ,SUM(om.coupon_partner_apply_amt) as `partnerCouponDiscounts`\n" +
            "  ,SUM(IF(om.gmv_state='1000', om.point_apply_amt-om.pre_point_amt, -1*(om.point_apply_amt-om.pre_point_amt))) as `pointDiscounts`\n" +
            "  ,SUM(IF(om.gmv_state='1000', om.pre_point_amt, -1*pre_point_amt)) as `prePointDiscounts`\n" +
            "  ,SUM(IF(om.gmv_state='1000', om.cart_dc_amt, -1*cart_dc_amt)) as `cartDiscounts`\n" +
            "  ,SUM(IF(om.gmv_state='1000', om.group_dc_amt, -1*group_dc_amt)) as `groupDiscounts`\n" +
            "  ,SUM(IF(om.gmv_state='1000', om.total_dc_amt, -1*total_dc_amt)) as `totalDiscounts`\n" +
            "  ,ROUND(SUM(IF(om.gmv_state='1000', om.total_dc_amt, -1*total_dc_amt))/SUM(om.price*om.qty)*100, 2) `discountRate`\n" +
            "  ,SUM(om.pay_fee) as `paymentFees`\n" +
            "  ,SUM(IF(om.gmv_state='1000', om.recv_amt, -1*om.recv_amt)) as `paymentAmount`\n" +
            "  ,SUM(om.revenue) as `sales`\n" +
            "  ,SUM(om.head_wonga) as `originalPrice`\n" +
            "  ,SUM(om.profit) as `profit`\n" +
            "  ,ROUND(IFNULL(SUM(om.profit)/SUM(om.price*om.qty)*100, 0), 2) as `profitMargin`\n" +
            "  ,SUM(om.head_amt) as `purchasesTradeAmount`\n" +
            "  ,SUM(IF(om.ord_com_type=2, 0, (om.point_apply_amt+om.dc_apply_amt+om.coupon_apply_amt+om.cart_dc_amt+om.group_dc_amt)*IF(om.gmv_state='1000',1,-1))) as `purchasesDiscounts`\n" +
            "  ,SUM(om.head_pay_fee) as `purchasesPaymentFees`\n" +
            "  ,SUM(IF(om.ord_com_type=2, 0, om.recv_amt*IF(om.gmv_state='1000', 1, -1))) as `purchasesPaymentAmount`\n" +
            "  ,SUM(om.head_wonga) as `purchasesOriginalPrice`\n" +
            "  ,SUM(om.head_profit) as `purchasesProfit`\n" +
            "  ,ROUND(IFNULL(SUM(om.head_profit)/SUM(om.head_amt)*100, 0), 2) as `purchasesProfitMargin`\n" +
            "  ,SUM(om.head_amt)/SUM(om.price*om.qty) * 100 as `purchasesRatio`\n" +
            "  ,SUM(om.partner_amt) as `partnerTradeAmount`\n" +
            "  ,SUM(IF(om.ord_com_type=2, (om.point_apply_amt+om.dc_apply_amt+om.coupon_apply_amt+om.cart_dc_amt+om.group_dc_amt)*IF(om.gmv_state='1000',1,-1), 0)) as `partnerDiscounts`\n" +
            "  ,SUM(om.partner_pay_fee) as `partnerPaymentFees`\n" +
            "  ,SUM(IF(om.ord_com_type=2, om.recv_amt*IF(om.gmv_state='1000', 1, -1), 0)) as `partnerPaymentAmount`\n" +
            "  ,SUM(om.partner_sale_fee) as `partnerSellFees`\n" +
            "  ,SUM(om.partner_fee) as `partnerFees`\n" +
            "  ,SUM(om.partner_support_amt) as `partnerSellGrants`\n" +
            "  ,ROUND(IFNULL(SUM(om.partner_profit)/SUM(om.partner_amt) * 100, 0), 2) as `partnerProfitMargin`\n" +
            "  ,CEIL(SUM(om.not_included_vat_revenue)) as `revenueExcludedVAT`\n" +
            "  ,CEIL(SUM(om.not_included_vat_wonga)) as `originalPriceExcludedVAT`\n" +
            "  ,CEIL(SUM(om.not_included_vat_profit)) as `profitExcludedVAT`\n" +
            "  ,ROUND(CEIL(SUM((om.not_included_vat_revenue)-(om.head_wonga/11*10)))/CEIL(SUM(om.price*om.qty))*100, 2) as `profitMarginExcludedVAT`\n" +
            "\n" +
            "FROM datamart.datamart.orders_merged om\n" +
            "  JOIN datamart.datamart.goods_tags as gt\n" +
            "    ON om.goods_no = gt.goods_no\n" +
            "  JOIN datamart.datamart.coupon as c\n" +
            "    ON om.coupon_no = c.coupon_no\n" +
            "  JOIN datamart.datamart.specialty_goods as sg\n" +
            "    ON om.goods_no = sg.goods_no\n" +
            "\n" +
            "WHERE \n" +
            "  -- 일자\n" +
            "  om.ord_state_date >= '20230410'\n" +
            "  AND om.ord_state_date <= '20230420'" +
            "  AND om.state_order = True\n" +
            "GROUP BY 1\n" +
            "\n" +
            "ORDER BY `sellQuantity` ASC\n" +
            "\n" +
            "LIMIT 20\n" +
            "\n" +
            "OFFSET 0"
}