package com.musinsa.stat.sales.fixture

import com.musinsa.stat.sales.dto.Category
import java.io.FileInputStream
import java.sql.Types
import javax.sql.rowset.RowSetMetaDataImpl
import javax.sql.rowset.RowSetProvider

object CategoryFixture {
    private fun createCategoryRowSetMetaDataImpl(): RowSetMetaDataImpl {
        val meta = RowSetMetaDataImpl()
        meta.columnCount = 52
        meta.setColumnName(1, "대분류")
        meta.setColumnType(1, Types.VARCHAR)

        meta.setColumnName(2, "중분류")
        meta.setColumnType(2, Types.VARCHAR)

        meta.setColumnName(3, "소분류")
        meta.setColumnType(3, Types.VARCHAR)

        meta.setColumnName(4, "대분류_카테고리명")
        meta.setColumnType(4, Types.VARCHAR)

        meta.setColumnName(5, "중분류_카테고리명")
        meta.setColumnType(5, Types.VARCHAR)

        meta.setColumnName(6, "소분류_카테고리명")
        meta.setColumnType(6, Types.VARCHAR)

        meta.setColumnName(7, "판매수량")
        meta.setColumnType(7, Types.BIGINT)

        meta.setColumnName(8, "판매금액")
        meta.setColumnType(8, Types.BIGINT)

        meta.setColumnName(9, "환불수량")
        meta.setColumnType(9, Types.BIGINT)

        meta.setColumnName(10, "환불금액")
        meta.setColumnType(10, Types.BIGINT)

        meta.setColumnName(11, "교환수량")
        meta.setColumnType(11, Types.BIGINT)

        meta.setColumnName(12, "교환금액")
        meta.setColumnType(12, Types.BIGINT)

        meta.setColumnName(13, "거래수량")
        meta.setColumnType(13, Types.BIGINT)

        meta.setColumnName(14, "거래금액")
        meta.setColumnType(14, Types.BIGINT)

        meta.setColumnName(15, "회원할인")
        meta.setColumnType(15, Types.BIGINT)

        meta.setColumnName(16, "제휴할인")
        meta.setColumnType(16, Types.BIGINT)

        meta.setColumnName(17, "기타할인")
        meta.setColumnType(17, Types.BIGINT)

        meta.setColumnName(18, "쿠폰(무신사)할인")
        meta.setColumnType(18, Types.BIGINT)

        meta.setColumnName(19, "쿠폰(업체)할인")
        meta.setColumnType(19, Types.BIGINT)

        meta.setColumnName(20, "적립금할인")
        meta.setColumnType(20, Types.BIGINT)

        meta.setColumnName(21, "선적립금할인")
        meta.setColumnType(21, Types.BIGINT)

        meta.setColumnName(22, "장바구니할인")
        meta.setColumnType(22, Types.BIGINT)

        meta.setColumnName(23, "그룹할인")
        meta.setColumnType(23, Types.BIGINT)

        meta.setColumnName(24, "소계")
        meta.setColumnType(24, Types.BIGINT)

        meta.setColumnName(25, "할인율")
        meta.setColumnType(25, Types.DOUBLE)

        meta.setColumnName(26, "결제수수료")
        meta.setColumnType(26, Types.BIGINT)

        meta.setColumnName(27, "결제금액")
        meta.setColumnType(27, Types.BIGINT)

        meta.setColumnName(28, "매출")
        meta.setColumnType(28, Types.BIGINT)

        meta.setColumnName(29, "원가")
        meta.setColumnType(29, Types.BIGINT)

        meta.setColumnName(30, "이익")
        meta.setColumnType(30, Types.BIGINT)

        meta.setColumnName(31, "이익률")
        meta.setColumnType(31, Types.DOUBLE)

        meta.setColumnName(32, "매입상품_거래금액")
        meta.setColumnType(32, Types.BIGINT)

        meta.setColumnName(33, "매입상품_할인")
        meta.setColumnType(33, Types.BIGINT)

        meta.setColumnName(34, "매입상품_결제수수료")
        meta.setColumnType(34, Types.BIGINT)

        meta.setColumnName(35, "매입상품_결제금액")
        meta.setColumnType(35, Types.BIGINT)

        meta.setColumnName(36, "매입상품_원가")
        meta.setColumnType(36, Types.BIGINT)

        meta.setColumnName(37, "매입상품_이익")
        meta.setColumnType(37, Types.BIGINT)

        meta.setColumnName(38, "매입상품_이익율")
        meta.setColumnType(38, Types.DOUBLE)

        meta.setColumnName(39, "매입상품_비중")
        meta.setColumnType(39, Types.DOUBLE)

        meta.setColumnName(40, "입점상품_거래금액")
        meta.setColumnType(40, Types.BIGINT)

        meta.setColumnName(41, "입점상품_할인")
        meta.setColumnType(41, Types.BIGINT)

        meta.setColumnName(42, "입점상품_결제수수료")
        meta.setColumnType(42, Types.BIGINT)

        meta.setColumnName(43, "입점상품_결제금액")
        meta.setColumnType(43, Types.BIGINT)

        meta.setColumnName(44, "입점상품_판매수수료")
        meta.setColumnType(44, Types.BIGINT)

        meta.setColumnName(45, "입점상품_수수료")
        meta.setColumnType(45, Types.BIGINT)

        meta.setColumnName(46, "입점상품_판매지원금")
        meta.setColumnType(46, Types.BIGINT)

        meta.setColumnName(47, "입점상품_이익률")
        meta.setColumnType(47, Types.DOUBLE)

        meta.setColumnName(48, "매출(VAT별도)")
        meta.setColumnType(48, Types.BIGINT)

        meta.setColumnName(49, "원가(VAT별도)")
        meta.setColumnType(49, Types.BIGINT)

        meta.setColumnName(50, "이익(VAT별도)")
        meta.setColumnType(50, Types.BIGINT)

        meta.setColumnName(51, "이익률(VAT별도)")
        meta.setColumnType(51, Types.DOUBLE)

        meta.setColumnName(52, "total")
        meta.setColumnType(52, Types.BIGINT)

        return meta
    }

