package com.br.flup.app.core.data.remote

import com.br.flup.app.core.model.EmployeeSignInRequest
import com.br.flup.app.core.model.EmployeeSignInSuccessResponse
import com.br.flup.app.core.model.EventSignInRequest
import com.br.flup.app.core.model.EventSignInSuccessResponse
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface FlupAPI {

    @POST("signin")
    fun signInEvent(@Body eventSignInRequest: EventSignInRequest): Single<EventSignInSuccessResponse>

    @POST("employe-login")
    fun signInEmployee(@Body employeeSignInRequest: EmployeeSignInRequest): Single<EmployeeSignInSuccessResponse>

}
