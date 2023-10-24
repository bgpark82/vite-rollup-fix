package com.musinsa.stat.sales.fixture

object QueryFixture {
    val SAMPLE_QUERY = """
        SELECT
          om.ord_state_date AS `date`
          ,SUM(om.sell_qty) AS `판매수량`
          ,SUM(om.sell_amt) AS `판매금액`
          ,SUM(om.refund_qty) AS `환불수량`
          ,SUM(om.refund_amt) AS `환불금액`
          ,SUM(om.exchange_qty) AS `교환수량`
          ,SUM(om.exchange_amt) AS `교환금액`
          ,SUM(om.sell_sub_clm_qty) AS `거래수량`
          ,SUM(om.sell_sub_clm_amt) AS `거래금액`
          ,SUM(IF(om.dc_type=1, IF(om.gmv_state IN ('1000', '5000'), om.dc_apply_amt, -1*om.dc_apply_amt), 0)) as `회원할인`
          ,SUM(IF(om.dc_type=2, IF(om.gmv_state IN ('1000', '5000'), om.dc_apply_amt, -1*om.dc_apply_amt), 0)) as `제휴할인`
          ,SUM(IF(om.dc_type NOT IN (1,2), IF(om.gmv_state IN ('1000', '5000'), om.dc_apply_amt, -1*om.dc_apply_amt), 0)) as `기타할인`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.coupon_apply_amt-om.coupon_partner_apply_amt, -1*om.coupon_apply_amt-om.coupon_partner_apply_amt)) as `쿠폰(무신사)할인`
          ,SUM(om.coupon_partner_apply_amt) as `쿠폰(업체)할인`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.point_apply_amt-om.pre_point_amt, -1*(om.point_apply_amt-om.pre_point_amt))) as `적립금할인`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.pre_point_amt, -1*pre_point_amt)) as `선적립금할인`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.cart_dc_amt, -1*cart_dc_amt)) as `장바구니할인`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.group_dc_amt, -1*group_dc_amt)) as `그룹할인`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.total_dc_amt, -1*total_dc_amt)) as `소계`
          ,ROUND(SUM(IF(om.gmv_state IN ('1000', '5000'), om.total_dc_amt, -1*total_dc_amt))/SUM(om.price*om.qty)*100, 2) `할인율`
          ,SUM(om.pay_fee) as `결제수수료`
          ,SUM(IF(om.gmv_state IN ('1000', '5000'), om.recv_amt, -1*om.recv_amt)) as `결제금액`
          ,SUM(om.revenue) as `매출`
          ,SUM(om.head_wonga) as `원가`
          ,SUM(om.profit) as `이익`
          ,ROUND(IFNULL(SUM(om.profit)/SUM(om.price*om.qty)*100, 0), 2) as `이익률`
          ,SUM(om.head_amt) as `매입상품_거래금액`
          ,SUM(IF(om.ord_com_type=2, 0, (om.point_apply_amt+om.dc_apply_amt+om.coupon_apply_amt+om.cart_dc_amt+om.group_dc_amt)*IF(om.gmv_state IN ('1000', '5000'),1,-1))) as `매입상품_할인`
          ,SUM(om.head_pay_fee) as `매입상품_결제수수료`
          ,SUM(IF(om.ord_com_type=2, 0, om.recv_amt*IF(om.gmv_state IN ('1000', '5000'), 1, -1))) as `매입상품_결제금액`
          ,SUM(om.head_wonga) as `매입상품_원가`
          ,SUM(om.head_profit) as `매입상품_이익`
          ,ROUND(IFNULL(SUM(om.head_profit)/SUM(om.head_amt)*100, 0), 2) as `매입상품_이익율`
          ,SUM(om.head_amt)/SUM(om.price*om.qty) * 100 as `매입상품_비중`
          ,SUM(om.partner_amt) as `입점상품_거래금액`
          ,SUM(IF(om.ord_com_type=2, (om.point_apply_amt+om.dc_apply_amt+om.coupon_apply_amt+om.cart_dc_amt+om.group_dc_amt)*IF(om.gmv_state IN ('1000', '5000'),1,-1), 0)) as `입점상품_할인`
          ,SUM(om.partner_pay_fee) as `입점상품_결제수수료`
          ,SUM(IF(om.ord_com_type=2, om.recv_amt*IF(om.gmv_state IN ('1000', '5000'), 1, -1), 0)) as `입점상품_결제금액`
          ,SUM(om.partner_sale_fee) as `입점상품_판매수수료`
          ,SUM(om.partner_fee) as `입점상품_수수료`
          ,SUM(om.partner_support_amt) as `입점상품_판매지원금`
          ,ROUND(IFNULL(SUM(om.partner_profit)/SUM(om.partner_amt) * 100, 0), 2) as `입점상품_이익률`
          ,CEIL(SUM(om.not_included_vat_revenue)) as `매출(VAT별도)`
          ,CEIL(SUM(om.not_included_vat_wonga)) as `원가(VAT별도)`
          ,CEIL(SUM(om.not_included_vat_profit)) as `이익(VAT별도)`
          ,ROUND(CEIL(SUM((om.not_included_vat_revenue)-(om.head_wonga/11*10)))/CEIL(SUM(om.price*om.qty))*100, 2) as `이익률(VAT별도)`
          ,COUNT(*) OVER() AS `total`

        FROM datamart.datamart.orders_merged om
          --{{joinGoodsTags}}JOIN datamart.datamart.goods_tags as gt ON om.goods_no = gt.goods_no
          --{{joinCoupon}}JOIN datamart.datamart.coupon as c ON om.coupon_no = c.coupon_no
          --{{joinSpecialtyGoods}}JOIN datamart.datamart.specialty_goods as sg ON om.goods_no = sg.goods_no

        WHERE
          -- 일자
          om.ord_state_date >= '{{startDate}}'
          AND om.ord_state_date <= '{{endDate}}'

          -- 태그(String List)
          AND gt.tag IN ({{tag}})

          -- 매출시점
          AND if('{{salesStart}}'='SHIPPING_REQUEST', om.state_order, om.state_order_done) = True

          -- 업체
          AND om.com_id IN ({{partnerId}})

          -- 카테고리
          AND om.small_nm IN ({{category}})

          -- 스타일넘버
          AND om.style_no IN ({{styleNumber}})

          -- 상품코드
          AND om.goods_no IN ({{goodsNumber}})

          -- 브랜드
          AND om.brand IN ({{brandId}})

          -- 쿠폰
          AND c.coupon_no IN ({{couponNumber}})

          -- 광고코드
          AND om.ad_cd IN ({{adCode}})

          -- 전문관코드
          AND sg.specialty_cd IN ({{specialtyCode}})

          -- 담당MD
          AND om.md_id IN ({{mdId}})

          -- 업체구분
          AND om.com_type_cd = {{partnerType}}

        GROUP BY 1

        ORDER BY `{{orderBy}}` {{orderDirection}}

        LIMIT {{pageSize}}

        OFFSET {{page}}
    """.trimIndent()
}
