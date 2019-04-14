package com.br.flup.app.authentication.data

import com.br.flup.app.core.data.Outcome
import com.br.flup.app.authentication.model.SignInForm
import com.br.flup.app.authentication.model.SignInResult
import io.reactivex.Single
import io.reactivex.subjects.PublishSubject

interface SessionDataContract {

    interface Repository {
        val signInEventOutcome: PublishSubject<Outcome<SignInResult>>
        val signInEmployeeOutcome: PublishSubject<Outcome<SignInResult>>

        fun signInEvent(signInForm: SignInForm)
        fun signInEmployee(signInForm: SignInForm)
    }

    interface Remote {
        fun signInEvent(signInForm: SignInForm): Single<SignInResult>
        fun signInEmployee(signInForm: SignInForm): Single<SignInResult>
    }

}
