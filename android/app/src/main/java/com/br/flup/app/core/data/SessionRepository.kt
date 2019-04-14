package com.br.flup.app.core.data

import com.belfortdev.hurbchallenge.core.extension.failed
import com.belfortdev.hurbchallenge.core.extension.loading
import com.belfortdev.hurbchallenge.core.extension.performOnBackOutOnMain
import com.belfortdev.hurbchallenge.core.extension.success
import com.br.flup.app.core.data.remote.SessionRemoteData
import com.br.flup.app.core.model.FlupResponse
import com.br.flup.app.core.model.Outcome
import com.br.flup.app.core.model.UserDomain
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject

object SessionRepository : SessionDataContract.Repository {

    private val remote = SessionRemoteData()
    private val scheduler = AppScheduler()
    private val compositeDisposable = CompositeDisposable()

    override val signInEventOutcome: PublishSubject<Outcome<FlupResponse.EventSignIn>>
        get() = PublishSubject.create<Outcome<FlupResponse.EventSignIn>>()

    override val signInEmployeeOutcome: PublishSubject<Outcome<UserDomain.User>>
        get() = PublishSubject.create<Outcome<UserDomain.User>>()

    override fun signInEvent(eventCredentials: UserDomain.EventCredentials) {
        signInEventOutcome.loading(true)
        remote.signInEvent(eventCredentials)
            .performOnBackOutOnMain(scheduler)
            .subscribe(
                {
                    signInEventOutcome.success(it)
                },
                {
                    signInEventOutcome.failed(it)
                }
            )
    }

    override fun signInEmployee(employeeCredentials: UserDomain.EmployeeCredentials) {
        signInEmployeeOutcome.loading(true)
        remote.signInEmployee(employeeCredentials)
            .performOnBackOutOnMain(scheduler)
            .subscribe(
                {
                    it.employee?.let { employee ->
                        val user = UserDomain.User(employee)
                        signInEmployeeOutcome.success(user)
                    }

                },
                {

                }
            )
    }

}