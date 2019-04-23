package com.br.flup.app.authentication.viewmodel

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import com.belfortdev.hurbchallenge.core.extension.toLiveData
import com.br.flup.app.authentication.data.SessionRepository
import com.br.flup.app.authentication.model.Event
import com.br.flup.app.authentication.model.SignInForm
import com.br.flup.app.authentication.model.SignInResult
import com.br.flup.app.core.data.Outcome
import com.br.flup.app.core.viewmodel.DisposingViewModel

class AuthViewModel : DisposingViewModel() {

    var form = SignInForm()
    var isLoading = ObservableBoolean(false)

    private val repo = SessionRepository(compositeDisposable)

    val signInEventOutcome: LiveData<Outcome<SignInResult>> by lazy {
        repo.signInEventOutcome.toLiveData(compositeDisposable)
    }

    val signInEmployeeOutcome: LiveData<Outcome<SignInResult>> by lazy {
        repo.signInEmployeeOutcome.toLiveData(compositeDisposable)
    }

    fun signInEvent() {
        if (isFormValid()) {
            repo.signInEvent(form)
        }
    }

    fun signInEmployee() {
        if (isFormValid()) {
            repo.signInEmployee(form)
        }
    }

    fun onSuccessfulEventSignIn(event: Event) {

        resetForm()
    }

    fun resetForm() {
        form = SignInForm()
    }

    private fun isFormValid(): Boolean {
        form.apply {
            return identifier.isNotBlank() && password.isNotBlank()
        }
    }

}