package org.example

import org.komapper.core.dsl.QueryDsl
import org.komapper.core.dsl.query.bind
import org.komapper.jdbc.JdbcDatabase
import kotlin.reflect.typeOf

fun main() {
    // (1) create a database instance
    val database = JdbcDatabase(
        "jdbc:oracle:thin:@//localhost:1521/FREEPDB1",
        user = "SYSTEM",
        password = "password"
    )

    // (2) start transaction
    database.withTransaction {
//        val searchName = "田中%"
        val searchName = null

        val sql = """
            SELECT  
                e.id,
                e.name,
                d.name AS department_name 
            FROM employee e
            LEFT JOIN department d ON e.department_id = d.id
            WHERE 1 = 1
            /*% if name != null */
              AND e.name LIKE /* name */'test'
            /*% end */
            """

        // 生SQLを実行し、結果をDTOで受け取る
        val result: List<Map<String, Any?>> = database.runQuery {
            QueryDsl.fromTemplate(sql)
                .bind("name", searchName)
                .select { row -> // 👈 ここで Row オブジェクトを受け取る
                    // カラム名をキーにしたMapに詰め替える
                    mapOf(
                        "id" to row.get("id", typeOf<Int>()),
                        "name" to row.get("name", typeOf<String>()),
                        "department_name" to row.get("department_name", typeOf<String>()),
                    )
                }
        }
        println(result)
    }
}