package com.br.flup.app.core.model

import java.util.*

object UserDomain {

    enum class Role {
        CASHIER, BARTENDER, MANAGER
    }

    data class EventCredentials(val email: String, val password: String)

    data class EmployeeCredentials(val name: String, val password: String)

    data class User(val id: String,
                    val createdAt: Date,
                    val name: String,
                    val role: Role) {
        constructor(employeeResponse: FlupResponse.Employee): this(
            id = employeeResponse.userId,
            createdAt = employeeResponse.createdAt,
            name = employeeResponse.name,
            role = Role.valueOf(employeeResponse.roles[0].toUpperCase())
        )
    }

}