package com.br.flup.app.core.data

import com.belfortdev.hurbchallenge.core.extension.failed
import com.belfortdev.hurbchallenge.core.extension.loading
import com.belfortdev.hurbchallenge.core.extension.performOnBackOutOnMain
import com.belfortdev.hurbchallenge.core.extension.success
import com.br.flup.app.core.data.remote.SessionRemoteData
import com.br.flup.app.core.model.*
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject

object SessionRepository : SessionDataContract.Repository {

    private val remote = SessionRemoteData()
    private val scheduler = AppScheduler()
    private val compositeDisposable = CompositeDisposable()

    override val signInEventOutcome: PublishSubject<Outcome<EventSignIn>>
        get() = PublishSubject.create<Outcome<EventSignIn>>()

    override val signInEmployeeOutcome: PublishSubject<Outcome<User>>
        get() = PublishSubject.create<Outcome<User>>()

    override fun signInEvent(eventCredentials: EventCredentials) {
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

    override fun signInEmployee(employeeCredentials: EmployeeCredentials) {
        signInEmployeeOutcome.loading(true)
        remote.signInEmployee(employeeCredentials)
            .performOnBackOutOnMain(scheduler)
            .subscribe(
                {
                    signInEmployeeOutcome.success(it)
                },
                {
                    signInEmployeeOutcome.failed(it)
                }
            )
    }

}