package com.musinsa.stat.sales.fixture

import com.musinsa.stat.sales.dto.DailyAndMontly
import java.sql.Types
import javax.sql.rowset.RowSetMetaDataImpl
import javax.sql.rowset.RowSetProvider

object DailyFixture {

    /**
     * RowSetMeta 생성
     *
     * @return Daily RowSetMeta
     */
    private fun createDailyRowSetMetaDataImpl(): RowSetMetaDataImpl {
        val meta = RowSetMetaDataImpl()
        meta.columnCount = 47
        meta.setColumnName(1, "date")
        meta.setColumnType(1, Types.VARCHAR)

        meta.setColumnName(2, "집계")
        meta.setColumnType(2, Types.BIGINT)

        meta.setColumnName(3, "판매수량")
        meta.setColumnType(3, Types.BIGINT)

        meta.setColumnName(4, "판매금액")
        meta.setColumnType(4, Types.BIGINT)

        meta.setColumnName(5, "환불수량")
        meta.setColumnType(5, Types.BIGINT)

        meta.setColumnName(6, "환불금액")
        meta.setColumnType(6, Types.BIGINT)

        meta.setColumnName(7, "교환수량")
        meta.setColumnType(7, Types.BIGINT)

        meta.setColumnName(8, "교환금액")
        meta.setColumnType(8, Types.BIGINT)

        meta.setColumnName(9, "거래수량")
        meta.setColumnType(9, Types.BIGINT)

        meta.setColumnName(10, "거래금액")
        meta.setColumnType(10, Types.BIGINT)

        meta.setColumnName(11, "회원할인")
        meta.setColumnType(11, Types.BIGINT)

        meta.setColumnName(12, "제휴할인")
        meta.setColumnType(12, Types.BIGINT)

        meta.setColumnName(13, "기타할인")
        meta.setColumnType(13, Types.BIGINT)

        meta.setColumnName(14, "쿠폰(무신사)할인")
        meta.setColumnType(14, Types.BIGINT)

        meta.setColumnName(15, "쿠폰(업체)할인")
        meta.setColumnType(15, Types.BIGINT)

        meta.setColumnName(16, "적립금할인")
        meta.setColumnType(16, Types.BIGINT)

        meta.setColumnName(17, "선적립금할인")
        meta.setColumnType(17, Types.BIGINT)

        meta.setColumnName(18, "장바구니할인")
        meta.setColumnType(18, Types.BIGINT)

        meta.setColumnName(19, "그룹할인")
        meta.setColumnType(19, Types.BIGINT)

        meta.setColumnName(20, "소계")
        meta.setColumnType(20, Types.BIGINT)

        meta.setColumnName(21, "할인율")
        meta.setColumnType(21, Types.DOUBLE)

        meta.setColumnName(22, "결제수수료")
        meta.setColumnType(22, Types.BIGINT)

        meta.setColumnName(23, "결제금액")
        meta.setColumnType(23, Types.BIGINT)

        meta.setColumnName(24, "매출")
        meta.setColumnType(24, Types.BIGINT)

        meta.setColumnName(25, "원가")
        meta.setColumnType(25, Types.BIGINT)

        meta.setColumnName(26, "이익")
        meta.setColumnType(26, Types.BIGINT)

        meta.setColumnName(27, "이익률")
        meta.setColumnType(27, Types.DOUBLE)

        meta.setColumnName(28, "매입상품_거래금액")
        meta.setColumnType(28, Types.BIGINT)

        meta.setColumnName(29, "매입상품_할인")
        meta.setColumnType(29, Types.BIGINT)

        meta.setColumnName(30, "매입상품_결제수수료")
        meta.setColumnType(30, Types.BIGINT)

        meta.setColumnName(31, "매입상품_결제금액")
        meta.setColumnType(31, Types.BIGINT)

        meta.setColumnName(32, "매입상품_원가")
        meta.setColumnType(32, Types.BIGINT)

        meta.setColumnName(33, "매입상품_이익")
        meta.setColumnType(33, Types.BIGINT)

        meta.setColumnName(34, "매입상품_이익율")
        meta.setColumnType(34, Types.DOUBLE)

        meta.setColumnName(35, "매입상품_비중")
        meta.setColumnType(35, Types.DOUBLE)

        meta.setColumnName(36, "입점상품_거래금액")
        meta.setColumnType(36, Types.BIGINT)

        meta.setColumnName(37, "입점상품_할인")
        meta.setColumnType(37, Types.BIGINT)

        meta.setColumnName(38, "입점상품_결제수수료")
        meta.setColumnType(38, Types.BIGINT)

        meta.setColumnName(39, "입점상품_결제금액")
        meta.setColumnType(39, Types.BIGINT)

        meta.setColumnName(40, "입점상품_판매수수료")
        meta.setColumnType(40, Types.BIGINT)

        meta.setColumnName(41, "입점상품_수수료")
        meta.setColumnType(41, Types.BIGINT)

        meta.setColumnName(42, "입점상품_판매지원금")
        meta.setColumnType(42, Types.BIGINT)

        meta.setColumnName(43, "입점상품_이익률")
        meta.setColumnType(43, Types.DOUBLE)

        meta.setColumnName(44, "매출(VAT별도)")
        meta.setColumnType(44, Types.BIGINT)

        meta.setColumnName(45, "원가(VAT별도)")
        meta.setColumnType(45, Types.BIGINT)

        meta.setColumnName(46, "이익(VAT별도)")
        meta.setColumnType(46, Types.BIGINT)

        meta.setColumnName(47, "이익률(VAT별도)")
        meta.setColumnType(47, Types.DOUBLE)

        return meta
    }

