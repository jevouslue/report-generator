package org.example

import org.example.dto.EmployeeDetailDto
import org.example.dto.selectAsEmployeeDetailDto
import org.example.entities.department
import org.example.entities.employee
import org.komapper.core.dsl.Meta
import org.komapper.core.dsl.QueryDsl
import org.komapper.jdbc.JdbcDatabase

fun main() {
    // (1) create a database instance
    val database = JdbcDatabase(
        "jdbc:oracle:thin:@//localhost:1521/FREEPDB1",
        user = "SYSTEM",
        password = "password"
    )

    // (2) start transaction
    database.withTransaction {

        // (3) get an entity metamodel
        val e = Meta.employee
        val d = Meta.department

        val result: List<EmployeeDetailDto> = database.runQuery {
            QueryDsl.from(e)
                .leftJoin(d) { e.departmentId eq d.id }
                .selectAsEmployeeDetailDto(
                    id = e.id,
                    name = e.name,
                    departmentName = d.name,
                )
        }
        println(result)
    }
}