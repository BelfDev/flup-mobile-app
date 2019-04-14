package com.br.flup.app.core.data.remote

import com.br.flup.app.core.data.SessionDataContract
import com.br.flup.app.core.model.FlupResponse
import com.br.flup.app.core.model.UserDomain
import io.reactivex.Single

class SessionRemoteData() : SessionDataContract.Remote {

    private val flupApiService = ApiFactory.flupApiService

    override fun signInEvent(eventCredentials: UserDomain.EventCredentials): Single<FlupResponse.EventSignIn> =
        flupApiService.signInEvent(eventCredentials)

    override fun signInEmployee(employeeCredentials: UserDomain.EmployeeCredentials): Single<FlupResponse.EmployeeSignIn> =
        flupApiService.signInEmployee(employeeCredentials)

}