    fun DAILY_SUM(): DailyAndMontly {
        val rowSetTemp = RowSetProvider.newFactory().createCachedRowSet()
        rowSetTemp.setMetaData(createDailyRowSetMetaDataImpl())
        rowSetTemp.moveToInsertRow()
        rowSetTemp.updateString("date", null)
        rowSetTemp.updateLong("집계", 1)
        rowSetTemp.updateLong("판매수량", 503974)
        rowSetTemp.updateLong("판매금액", 24714780428)
        rowSetTemp.updateLong("환불수량", -36120)
        rowSetTemp.updateLong("환불금액", -2682372924)
        rowSetTemp.updateLong("교환수량", -466)
        rowSetTemp.updateLong("교환금액", -21286300)
        rowSetTemp.updateLong("거래수량", 467388)
        rowSetTemp.updateLong("거래금액", 22011121204)
        rowSetTemp.updateLong("회원할인", 342419799)
        rowSetTemp.updateLong("제휴할인", 0)
        rowSetTemp.updateLong("기타할인", 0)
        rowSetTemp.updateLong("쿠폰(무신사)할인", 2630184773)
        rowSetTemp.updateLong("쿠폰(업체)할인", 80774395)
        rowSetTemp.updateLong("적립금할인", 449794246)
        rowSetTemp.updateLong("선적립금할인", 130526881)
        rowSetTemp.updateLong("장바구니할인", 6938313)
        rowSetTemp.updateLong("그룹할인", 417900)
        rowSetTemp.updateLong("소계", 3641056307)
        rowSetTemp.updateDouble("할인율", 16.54)
        rowSetTemp.updateLong("결제수수료", 8523106)
        rowSetTemp.updateLong("결제금액", 18378588003)
        rowSetTemp.updateLong("매출", 13984504330)
        rowSetTemp.updateLong("원가", 8319034160)
        rowSetTemp.updateLong("이익", 5658685497)
        rowSetTemp.updateDouble("이익률", 25.71)
        rowSetTemp.updateLong("매입상품_거래금액", 15858703557)
        rowSetTemp.updateLong("매입상품_할인", 2636847822)
        rowSetTemp.updateLong("매입상품_결제수수료", 5464155)
        rowSetTemp.updateLong("매입상품_결제금액", 13227319890)
        rowSetTemp.updateLong("매입상품_원가", 8319034160)
        rowSetTemp.updateLong("매입상품_이익", 4908285730)
        rowSetTemp.updateDouble("매입상품_이익율", 30.95)
        rowSetTemp.updateDouble("매입상품_비중", 72.04859493535503)
        rowSetTemp.updateLong("입점상품_거래금액", 6152417647)
        rowSetTemp.updateLong("입점상품_할인", 1004208485)
        rowSetTemp.updateLong("입점상품_결제수수료", 3058951)
        rowSetTemp.updateLong("입점상품_결제금액", 5151268113)
        rowSetTemp.updateLong("입점상품_판매수수료", 1671724054)
        rowSetTemp.updateLong("입점상품_수수료", 754125489)
        rowSetTemp.updateLong("입점상품_판매지원금", 6784673)
        rowSetTemp.updateDouble("입점상품_이익률", 12.2)
        rowSetTemp.updateLong("매출(VAT별도)", 12713185755)
        rowSetTemp.updateLong("원가(VAT별도)", 7562758328)
        rowSetTemp.updateLong("이익(VAT별도)", 5150427428)
        rowSetTemp.updateDouble("이익률(VAT별도)", 23.4)
        rowSetTemp.insertRow()

        return DailyAndMontly(rowSetTemp, "SUM")
    }

