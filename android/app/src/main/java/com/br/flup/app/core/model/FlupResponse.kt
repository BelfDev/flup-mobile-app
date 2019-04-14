package com.br.flup.app.core.model

import com.google.gson.annotations.SerializedName
import java.util.*

object FlupResponse {

    data class EventSignIn(val success: Boolean, val token: String)

    data class EmployeeSignIn(
        val success: Boolean,
        @SerializedName("employe") val employee: Employee?,
        @SerializedName("msg") val errorMessage: String?
    )

    data class Employee(
        val name: String,
        @SerializedName("user") val userId: String,
        @SerializedName("Created_date") val createdAt: Date,
        @SerializedName("role") val roles: List<String>
    )

}