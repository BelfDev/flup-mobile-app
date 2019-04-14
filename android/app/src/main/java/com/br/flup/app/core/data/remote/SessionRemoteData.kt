package com.br.flup.app.core.data.remote

import com.br.flup.app.core.data.SessionDataContract
import com.br.flup.app.core.model.EmployeeCredentials
import com.br.flup.app.core.model.EventCredentials
import com.br.flup.app.core.model.EventSignIn
import com.br.flup.app.core.model.User
import io.reactivex.Single

class SessionRemoteData : SessionDataContract.Remote {

    private val flupApiService = ApiFactory.flupApiService

    override fun signInEvent(eventCredentials: EventCredentials): Single<EventSignIn> =
        flupApiService.signInEvent(eventCredentials)

    override fun signInEmployee(employeeCredentials: EmployeeCredentials): Single<User> =
        flupApiService.signInEmployee(employeeCredentials).map { response ->
            response.employee?.let { employee ->
                User(employee)
            }
        }

}