    fun DAILY_20230505(): DailyAndMontly {
        val rowSetTemp = RowSetProvider.newFactory().createCachedRowSet()
        rowSetTemp.setMetaData(createDailyRowSetMetaDataImpl())
        rowSetTemp.moveToInsertRow()

        rowSetTemp.updateString("date", "20230505")
        rowSetTemp.updateLong("집계", 0)
        rowSetTemp.updateLong("판매수량", 254948)
        rowSetTemp.updateLong("판매금액", 12487389489)
        rowSetTemp.updateLong("환불수량", -18792)
        rowSetTemp.updateLong("환불금액", -1378885747)
        rowSetTemp.updateLong("교환수량", -466)
        rowSetTemp.updateLong("교환금액", -21286300)
        rowSetTemp.updateLong("거래수량", 235690)
        rowSetTemp.updateLong("거래금액", 11087217442)
        rowSetTemp.updateLong("회원할인", 171421701)
        rowSetTemp.updateLong("제휴할인", 0)
        rowSetTemp.updateLong("기타할인", 0)
        rowSetTemp.updateLong("쿠폰(무신사)할인", 1322699762)
        rowSetTemp.updateLong("쿠폰(업체)할인", 43485972)
        rowSetTemp.updateLong("적립금할인", 228399158)
        rowSetTemp.updateLong("선적립금할인", 65880169)
        rowSetTemp.updateLong("장바구니할인", 1919153)
        rowSetTemp.updateLong("그룹할인", 417900)
        rowSetTemp.updateLong("소계", 1834223815)
        rowSetTemp.updateDouble("할인율", 16.54)
        rowSetTemp.updateLong("결제수수료", 4193738)
        rowSetTemp.updateLong("결제금액", 9257187365)
        rowSetTemp.updateLong("매출", 6952228132)
        rowSetTemp.updateLong("원가", 4180163438)
        rowSetTemp.updateLong("이익", 2769415059)
        rowSetTemp.updateDouble("이익률", 24.98)
        rowSetTemp.updateLong("매입상품_거래금액", 7866007087)
        rowSetTemp.updateLong("매입상품_할인", 1313924029)
        rowSetTemp.updateLong("매입상품_결제수수료", 2781015)
        rowSetTemp.updateLong("매입상품_결제금액", 6554864073)
        rowSetTemp.updateLong("매입상품_원가", 4180163438)
        rowSetTemp.updateLong("매입상품_이익", 2374700635)
        rowSetTemp.updateDouble("매입상품_이익율", 30.19)
        rowSetTemp.updateDouble("매입상품_비중", 70.94662955921127)
        rowSetTemp.updateLong("입점상품_거래금액", 3221210355)
        rowSetTemp.updateLong("입점상품_할인", 520299786)
        rowSetTemp.updateLong("입점상품_결제수수료", 1412723)
        rowSetTemp.updateLong("입점상품_결제금액", 2702323292)
        rowSetTemp.updateLong("입점상품_판매수수료", 870610575)
        rowSetTemp.updateLong("입점상품_수수료", 395951336)
        rowSetTemp.updateLong("입점상품_판매지원금", 2649635)
        rowSetTemp.updateDouble("입점상품_이익률", 12.25)
        rowSetTemp.updateLong("매출(VAT별도)", 6320207393)
        rowSetTemp.updateLong("원가(VAT별도)", 3800148580)
        rowSetTemp.updateLong("이익(VAT별도)", 2520058814)
        rowSetTemp.updateDouble("이익률(VAT별도)", 22.73)

        return DailyAndMontly(rowSetTemp, "20230505")
    }

