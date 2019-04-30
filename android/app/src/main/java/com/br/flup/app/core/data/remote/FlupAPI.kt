package com.br.flup.app.core.data.remote

import com.br.flup.app.core.model.*
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface FlupAPI {

    @POST("signin")
    fun signInEvent(@Body eventSignInRequest: EventSignInRequest): Single<EventSignInSuccessResponse>

    @POST("employe-login")
    fun signInEmployee(@Body employeeSignInRequest: EmployeeSignInRequest): Single<EmployeeSignInSuccessResponse>

    @GET("products")
    fun getProducts() : Single<List<ProductSuccessResponse>>
}
