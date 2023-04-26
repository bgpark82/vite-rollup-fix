package com.musinsa.stat.databricks

import org.junit.jupiter.api.Test

internal class DatabricksResolverTest {
    private val databricksResolver = DatabricksResolver()


    @Test
    fun `데이터브릭스 연결 성공`() {
        // given
//        val ord_state_date = Field("ord_state_date", FieldType.nullable(
//            ArrowType.Utf8()
//        ), null)

        // when
        val conn = databricksResolver.getSQLWarehouseConnection()
        println(conn.schema)
        println(conn.catalog)
        val testQuery = "SELECT ord_state_date FROM datamart.datamart.orders LIMIT 10"
//        val testQuery = "SELECT 1 FROM dual"
        val stmt = conn.createStatement()
        val rs = stmt.executeQuery(testQuery)

//        val allocator = RootAllocator(Long.MAX_VALUE)
//        val iterator = JdbcToArrow.sqlToArrowVectorIterator(rs, allocator)
//        while (iterator.hasNext()) {
//            val root: VectorSchemaRoot = iterator.next()
//            println(root)
//        }

        while(rs.next()) {
            println(rs.toString())
        }


        conn.close()
        // then

    }

}