    fun DAILY_20230506(): DailyAndMontly {
        val rowSetTemp = RowSetProvider.newFactory().createCachedRowSet()
        rowSetTemp.setMetaData(createDailyRowSetMetaDataImpl())
        rowSetTemp.moveToInsertRow()

        rowSetTemp.updateString("date", "20230506")
        rowSetTemp.updateLong("집계", 0)
        rowSetTemp.updateLong("판매수량", 249026)
        rowSetTemp.updateLong("판매금액", 12227390939)
        rowSetTemp.updateLong("환불수량", -17328)
        rowSetTemp.updateLong("환불금액", -1303487177)
        rowSetTemp.updateLong("교환수량", 0)
        rowSetTemp.updateLong("교환금액", 0)
        rowSetTemp.updateLong("거래수량", 231698)
        rowSetTemp.updateLong("거래금액", 10923903762)
        rowSetTemp.updateLong("회원할인", 170998098)
        rowSetTemp.updateLong("제휴할인", 0)
        rowSetTemp.updateLong("기타할인", 0)
        rowSetTemp.updateLong("쿠폰(무신사)할인", 1307485011)
        rowSetTemp.updateLong("쿠폰(업체)할인", 37288423)
        rowSetTemp.updateLong("적립금할인", 221395088)
        rowSetTemp.updateLong("선적립금할인", 64646712)
        rowSetTemp.updateLong("장바구니할인", 5019160)
        rowSetTemp.updateLong("그룹할인", 0)
        rowSetTemp.updateLong("소계", 1806832492)
        rowSetTemp.updateDouble("할인율", 16.54)
        rowSetTemp.updateLong("결제수수료", 4329368)
        rowSetTemp.updateLong("결제금액", 9121400638)
        rowSetTemp.updateLong("매출", 7032276198)
        rowSetTemp.updateLong("원가", 4138870722)
        rowSetTemp.updateLong("이익", 2889270438)
        rowSetTemp.updateDouble("이익률", 26.45)
        rowSetTemp.updateLong("매입상품_거래금액", 7992696470)
        rowSetTemp.updateLong("매입상품_할인", 1322923793)
        rowSetTemp.updateLong("매입상품_결제수수료", 2683140)
        rowSetTemp.updateLong("매입상품_결제금액", 6672455817)
        rowSetTemp.updateLong("매입상품_원가", 4138870722)
        rowSetTemp.updateLong("매입상품_이익", 2533585095)
        rowSetTemp.updateDouble("매입상품_이익율", 31.7)
        rowSetTemp.updateDouble("매입상품_비중", 73.16703482690386)
        rowSetTemp.updateLong("입점상품_거래금액", 2931207292)
        rowSetTemp.updateLong("입점상품_할인", 483908699)
        rowSetTemp.updateLong("입점상품_결제수수료", 1646228)
        rowSetTemp.updateLong("입점상품_결제금액", 2448944821)
        rowSetTemp.updateLong("입점상품_판매수수료", 801113479)
        rowSetTemp.updateLong("입점상품_수수료", 358174153)
        rowSetTemp.updateLong("입점상품_판매지원금", 4135038)
        rowSetTemp.updateDouble("입점상품_이익률", 12.13)
        rowSetTemp.updateLong("매출(VAT별도)", 6392978362)
        rowSetTemp.updateLong("원가(VAT별도)", 3762609748)
        rowSetTemp.updateLong("이익(VAT별도)", 2630368615)
        rowSetTemp.updateDouble("이익률(VAT별도)", 24.08)

        return DailyAndMontly(rowSetTemp, "20230506")

    }

}