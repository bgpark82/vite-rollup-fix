package com.musinsa.stat.search.domain

import com.musinsa.stat.search.dto.Tag
import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet

object TagRowMapper : RowMapper<Tag> {
    override fun mapRow(rs: ResultSet, rowNum: Int): Tag {
        return Tag(
            tag = rs.getString("태그")
        )
    }
}