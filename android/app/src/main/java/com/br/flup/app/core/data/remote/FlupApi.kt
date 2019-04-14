package com.br.flup.app.core.data.remote

import com.br.flup.app.core.model.FlupResponse
import com.br.flup.app.core.model.UserDomain
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface FlupApi {

    @POST("signin")
    fun signInEvent(@Body eventCredentials: UserDomain.EventCredentials): Single<FlupResponse.EventSignIn>

    @POST("employe-login")
    fun signInEmployee(@Body employeeCredentials: UserDomain.EmployeeCredentials): Single<FlupResponse.EmployeeSignIn>

}