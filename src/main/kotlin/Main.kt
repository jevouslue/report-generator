package org.example

import org.example.services.DB

fun main() {
    // 1. 既存のひどい設計の生SQLをそのまま定義 (トリプルクォートで複数行も楽々)
    val sql = """
        SELECT  
            e.id,
            e.name,
            d.name AS department_name 
        FROM employee e
        LEFT JOIN department d ON e.department_id = d.id
    """

    // 2. SQL実行
    // プリペアドステートメントも対応
    val list = DB().first(sql)
    println("result: $list")

    // 3. テンプレートエクセルファイルを元に帳票出力
//    ExcelTemplate("template.xlsx")
//        .build(
//            mutableMapOf("employees" to list),
//            "report.xlsx"
//        )
//
//    println("エクセル出力が完了しました。")
}