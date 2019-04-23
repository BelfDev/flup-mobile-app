package com.br.flup.app.authentication.viewmodel

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import com.belfortdev.hurbchallenge.core.extension.toLiveData
import com.br.flup.app.authentication.data.SessionRepository
import com.br.flup.app.authentication.model.SignInForm
import com.br.flup.app.authentication.model.SignInResult
import com.br.flup.app.core.data.Outcome
import com.br.flup.app.core.viewmodel.DisposingViewModel

class AuthViewModel : DisposingViewModel() {

    var form = SignInForm()

    private val repo = SessionRepository(compositeDisposable)
    
    val signInEventOutcome: LiveData<Outcome<SignInResult>> by lazy {
        repo.signInEventOutcome.toLiveData(compositeDisposable)
    }

    val signInEmployeeOutcome: LiveData<Outcome<SignInResult>> by lazy {
        repo.signInEmployeeOutcome.toLiveData(compositeDisposable)
    }

    fun signInEvent() {
        if (signInEventOutcome.value == null && isFormValid()) {
            repo.signInEvent(form)
        }
    }

    fun signInEmployee() {
        if (signInEmployeeOutcome.value == null && isFormValid()) {
            repo.signInEmployee(form)
        }
    }

    fun resetForm() {
        form = SignInForm()
    }

    private fun isFormValid() : Boolean {
        form.apply {
            return identifier.isNotBlank() && password.isNotBlank()
        }
    }

}