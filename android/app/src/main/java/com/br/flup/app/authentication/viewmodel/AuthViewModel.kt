package com.br.flup.app.authentication.viewmodel

import androidx.lifecycle.LiveData
import com.belfortdev.hurbchallenge.core.extension.toLiveData
import com.br.flup.app.authentication.data.SessionRepository
import com.br.flup.app.authentication.model.SignInForm
import com.br.flup.app.authentication.model.SignInResult
import com.br.flup.app.core.data.Outcome
import com.br.flup.app.core.viewmodel.DisposingViewModel

class AuthViewModel : DisposingViewModel() {

    private val repo = SessionRepository(compositeDisposable)

    val signInEventOutcome: LiveData<Outcome<SignInResult>> by lazy {
        repo.signInEventOutcome.toLiveData(compositeDisposable)
    }

    val signInEmployeeOutcome: LiveData<Outcome<SignInResult>> by lazy {
        repo.signInEmployeeOutcome.toLiveData(compositeDisposable)
    }

    fun signInEvent(signInForm: SignInForm) {
        if (signInEventOutcome.value == null) {
            repo.signInEvent(signInForm)
        }
    }

    fun signInEmployee(signInForm: SignInForm) {
        if (signInEmployeeOutcome.value == null) {
            repo.signInEmployee(signInForm)
        }
    }

}