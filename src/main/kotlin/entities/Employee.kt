package org.example.entities

import org.komapper.annotation.*
import java.time.LocalDateTime

@KomapperEntity
data class Employee(
    @KomapperId @KomapperAutoIncrement
    val id: Int = 0,
    val name: String,
    val departmentId: Int,
    @KomapperVersion
    val version: Int = 0,
    @KomapperCreatedAt
    val createdAt: LocalDateTime = LocalDateTime.MIN,
    @KomapperUpdatedAt
    val updatedAt: LocalDateTime = LocalDateTime.MIN,
)