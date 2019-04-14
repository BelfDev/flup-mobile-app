package com.br.flup.app.core.data.remote

import com.br.flup.app.core.model.EmployeeCredentials
import com.br.flup.app.core.model.EmployeeSignIn
import com.br.flup.app.core.model.EventCredentials
import com.br.flup.app.core.model.EventSignIn
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface FlupApi {

    @POST("signin")
    fun signInEvent(@Body eventCredentials: EventCredentials): Single<EventSignIn>

    @POST("employe-login")
    fun signInEmployee(@Body employeeCredentials: EmployeeCredentials): Single<EmployeeSignIn>

}