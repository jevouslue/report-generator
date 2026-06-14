package org.example

import org.example.services.DB
import org.example.services.ExcelTemplate

fun main() {
    // 2. 既存のひどい設計の生SQLをそのまま定義 (トリプルクォートで複数行も楽々)
    val sql = """
        SELECT  
            e.id,
            e.name,
            d.name AS department_name 
        FROM employee e
        LEFT JOIN department d ON e.department_id = d.id
    """

    // 3. SQL実行。結果は List<Map<String, Any>> で取得（Jxlsにそのまま渡せる）
    val list = DB().queryForList(sql)
    println("result: $list")

    // 4. Jxlsでエクセル出力
    ExcelTemplate("template.xlsx")
        .build(
            mutableMapOf<String, Any>("employees" to list),
            "report.xlsx"
        )

    println("エクセル出力が完了しました。")
}