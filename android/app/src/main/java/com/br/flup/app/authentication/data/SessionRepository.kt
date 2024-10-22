package com.br.flup.app.authentication.data

import com.belfortdev.hurbchallenge.core.extension.*
import com.br.flup.app.authentication.data.remote.SessionRemoteDataSource
import com.br.flup.app.authentication.model.*
import com.br.flup.app.core.data.AppScheduler
import com.br.flup.app.core.data.Outcome
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject

class SessionRepository(private val compositeDisposable: CompositeDisposable) : SessionDataContract.Repository {

    private val remote = SessionRemoteDataSource
    private val scheduler = AppScheduler()

    override val signInOutcome: PublishSubject<Outcome<SignInResult>> =
        PublishSubject.create<Outcome<SignInResult>>()

    override fun signInEvent(signInForm: SignInForm) {
        signInOutcome.loading(true)
        remote.signInEvent(signInForm)
            .performOnBackOutOnMain(scheduler)
            .subscribe(
                { result ->
                    when (result) {
                        is Event -> signInOutcome.success(result)
                        is SignInFailure -> signInOutcome.failed(result)
                    }
                },
                { exception ->
                    signInOutcome.error(exception)
                }
            ).addTo(compositeDisposable)
    }

    override fun signInEmployee(signInForm: SignInForm) {
        signInOutcome.loading(true)
        remote.signInEmployee(signInForm)
            .performOnBackOutOnMain(scheduler)
            .subscribe(
                { result ->
                    when (result) {
                        is User -> signInOutcome.success(result)
                        is SignInFailure -> signInOutcome.failed(result)
                    }
                },
                { exception ->
                    signInOutcome.error(exception)
                }
            ).addTo(compositeDisposable)
    }

}
