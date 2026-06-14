package org.example.services

import org.springframework.jdbc.core.JdbcTemplate
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
    fun first(sql: String, params: MutableMap<String, Any?> = mutableMapOf()): Map<String, Any?>? {
        // 1. 実体である JdbcTemplate にキャストする
        val template = jdbcTemplate.jdbcOperations as? JdbcTemplate

        // 2. キャストできた場合のみ maxRows を制御する（基本は必ずキャストできます）
        val previousMaxRows = template?.maxRows ?: -1

        try {
            template?.maxRows = 1 // 最大件数を 1 に設定
            // 3. クエリを実行
            val list = jdbcTemplate.queryForList(sql, params)
            return list.firstOrNull()
        } finally {
            // 4. 元の設定値に戻す
            template?.maxRows = previousMaxRows
        }
    }

    @Suppress("SqlSourceToSinkFlow")
    fun get(sql: String, params: MutableMap<String, Any?> = mutableMapOf()): List<Map<String, Any?>> {
        return jdbcTemplate.queryForList(sql, params)
    }
}