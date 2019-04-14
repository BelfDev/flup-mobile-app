package com.br.flup.app.core.model

import java.util.*


enum class Role {
    CASHIER, BARTENDER, MANAGER
}

data class EventCredentials(val email: String, val password: String)

data class EmployeeCredentials(val name: String, val password: String)

data class User(
    val id: String,
    val createdAt: Date,
    val name: String,
    val role: Role
) {
    constructor(employee: Employee) : this(
        id = employee.userId,
        createdAt = employee.createdAt,
        name = employee.name,
        role = Role.valueOf(employee.roles[0].toUpperCase())
    )
}

