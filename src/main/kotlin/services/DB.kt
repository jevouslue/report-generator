package org.example.services

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.datasource.DriverManagerDataSource

class DB {
    private val dataSource: DriverManagerDataSource = DriverManagerDataSource().apply {
        setDriverClassName("oracle.jdbc.OracleDriver")
        url = System.getenv("DB_URL") ?: throw IllegalStateException("環境変数 DB_URL が設定されていません。")
        username = System.getenv("DB_USER") ?: throw IllegalStateException("環境変数 DB_USER が設定されていません。")
        password = System.getenv("DB_PASSWORD") ?: throw IllegalStateException("環境変数 DB_PASSWORD が設定されていません。")
    }
    private val jdbcTemplate = NamedParameterJdbcTemplate(dataSource)

    @Suppress("SqlSourceToSinkFlow")
    fun queryForList(sql: String, params: MutableMap<String, Any?> = mutableMapOf()): List<Map<String, Any?>> {
        return jdbcTemplate.queryForList(sql, params)
    }
}