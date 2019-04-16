package com.br.flup.app.core.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.br.flup.app.R
import com.br.flup.app.authentication.model.Event
import com.br.flup.app.authentication.model.SignInFailure
import com.br.flup.app.authentication.model.SignInForm
import com.br.flup.app.authentication.viewmodel.AuthViewModel
import com.br.flup.app.core.data.Outcome.*
import com.br.flup.app.core.extension.getViewModel

class CoreActivity : AppCompatActivity() {

    private val vm by lazy {
        getViewModel { AuthViewModel() }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val eventSignInForm = SignInForm("josh", "456123")
        vm.signInEvent(eventSignInForm)

        initDataListeners()
    }

    private fun initDataListeners() {
        vm.signInEventOutcome.observe(this, Observer { outcome ->
            when (outcome) {

                is Progress -> {
                    println("LOADING")
                }

                is Success -> {

                    Toast.makeText(
                        this,
                        "SUCCESS",
                        Toast.LENGTH_LONG
                    ).show()
                    val event = outcome.data as Event
                    println(event.token)
                }

                is Failure -> {
                    Toast.makeText(
                        this,
                        "FAILURE",
                        Toast.LENGTH_LONG
                    ).show()
                    val failure = outcome.data as SignInFailure
                    println(failure.errorMessage)
                }

                is Error -> {
                    println(outcome.e)
                }

            }
        })
    }

}
