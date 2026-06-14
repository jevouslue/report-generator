package org.example

import org.jxls.builder.JxlsOutputFile
import org.jxls.transform.poi.JxlsPoiTemplateFillerBuilder
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.datasource.DriverManagerDataSource
import java.io.File

fun main() {
    // 1. DB接続設定 (生Java/Kotlinで定義)
    val dataSource = DriverManagerDataSource().apply {
        setDriverClassName("oracle.jdbc.OracleDriver")
        url = System.getenv("DB_URL") ?: throw IllegalStateException("環境変数 DB_URL が設定されていません。")
        username = System.getenv("DB_USER") ?: throw IllegalStateException("環境変数 DB_USER が設定されていません。")
        password = System.getenv("DB_PASSWORD") ?: throw IllegalStateException("環境変数 DB_PASSWORD が設定されていません。")
    }

    val jdbcTemplate = NamedParameterJdbcTemplate(dataSource)

    // 2. 既存のひどい設計の生SQLをそのまま定義 (トリプルクォートで複数行も楽々)
    val sql = """
        SELECT  
            e.id,
            e.name,
            d.name AS department_name 
        FROM employee e
        LEFT JOIN department d ON e.department_id = d.id
    """.trimIndent()

//    val params = mapOf("category" to "SALES_REPORT")
    // 3. SQL実行。結果は List<Map<String, Any>> で取得（Jxlsにそのまま渡せる）
//    val list = jdbcTemplate.queryForList(sql, params)
    val list = jdbcTemplate.jdbcOperations.queryForList(sql)
    println("result: $list")

//    val templateStream = object {}.javaClass.classLoader
//        .getResourceAsStream("xlsx_templates/template.xlsx")
//        ?: throw IllegalArgumentException("テンプレートファイルが見つかりません。")

    // 4. Jxlsでエクセル出力
    JxlsPoiTemplateFillerBuilder.newInstance()
        .withTemplate("resources/xlsx_templates/template.xlsx")
        .buildAndFill(
            mutableMapOf<String, Any>("employees" to list),
            JxlsOutputFile(File("storage/artifacts/report.xlsx"))
        )

    println("エクセル出力が完了しました。")
}