    fun 쿼리_결과_카테고리_리스트(): List<Category> {
        val categories = mutableListOf<Category>()

        val reader =
            FileInputStream("src/test/resources/sales/category-query-result.csv").bufferedReader()

        // 첫째줄 제거
        val header = reader.readLine()

        reader.forEachLine {
            val data = it.split(
                ',',
                limit = 52
            )

            val rowSetTemp = RowSetProvider.newFactory().createCachedRowSet()
            rowSetTemp.setMetaData(createCategoryRowSetMetaDataImpl())
            rowSetTemp.moveToInsertRow()

            rowSetTemp.updateString("대분류", data[0])
            rowSetTemp.updateString("중분류", data[1])
            rowSetTemp.updateString("소분류", data[2])
            rowSetTemp.updateString("대분류_카테고리명", data[3])
            rowSetTemp.updateString("중분류_카테고리명", data[4])
            rowSetTemp.updateString("소분류_카테고리명", data[5])
            rowSetTemp.updateLong("판매수량", data[6].toLong())
            rowSetTemp.updateLong("판매금액", data[7].toLong())
            rowSetTemp.updateLong("환불수량", data[8].toLong())
            rowSetTemp.updateLong("환불금액", data[9].toLong())
            rowSetTemp.updateLong("교환수량", data[10].toLong())
            rowSetTemp.updateLong("교환금액", data[11].toLong())
            rowSetTemp.updateLong("거래수량", data[12].toLong())
            rowSetTemp.updateLong("거래금액", data[13].toLong())
            rowSetTemp.updateLong("회원할인", data[14].toLong())
            rowSetTemp.updateLong("제휴할인", data[15].toLong())
            rowSetTemp.updateLong("기타할인", data[16].toLong())
            rowSetTemp.updateLong("쿠폰(무신사)할인", data[17].toLong())
            rowSetTemp.updateLong("쿠폰(업체)할인", data[18].toLong())
            rowSetTemp.updateLong("적립금할인", data[19].toLong())
            rowSetTemp.updateLong("선적립금할인", data[20].toLong())
            rowSetTemp.updateLong("장바구니할인", data[21].toLong())
            rowSetTemp.updateLong("그룹할인", data[22].toLong())
            rowSetTemp.updateLong("소계", data[23].toLong())
            rowSetTemp.updateDouble("할인율", data[24].toDouble())
            rowSetTemp.updateLong("결제수수료", data[25].toLong())
            rowSetTemp.updateLong("결제금액", data[26].toLong())
            rowSetTemp.updateLong("매출", data[27].toLong())
            rowSetTemp.updateLong("원가", data[28].toLong())
            rowSetTemp.updateLong("이익", data[29].toLong())
            rowSetTemp.updateDouble("이익률", data[30].toDouble())
            rowSetTemp.updateLong("매입상품_거래금액", data[31].toLong())
            rowSetTemp.updateLong("매입상품_할인", data[32].toLong())
            rowSetTemp.updateLong("매입상품_결제수수료", data[33].toLong())
            rowSetTemp.updateLong("매입상품_결제금액", data[34].toLong())
            rowSetTemp.updateLong("매입상품_원가", data[35].toLong())
            rowSetTemp.updateLong("매입상품_이익", data[36].toLong())
            rowSetTemp.updateDouble("매입상품_이익율", data[37].toDouble())
            rowSetTemp.updateDouble("매입상품_비중", data[38].toDouble())
            rowSetTemp.updateLong("입점상품_거래금액", data[39].toLong())
            rowSetTemp.updateLong("입점상품_할인", data[40].toLong())
            rowSetTemp.updateLong("입점상품_결제수수료", data[41].toLong())
            rowSetTemp.updateLong("입점상품_결제금액", data[42].toLong())
            rowSetTemp.updateLong("입점상품_판매수수료", data[43].toLong())
            rowSetTemp.updateLong("입점상품_수수료", data[44].toLong())
            rowSetTemp.updateLong("입점상품_판매지원금", data[45].toLong())
            rowSetTemp.updateDouble("입점상품_이익률", data[46].toDouble())
            rowSetTemp.updateLong("매출(VAT별도)", data[47].toLong())
            rowSetTemp.updateLong("원가(VAT별도)", data[48].toLong())
            rowSetTemp.updateLong("이익(VAT별도)", data[49].toLong())
            rowSetTemp.updateDouble("이익률(VAT별도)", data[50].toDouble())
            rowSetTemp.updateLong("total", data[51].toLong())

            categories.add(
                Category(
                    rowSetTemp,
                    data[0],
                    data[1],
                    data[2],
                    data[3],
                    data[4],
                    data[5]
                )
            )
        }
        return categories
    }
}
