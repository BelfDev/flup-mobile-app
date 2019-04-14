package com.br.flup.app.core.data

import com.br.flup.app.core.model.*
import io.reactivex.Single
import io.reactivex.subjects.PublishSubject

interface SessionDataContract {

    interface Repository {
        val signInEventOutcome: PublishSubject<Outcome<EventSignIn>>
        val signInEmployeeOutcome: PublishSubject<Outcome<User>>

        fun signInEvent(eventCredentials: EventCredentials)
        fun signInEmployee(employeeCredentials: EmployeeCredentials)
    }

    interface Remote {
        fun signInEvent(eventCredentials: EventCredentials): Single<EventSignIn>
        fun signInEmployee(employeeCredentials: EmployeeCredentials): Single<User>
    }

}