package org.example

import org.example.entities.department
import org.example.entities.employee
import org.komapper.core.dsl.Meta
import org.komapper.core.dsl.QueryDsl
import org.komapper.jdbc.JdbcDatabase

fun main() {
    println("Hello, World!")
    // (1) create a database instance
    val database = JdbcDatabase(
        "jdbc:oracle:thin:@//localhost:1521/FREEPDB1",
        user = "SYSTEM",
        password = "password"
    )
//
//    // (2) start transaction
    database.withTransaction {

        // (3) get an entity metamodel
        val e = Meta.employee
        val d = Meta.department

        // (4) create schema
        database.runQuery {
            QueryDsl.create(e)
            QueryDsl.create(d)
        }
//
//        // (5) insert multiple employees at once
//        database.runQuery {
//            QueryDsl.insert(e).single(
//                Employee(name = "AAA", departmentId = 1),
//            )
//            QueryDsl.insert(e).single(
//                Employee(name = "BBB", departmentId = 1),
//            )
//            QueryDsl.insert(d).single(
//                Department(name = "Sales"),
//            )
//            QueryDsl.insert(d).single(
//                Department(name = "HR"),
//            )
//        }
//
//        // (6) select all
//        val employees = database.runQuery {
//            QueryDsl.from(e).orderBy(e.id)
//        }
//
//        // (7) print all results
//        for ((i, employee) in employees.withIndex()) {
//            println("RESULT $i: $employee")
//        }
//
//        // (8) select one employee by name
//        val employee = database.runQuery {
//            QueryDsl.from(e).where { e.name eq "AAA" }.first()
//        }
//
//        // (9) update the employee
//        val updated = database.runQuery {
//            QueryDsl.update(e).single(employee.copy(name = "CCC"))
//        }
//        println("UPDATED: $updated")
//
//        // (10) delete the employee
//        database.runQuery {
//            QueryDsl.delete(e).single(updated)
//        }
//
//        // (11) count the remaining employees
//        val count = database.runQuery {
//            QueryDsl.from(e).select(count())
//        }
//        println("COUNT: $count")
    }
}