package com.br.flup.app.core.data

import com.br.flup.app.core.model.FlupResponse
import com.br.flup.app.core.model.Outcome
import com.br.flup.app.core.model.UserDomain
import io.reactivex.Single
import io.reactivex.subjects.PublishSubject

interface SessionDataContract {

    interface Repository {
        val signInEventOutcome: PublishSubject<Outcome<FlupResponse.EventSignIn>>
        val signInEmployeeOutcome: PublishSubject<Outcome<UserDomain.User>>

        fun signInEvent(eventCredentials: UserDomain.EventCredentials)
        fun signInEmployee(employeeCredentials: UserDomain.EmployeeCredentials)
    }

    interface Remote {
        fun signInEvent(eventCredentials: UserDomain.EventCredentials): Single<FlupResponse.EventSignIn>
        fun signInEmployee(employeeCredentials: UserDomain.EmployeeCredentials): Single<FlupResponse.EmployeeSignIn>
    }

}