package com.br.flup.app.authentication.model

import java.util.*

enum class Role {
    CASHIER, BARTENDER, MANAGER
}

data class SignInForm(val identifier: String, val password: String)

interface SignInResult

data class Event(val token: String) : SignInResult

data class User(
    val id: String,
    val createdAt: Date,
    val name: String,
    val role: Role
) : SignInResult

data class SignInFailure(val errorMessage: String) : SignInResult
