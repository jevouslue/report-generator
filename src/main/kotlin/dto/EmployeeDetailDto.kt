package org.example.dto

import org.komapper.annotation.KomapperProjection

@KomapperProjection
data class EmployeeDetailDto(
    val id: Int,
    val name: String,
    val departmentName: String?,
)